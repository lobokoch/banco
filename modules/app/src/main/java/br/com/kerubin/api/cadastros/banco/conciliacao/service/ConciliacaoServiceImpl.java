package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT_ACCOUNT_TYPE;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_USER;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoContext;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.database.core.ServiceContext;
import br.com.kerubin.api.database.core.ServiceContextData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciliacaoServiceImpl implements ConciliacaoService {
	
	public static final String HTTP = "http://";
	public static final String FINANCEIRO_CONTASPAGAR_SERVICE = "financeiro-contaspagar/financeiro/contas_pagar/conciliacaoBancaria";
	public static final String FINANCEIRO_CONTASRECEBER_SERVICE = "financeiro-contasreceber/financeiro/contas_receber/conciliacaoBancaria";
	public static final String FINANCEIRO_FLUXOCAIXA_SERVICE = "financeiro-fluxocaixa/financeiro/fluxo_caixa/conciliacaoBancaria";
	
	@Inject
	private ConciliacaoServiceHelper conciliacaoTransacaoService;
	
	@Inject
	private RestTemplate restTemplate;
	
	@Override
	public UUID processarArquivo(InputStream stream) {
		ConciliacaoOFXReader reader = new ConciliacaoOFXReader();
		reader.readOFXStream(stream);
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = createConciliacaoBancaria(reader);
		
		createConciliacaoTransacaoAsync(conciliacaoBancariaEntity, reader);
		
		return conciliacaoBancariaEntity.getId();
	}

	private void createConciliacaoTransacaoAsync(ConciliacaoBancariaEntity conciliacaoBancariaEntity, ConciliacaoOFXReader reader) {
		
		ConciliacaoContext contextoInicial = ConciliacaoContext.builder()
				.conciliacaoBancariaEntity(conciliacaoBancariaEntity)
				.serviceContextData(ServiceContext.getServiceContextData())
				.build();
		
		CompletableFuture
			.supplyAsync(() -> criarTransacoes(contextoInicial, reader))
			.thenApply(contexto -> inicializarAnaliseDasTransacoes(contexto)) //
			.thenApply(contexto -> verificarModulosDeContas(contexto)) //
			.thenApply(contexto -> verificarModuloCaixa(contexto)) //
			.thenApply(contexto -> finalizarProcessamentoDasTransacoes(contexto)) //
			.thenAccept(contexto -> finalizarProcessamentoConciliacao(contexto)); //
		
	}

	/**
	 * Cria as transações de conciliação bancária e salva no banco de dados.
	 * */
	private ConciliacaoContext criarTransacoes(ConciliacaoContext contexto, ConciliacaoOFXReader reader) {
		
		log.info("INICIO criarTransacoes...");
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		
		List<ConciliacaoTransacaoEntity> transacoes = conciliacaoTransacaoService.criarTransacoes(contexto.getConciliacaoBancariaEntity(), reader);
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
	private void finalizarProcessamentoConciliacao(ConciliacaoContext contexto) {
		
		log.info("INICIO finalizarProcessamentoConciliacao...");
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
		conciliacaoBancariaEntity.setSituacaoConciliacao(SituacaoConciliacao.TRANSACOES_ANALISADAS);
		
		conciliacaoBancariaEntity = conciliacaoTransacaoService.salvarConciliacao(conciliacaoBancariaEntity);
		
		contexto.setConciliacaoBancariaEntity(conciliacaoBancariaEntity);
		
		log.info("FIM finalizarProcessamentoConciliacao...");
		
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
        if (serviceResponse != null && serviceResponse.getTransacoes() != null) {
        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream().filter(it -> it.getTituloConciliadoId() != null).collect(Collectors.toList());
        	transacoesMatched.forEach(it -> {
        		ConciliacaoTransacaoEntity transacao = transacoesDedito.stream().filter(it2 -> it2.getId().equals(it.getId())).findFirst().orElse(null);
        		if (transacao != null) {
        			transacao.setTituloConciliadoId(it.getTituloConciliadoId());
        			transacao.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
        			transacao.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
        		}
        		else {
        			log.warn("Transação de conciliação bancária retornada na resposta do Contas a Pagar não encontrada na lista corrente:" + it);
        		}
        	});
        }
        
        List<ConciliacaoTransacaoEntity> transacoesAlteradas = transacoesDedito.stream().filter(it -> it.getTituloConciliadoId() != null).collect(Collectors.toList());
        if (!transacoesAlteradas.isEmpty()) {
        	conciliacaoTransacaoService.salvarTransacoes(transacoesAlteradas);
        }
        
		return transacoesDedito;
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
        if (serviceResponse != null && serviceResponse.getTransacoes() != null) {
        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream().filter(it -> it.getTituloConciliadoId() != null).collect(Collectors.toList());
        	transacoesMatched.forEach(it -> {
        		ConciliacaoTransacaoEntity transacao = transacoesNaoBaixadas.stream().filter(it2 -> it2.getId().equals(it.getId())).findFirst().orElse(null);
        		if (transacao != null) {
        			transacao.setTituloConciliadoId(it.getTituloConciliadoId());
        			transacao.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
        			transacao.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
        		}
        		else {
        			log.warn("Transação de conciliação bancária retornada na resposta do Contas a Pagar não encontrada na lista corrente:" + it);
        		}
        	});
        }
        
        // Marca os itens ainda não baixados, para serem baixados no caixa.
        transacoesNaoBaixadas.stream()
        .filter(it -> it.getTituloConciliadoId() == null)
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
	        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream().filter(it -> it.getTituloConciliadoId() != null).collect(Collectors.toList());
	        	transacoesMatched.forEach(it -> {
	        		ConciliacaoTransacaoEntity transacao = transacoesCredito.stream().filter(it2 -> it2.getId().equals(it.getId())).findFirst().orElse(null);
	        		if (transacao != null) {
	        			transacao.setTituloConciliadoId(it.getTituloConciliadoId());
	        			transacao.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
	        			transacao.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
	        		}
	        		else {
	        			log.warn("Transação de conciliação bancária retornada na resposta do Contas a Receber não encontrada na lista corrente:" + it);
	        		}
	        	});
	        }
	        
	        List<ConciliacaoTransacaoEntity> transacoesAlteradas = transacoesCredito.stream().filter(it -> it.getTituloConciliadoId() != null).collect(Collectors.toList());
	        if (!transacoesAlteradas.isEmpty()) {
	        	conciliacaoTransacaoService.salvarTransacoes(transacoesAlteradas);
	        }
	        
			return transacoesCredito;
			
		});
		
	}
	
	private HttpHeaders buildHttpHeaders(ServiceContextData serviceContextData) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_TENANT, serviceContextData.getTenant());
        headers.set(HEADER_USER, serviceContextData.getUsername());
        headers.set(HEADER_TENANT_ACCOUNT_TYPE, serviceContextData.getTenantAccountType());
        
        return headers;
	}
	
	private ConciliacaoBancariaDTO toDTO(ConciliacaoBancariaEntity conciliacaoBancariaEntity, List<ConciliacaoTransacaoEntity> transacoes) {
		
		List<ConciliacaoTransacaoDTO> transacoesDTO = transacoes.stream().map(it -> {
			
			ConciliacaoTransacaoDTO dto = ConciliacaoTransacaoDTO.builder()
					.id(it.getId())
					.trnData(it.getTrnData())
					.trnHistorico(it.getTrnHistorico())
					.trnDocumento(it.getTrnDocumento())
					.trnTipo(it.getTrnTipo())
					.trnValor(it.getTrnValor())
					.build();
			
			return dto;
			
		}).collect(Collectors.toList());
		
		ConciliacaoBancariaDTO conciliacaoBancariaDTO = ConciliacaoBancariaDTO.builder()
				.id(conciliacaoBancariaEntity.getId())
				.bancoId(conciliacaoBancariaEntity.getBancoId())
				.agenciaId(conciliacaoBancariaEntity.getAgenciaId())
				.contaId(conciliacaoBancariaEntity.getContaId())
				.dataIni(conciliacaoBancariaEntity.getDataIni())
				.dataFim(conciliacaoBancariaEntity.getDataFim())
				.transacoes(transacoesDTO)
				.build();
		
		return conciliacaoBancariaDTO;
	}

}