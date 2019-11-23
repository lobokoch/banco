package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.buildHttpHeaders;
import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.toDTO;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_CONTASPAGAR_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_CONTASRECEBER_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_FLUXOCAIXA_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.HTTP;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoOFXReader;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoContext;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoTituloDTO;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloEntity;
import br.com.kerubin.api.database.core.ServiceContext;
import br.com.kerubin.api.database.core.ServiceContextData;
import lombok.extern.slf4j.Slf4j;

import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

@Slf4j
@Service
public class ConciliacaoServiceImpl implements ConciliacaoService {
	
	@Inject
	private ConciliacaoServiceHelper conciliacaoTransacaoService;
	
	@Inject
	private RestTemplate restTemplate;
	
	@Override
	public ConciliacaoBancariaAsyncExecution processarArquivo(InputStream stream) {
		ConciliacaoOFXReader reader = new ConciliacaoOFXReader();
		reader.readOFXStream(stream);
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = createConciliacaoBancaria(reader);
		
		CompletableFuture<ConciliacaoBancariaEntity> future = createConciliacaoTransacaoAsync(conciliacaoBancariaEntity, reader);
		
		ConciliacaoBancariaAsyncExecution result = ConciliacaoBancariaAsyncExecution.builder()
				.conciliacaoBancaria(conciliacaoBancariaEntity)
				.future(future)
				.build();
		
		return result;
	}

	private CompletableFuture<ConciliacaoBancariaEntity> createConciliacaoTransacaoAsync(ConciliacaoBancariaEntity conciliacaoBancariaEntity, ConciliacaoOFXReader reader) {
		
		ConciliacaoContext contextoInicial = ConciliacaoContext.builder()
				.conciliacaoBancariaEntity(conciliacaoBancariaEntity)
				.serviceContextData(ServiceContext.getServiceContextData())
				.build();
		
		return CompletableFuture
			.supplyAsync(() -> criarTransacoes(contextoInicial, reader))
			.thenApply(contexto -> inicializarAnaliseDasTransacoes(contexto)) //
			.thenApply(contexto -> verificarModulosDeContas(contexto)) //
			.thenApply(contexto -> verificarModuloCaixa(contexto)) //
			.thenApply(contexto -> finalizarProcessamentoDasTransacoes(contexto)) //
			.thenApply(contexto -> finalizarProcessamentoConciliacao(contexto)); //
		
	}

	/**
	 * Cria as transações de conciliação bancária e salva no banco de dados.
	 * */
	private ConciliacaoContext criarTransacoes(ConciliacaoContext contexto, ConciliacaoOFXReader reader) {
		
		log.info("INICIO criarTransacoes...");
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		
		List<ConciliacaoTransacaoEntity> transacoes = null;
		try {
			transacoes = conciliacaoTransacaoService.criarTransacoes(contexto.getConciliacaoBancariaEntity(), reader);
		} catch(Exception e) {
			log.error("Erro ao gerar transações:" + e.getMessage(), e);
			transacoes = Collections.emptyList();
		}
		contexto.setTransacoes(transacoes);
		
		log.info("FIM criarTransacoes.");
		return contexto;
	}
	
	
	/**
	 * Inicializa status para análise das transações. 
	 * */
	private ConciliacaoContext inicializarAnaliseDasTransacoes(ConciliacaoContext contexto) {
		log.info("INICIO inicializarAnaliseDasTransacoes...");
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
		conciliacaoBancariaEntity.setSituacaoConciliacao(SituacaoConciliacao.ANALISANDO_TRANSACOES);
		
		conciliacaoBancariaEntity = conciliacaoTransacaoService.salvarConciliacao(conciliacaoBancariaEntity);
		
		contexto.setConciliacaoBancariaEntity(conciliacaoBancariaEntity);
		
		log.info("FIM inicializarAnaliseDasTransacoes.");
		
		return contexto;
	}
	
	/**
	 * Verifica os serviços do Contas a Pagar e Contas a Receber se encontra essa transações lançadas para dar baixa depois. 
	 * */
	private ConciliacaoContext verificarModulosDeContas(ConciliacaoContext contexto) {
		
		log.info("INICIO verificarModulosDeContas...");
		
		List<ConciliacaoTransacaoEntity> transacoes = contexto.getTransacoes();
		
		List<ConciliacaoTransacaoEntity> transacoesCredito = transacoes.stream()
				.filter(it -> TipoTransacao.CREDITO.equals(it.getTrnTipo()))
				.collect(Collectors.toList());
		
		List<ConciliacaoTransacaoEntity> transacoesDedito = transacoes.stream()
				.filter(it -> TipoTransacao.DEBITO.equals(it.getTrnTipo()))
				.collect(Collectors.toList());
		
		
		CompletableFuture<List<ConciliacaoTransacaoEntity>> transacoesVerificadasNoContasPagarFutures = CompletableFuture.supplyAsync(() -> verificarTransacoesNoContasPagar(contexto, transacoesDedito));
		
		CompletableFuture<List<ConciliacaoTransacaoEntity>> transacoesVerificadasNoContasReceberFutures = verificarTransacoesNoContasReceber(contexto, transacoesCredito);
		
		List<CompletableFuture<List<ConciliacaoTransacaoEntity>>> futures = Arrays.asList(transacoesVerificadasNoContasPagarFutures, transacoesVerificadasNoContasReceberFutures);
		
		CompletableFuture<Void> allOfFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		
		CompletableFuture<List<List<ConciliacaoTransacaoEntity>>> transacoesVerificadasNosServicosFuture = allOfFutures.thenApply(v -> {
			
			List<List<ConciliacaoTransacaoEntity>> resultadosDosServicos = futures.stream().map(future -> future.join()).collect(Collectors.toList());
			return resultadosDosServicos;
		});
		
		List<List<ConciliacaoTransacaoEntity>> transacoesVerificadasNosServicos;
		try {
			// Aguarda até processar tudo nos serviços de Contas a Pagar e Contas a Receber.
			transacoesVerificadasNosServicos = transacoesVerificadasNosServicosFuture.get();
			
			List<ConciliacaoTransacaoEntity> processadosA = transacoesVerificadasNosServicos.get(0);
			List<ConciliacaoTransacaoEntity> processadosB = transacoesVerificadasNosServicos.get(1);
			
			List<ConciliacaoTransacaoEntity> result = new ArrayList<>();
			
			result.addAll(processadosA);
			result.addAll(processadosB);
			
			contexto.setTransacoes(result);
			
			log.info("FIM1 verificarModulosDeContas.");
			
			return contexto;
			
		} catch (InterruptedException | ExecutionException e) {
			log.error("Erro ao buscar verificar transações nos serviços.", e);
		}
		
		log.info("FIM2 verificarModulosDeContas.");
		return contexto;
	}
	
	/**
	 * Finaliza as transações.
	 * */
	private ConciliacaoContext finalizarProcessamentoDasTransacoes(ConciliacaoContext contexto) {
		log.info("INICIO finalizarProcessamentoDasTransacoes...");
		
		log.info("FIM finalizarProcessamentoDasTransacoes.");
		
		return contexto;
		
	}
	
	/**
	 * Finaliza o processamento da conciliação.
	 * */
	private ConciliacaoBancariaEntity finalizarProcessamentoConciliacao(ConciliacaoContext contexto) {
		
		log.info("INICIO finalizarProcessamentoConciliacao...");
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
		conciliacaoBancariaEntity.setSituacaoConciliacao(SituacaoConciliacao.TRANSACOES_ANALISADAS);
		
		conciliacaoBancariaEntity = conciliacaoTransacaoService.salvarConciliacao(conciliacaoBancariaEntity);
		
		contexto.setConciliacaoBancariaEntity(conciliacaoBancariaEntity);
		
		log.info("FIM finalizarProcessamentoConciliacao...");
		
		return conciliacaoBancariaEntity;
		
	}
	
	
	
	private ConciliacaoBancariaEntity createConciliacaoBancaria(ConciliacaoOFXReader reader) {
		return conciliacaoTransacaoService.createConciliacaoBancaria(reader);
	}
	
	private List<ConciliacaoTransacaoEntity> verificarTransacoesNoContasPagar(ConciliacaoContext contexto, List<ConciliacaoTransacaoEntity> transacoesDedito) {
		
		ServiceContextData serviceContextData = contexto.getServiceContextData();
		
		ServiceContext.applyServiceContextData(serviceContextData);
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
		
		ConciliacaoBancariaDTO conciliacaoBancariaDTO = toDTO(conciliacaoBancariaEntity, transacoesDedito);
		
		String url = HTTP + FINANCEIRO_CONTASPAGAR_SERVICE + "/verificarTransacoes";
		
		HttpHeaders headers = buildHttpHeaders(serviceContextData);
		
		HttpEntity<ConciliacaoBancariaDTO> request = new HttpEntity<ConciliacaoBancariaDTO>(conciliacaoBancariaDTO, headers);
        ResponseEntity<ConciliacaoBancariaDTO> response = null;
        
        response = restTemplate.exchange(url, HttpMethod.POST, request, ConciliacaoBancariaDTO.class);
        
        ConciliacaoBancariaDTO serviceResponse = response.getBody();
		
        // Faz o match e atualiza as transações atuais com as retornadas, no caso se achou alguma que faz match no Contas a Pagar.

        if (isNotEmpty(serviceResponse) && isNotEmpty(serviceResponse.getTransacoes())) {
        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream() //
        			.filter(it -> isNotEmpty(it.getTituloConciliadoId())) //
        			.collect(Collectors.toList());
        	
        	// Fez metch com algum título
        	/*List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream()
        			.filter(it -> isNotEmpty(it.getConciliacaoTransacaoTitulosDTO())).collect(Collectors.toList());*/
        	
        	transacoesMatched.forEach(it -> {
        		ConciliacaoTransacaoEntity transacao = transacoesDedito.stream().filter(it2 -> it2.getId().equals(it.getId())).findFirst().orElse(null);
        		if (isNotEmpty(transacao)) {
        			transacao.setTituloConciliadoId(it.getTituloConciliadoId());
        			transacao.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
        			transacao.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
        			transacao.setDataConciliacao(it.getDataConciliacao());
        			
        			transacao.setConciliacaoTransacaoTitulos(toEntity(it.getConciliacaoTransacaoTitulosDTO()));
        		}
        		else {
        			log.warn("Transação de conciliação bancária retornada na resposta do Contas a Pagar não encontrada na lista corrente:" + it);
        		}
        	});
        }
        
        List<ConciliacaoTransacaoEntity> transacoesAlteradas = transacoesDedito.stream().filter(it -> isNotEmpty(it.getTituloConciliadoId())).collect(Collectors.toList());
        if (!transacoesAlteradas.isEmpty()) {
        	conciliacaoTransacaoService.salvarTransacoes(transacoesAlteradas);
        }
        
		return transacoesDedito;
	}
	
	private List<ConciliacaoTransacaoTituloEntity> toEntity(List<ConciliacaoTransacaoTituloDTO> dto) {
		if (isNotEmpty(dto)) {
			List<ConciliacaoTransacaoTituloEntity> titulos = dto.stream().map(it -> { 
				ConciliacaoTransacaoTituloEntity titulo = new ConciliacaoTransacaoTituloEntity(); 
				titulo.setId(it.getId());
				titulo.setTituloConciliadoId(it.getTituloConciliadoId());
				titulo.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
				titulo.setTituloConciliadoDataVen(it.getTituloConciliadoDataVen());
				titulo.setTituloConciliadoDataPag(it.getTituloConciliadoDataPag());
				titulo.setDataConciliacao(it.getDataConciliacao());
				titulo.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
				
				return titulo;
			}).collect(Collectors.toList());
			
			return titulos;
		}
		
		return null;
	}

	/**
	 * Caso tenha algum movimento (transação) não encontrado no Contas a Pagar e Contas a Receber, verifica se já foi lançado/ vai poder lançar no caixa.
	 * */
	private ConciliacaoContext verificarModuloCaixa(ConciliacaoContext contexto) {
		
		log.info("INICIO verificarModuloCaixa...");
		
		ServiceContextData serviceContextData = contexto.getServiceContextData();
		ServiceContext.applyServiceContextData(serviceContextData);
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity(); 
		List<ConciliacaoTransacaoEntity> transacoes = contexto.getTransacoes();
		
		
		List<ConciliacaoTransacaoEntity> transacoesNaoBaixadas = transacoes.stream().filter(it -> it.getTituloConciliadoId() == null).collect(Collectors.toList());
		
		ConciliacaoBancariaDTO conciliacaoBancariaDTO = toDTO(conciliacaoBancariaEntity, transacoesNaoBaixadas);
		
		String url = HTTP + FINANCEIRO_FLUXOCAIXA_SERVICE + "/verificarTransacoes";
		
		HttpHeaders headers = buildHttpHeaders(serviceContextData);
		
		HttpEntity<ConciliacaoBancariaDTO> request = new HttpEntity<ConciliacaoBancariaDTO>(conciliacaoBancariaDTO, headers);
        ResponseEntity<ConciliacaoBancariaDTO> response = null;
        
        response = restTemplate.exchange(url, HttpMethod.POST, request, ConciliacaoBancariaDTO.class);
        
        ConciliacaoBancariaDTO serviceResponse = response.getBody();
		
        // Faz o match e atualiza as transações atuais com as retornadas, no caso se achou alguma que faz match no F.
        if (isNotEmpty(serviceResponse) && isNotEmpty(serviceResponse.getTransacoes())) {
        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream().filter(it -> isNotEmpty(it.getTituloConciliadoId())).collect(Collectors.toList());
        	transacoesMatched.forEach(it -> {
        		ConciliacaoTransacaoEntity transacao = transacoesNaoBaixadas.stream().filter(it2 -> it2.getId().equals(it.getId())).findFirst().orElse(null);
        		if (isNotEmpty(transacao)) {
        			transacao.setTituloConciliadoId(it.getTituloConciliadoId());
        			transacao.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
        			transacao.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
        			transacao.setDataConciliacao(it.getDataConciliacao());
        			
        			transacao.setConciliacaoTransacaoTitulos(toEntity(it.getConciliacaoTransacaoTitulosDTO()));
        		}
        		else {
        			log.warn("Transação de conciliação bancária retornada na resposta do Contas a Pagar não encontrada na lista corrente:" + it);
        		}
        	});
        }
        
        // Marca os itens ainda não baixados, para serem baixados no caixa.
        transacoesNaoBaixadas.stream().filter(it -> isEmpty(it.getTituloConciliadoId()))
	        .forEach(it -> it.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIAR_CAIXA));
        
        if (!transacoesNaoBaixadas.isEmpty()) {
        	conciliacaoTransacaoService.salvarTransacoes(transacoesNaoBaixadas);
        }
        
        contexto.setTransacoes(transacoes);
        
        log.info("FIM verificarModuloCaixa.");
        
		return contexto;
	}
	
	private CompletableFuture<List<ConciliacaoTransacaoEntity>> verificarTransacoesNoContasReceber(ConciliacaoContext contexto, List<ConciliacaoTransacaoEntity> transacoesCredito) {
		
		return CompletableFuture.supplyAsync(() -> {
			
			ServiceContextData serviceContextData = contexto.getServiceContextData();
			ServiceContext.applyServiceContextData(serviceContextData);
			
			ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
			
			ConciliacaoBancariaDTO conciliacaoBancariaDTO = toDTO(conciliacaoBancariaEntity, transacoesCredito);
			
			String url = HTTP + FINANCEIRO_CONTASRECEBER_SERVICE + "/verificarTransacoes";
			
			HttpHeaders headers = buildHttpHeaders(serviceContextData);
			
			HttpEntity<ConciliacaoBancariaDTO> request = new HttpEntity<ConciliacaoBancariaDTO>(conciliacaoBancariaDTO, headers);
	        ResponseEntity<ConciliacaoBancariaDTO> response = null;
	        
	        response = restTemplate.exchange(url, HttpMethod.POST, request, ConciliacaoBancariaDTO.class);
	        
	        ConciliacaoBancariaDTO serviceResponse = response.getBody();
			
	        // Faz o match e atualiza as transações atuais com as retornadas, no caso se achou alguma que faz match no Contas a Receber.
	        if (serviceResponse != null && serviceResponse.getTransacoes() != null) {
	        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream().filter(it -> isNotEmpty(it.getTituloConciliadoId())).collect(Collectors.toList());
	        	transacoesMatched.forEach(it -> {
	        		ConciliacaoTransacaoEntity transacao = transacoesCredito.stream().filter(it2 -> it2.getId().equals(it.getId())).findFirst().orElse(null);
	        		if (isNotEmpty(transacao)) {
	        			transacao.setTituloConciliadoId(it.getTituloConciliadoId());
	        			transacao.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
	        			transacao.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
	        			transacao.setDataConciliacao(it.getDataConciliacao());
	        			
	        			transacao.setConciliacaoTransacaoTitulos(toEntity(it.getConciliacaoTransacaoTitulosDTO()));
	        		}
	        		else {
	        			log.warn("Transação de conciliação bancária retornada na resposta do Contas a Receber não encontrada na lista corrente:" + it);
	        		}
	        	});
	        }
	        
	        List<ConciliacaoTransacaoEntity> transacoesAlteradas = transacoesCredito.stream().filter(it -> isNotEmpty(it.getTituloConciliadoId())).collect(Collectors.toList());
	        if (!transacoesAlteradas.isEmpty()) {
	        	conciliacaoTransacaoService.salvarTransacoes(transacoesAlteradas);
	        }
	        
			return transacoesCredito;
			
		});
		
	}
	
	
	
	

}
