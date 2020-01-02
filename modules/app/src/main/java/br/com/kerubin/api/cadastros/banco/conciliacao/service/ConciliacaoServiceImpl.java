package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.buildHttpHeaders;
import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.toDTO;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_CONTASPAGAR_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_CONTASRECEBER_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_FLUXOCAIXA_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.HTTP;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import br.com.kerubin.api.cadastros.banco.conciliacao.model.PlanoContaDTO;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloEntity;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaEntity;
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
	private ConciliacaoServiceHelper conciliacaoServiceHelper;
	
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
        	
        	transacoesMatched.forEach(transacaoDTO -> {
        		ConciliacaoTransacaoEntity transacaoEntity = transacoesDedito.stream().filter(it2 -> it2.getId().equals(transacaoDTO.getId())).findFirst().orElse(null);
        		updateTransacaoEntity(transacaoEntity, transacaoDTO);
        	});
        }
        
        List<ConciliacaoTransacaoEntity> transacoesAlteradas = transacoesDedito.stream().filter(it -> isNotEmpty(it.getTituloConciliadoId())).collect(Collectors.toList());
        if (!transacoesAlteradas.isEmpty()) {
        	conciliacaoTransacaoService.salvarTransacoes(transacoesAlteradas);
        }
        
		return transacoesDedito;
	}

	private void updateTransacaoEntity(ConciliacaoTransacaoEntity transacaoEntity, ConciliacaoTransacaoDTO transacaoDTO) {
		if (isNotEmpty(transacaoEntity) && isNotEmpty(transacaoDTO)) {
			transacaoEntity.setTituloConciliadoId(transacaoDTO.getTituloConciliadoId());
			transacaoEntity.setTituloConciliadoDesc(transacaoDTO.getTituloConciliadoDesc());
			transacaoEntity.setTituloConciliadoValor(transacaoDTO.getTituloConciliadoValor());
			transacaoEntity.setTituloConciliadoDataVen(transacaoDTO.getTituloConciliadoDataVen());
			transacaoEntity.setTituloConciliadoDataPag(transacaoDTO.getTituloConciliadoDataPag());
			transacaoEntity.setTituloPlanoContas(toEntity(transacaoDTO.getTituloPlanoContas()));
			transacaoEntity.setSituacaoConciliacaoTrn(transacaoDTO.getSituacaoConciliacaoTrn());
			transacaoEntity.setDataConciliacao(transacaoDTO.getDataConciliacao());
			transacaoEntity.setConciliadoComErro(transacaoDTO.getConciliadoComErro());
			transacaoEntity.setConciliadoMsg(transacaoDTO.getConciliadoMsg());
			
			if (isNotEmpty(transacaoDTO.getConciliacaoTransacaoTitulosDTO())) {
				List<ConciliacaoTransacaoTituloDTO> titulosDTO = toSafeList(transacaoDTO.getConciliacaoTransacaoTitulosDTO());
				List<ConciliacaoTransacaoTituloEntity> titulosEntity = toSafeList(transacaoEntity.getConciliacaoTransacaoTitulos());
				try {
					titulosDTO = titulosDTO.stream()
							.filter(tituloDTO -> titulosEntity
								.stream()
								.noneMatch(
										tituloEntity -> {
											boolean ret = tituloEntity.getTituloConciliadoId().equals(tituloDTO.getTituloConciliadoId());
											return ret;
										}
							)) // filter
						.collect(Collectors.toList());
				} catch(Exception e) {
					log.error(MessageFormat.format("Erro ao fazer merge dos títulos da transação id: {0}", transacaoEntity.getId()), e);
				}
				
				if (isNotEmpty(titulosDTO)) {
					List<ConciliacaoTransacaoTituloEntity> entities = toEntity(titulosDTO);
					transacaoEntity.getConciliacaoTransacaoTitulos().clear(); // Para não adicionar itens já adicionados.
					entities.forEach(transacaoEntity::addConciliacaoTransacaoTitulo);
				}
			}
		}
		else {
			log.warn("Transação de conciliação bancária retornada na resposta do módulo não encontrada na lista corrente:" + transacaoDTO);
		}
	}
	
	
	
	private List<ConciliacaoTransacaoTituloEntity> toEntity(List<ConciliacaoTransacaoTituloDTO> dto) {
		if (isNotEmpty(dto)) {
			List<ConciliacaoTransacaoTituloEntity> titulos = dto.stream().map(it -> { 
				ConciliacaoTransacaoTituloEntity tituloEntity = new ConciliacaoTransacaoTituloEntity(); 
				tituloEntity.setId(it.getId());
				tituloEntity.setTituloConciliadoId(it.getTituloConciliadoId());
				tituloEntity.setTituloConciliadoDesc(it.getTituloConciliadoDesc());
				tituloEntity.setTituloConciliadoValor(it.getTituloConciliadoValor());
				tituloEntity.setTituloConciliadoDataVen(it.getTituloConciliadoDataVen());
				tituloEntity.setTituloConciliadoDataPag(it.getTituloConciliadoDataPag());
				tituloEntity.setTituloPlanoContas(toEntity(it.getTituloPlanoContas()));
				tituloEntity.setDataConciliacao(it.getDataConciliacao());
				tituloEntity.setSituacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn());
				
				return tituloEntity;
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
		
		Predicate<ConciliacaoTransacaoEntity> filter = trnEntity -> 
			!SituacaoConciliacaoTrn.CONCILIADO_CONTAS_PAGAR.equals(trnEntity.getSituacaoConciliacaoTrn()) || 
			!SituacaoConciliacaoTrn.CONCILIADO_CONTAS_RECEBER.equals(trnEntity.getSituacaoConciliacaoTrn());
		
		List<ConciliacaoTransacaoEntity> transacoesNaoBaixadas = transacoes.stream().filter(filter).collect(Collectors.toList());
		
		ConciliacaoBancariaDTO conciliacaoBancariaDTO = toDTO(conciliacaoBancariaEntity, transacoesNaoBaixadas);
		
		String url = HTTP + FINANCEIRO_FLUXOCAIXA_SERVICE + "/verificarTransacoes";
		
		HttpHeaders headers = buildHttpHeaders(serviceContextData);
		
		HttpEntity<ConciliacaoBancariaDTO> request = new HttpEntity<ConciliacaoBancariaDTO>(conciliacaoBancariaDTO, headers);
        ResponseEntity<ConciliacaoBancariaDTO> response = null;
        
        response = restTemplate.exchange(url, HttpMethod.POST, request, ConciliacaoBancariaDTO.class);
        
        ConciliacaoBancariaDTO serviceResponse = response.getBody();
		
        // Faz o match e atualiza as transações atuais com as retornadas, no caso se achou alguma que faz match.
        if (isNotEmpty(serviceResponse) && isNotEmpty(serviceResponse.getTransacoes())) {
			
        	List<ConciliacaoTransacaoDTO> transacoesMatched = serviceResponse.getTransacoes().stream().filter(touchedByCaixa()).collect(Collectors.toList());
        	transacoesMatched.forEach(transacaoDTO -> {
        		ConciliacaoTransacaoEntity transacaoEntity = transacoesNaoBaixadas.stream().filter(it2 -> it2.getId().equals(transacaoDTO.getId())).findFirst().orElse(null);
        		
        		updateTransacaoEntity(transacaoEntity, transacaoDTO);
        	});
        }
        
        // Marca os itens ainda não baixados, para serem baixados no caixa.
        transacoesNaoBaixadas.stream().filter(it -> isEmpty(it.getTituloConciliadoId()))
	        .forEach(it -> it.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIAR_CAIXA));
        
        List<ConciliacaoTransacaoEntity> transacoesASalvar = transacoesNaoBaixadas.stream().filter(entityTouchedByCaixa()).collect(Collectors.toList());
        
        if (!transacoesASalvar.isEmpty()) {
        	conciliacaoTransacaoService.salvarTransacoes(transacoesASalvar);
        }
        
        contexto.setTransacoes(transacoes);
        
        log.info("FIM verificarModuloCaixa.");
        
		return contexto;
	}
	
	// Item de transação encontrado no caixa e não encontrado no contas a pagar ou contas a receber, ou encontrado no caixa e em um dos módulos de contas. Pode estar na lista de títulos da transação.
	private Predicate<ConciliacaoTransacaoDTO> touchedByCaixa() {
		return dto -> ( SituacaoConciliacaoTrn.CONCILIADO_CAIXA.equals(dto.getSituacaoConciliacaoTrn()) || 
				SituacaoConciliacaoTrn.CAIXA_BAIXADO_SEM_CONCILIACAO.equals(dto.getSituacaoConciliacaoTrn()) ) || 
				isNotEmpty(dto.getConciliacaoTransacaoTitulosDTO()) && 
				dto.getConciliacaoTransacaoTitulosDTO().stream().anyMatch(it -> SituacaoConciliacaoTrn.CONCILIADO_CAIXA.equals(it.getSituacaoConciliacaoTrn()) || 
				SituacaoConciliacaoTrn.CAIXA_BAIXADO_SEM_CONCILIACAO.equals(it.getSituacaoConciliacaoTrn()));
	}
	
	private Predicate<ConciliacaoTransacaoEntity> entityTouchedByCaixa() {
		return entity -> ( 
				SituacaoConciliacaoTrn.CONCILIAR_CAIXA.equals(entity.getSituacaoConciliacaoTrn()) || 
				SituacaoConciliacaoTrn.CONCILIADO_CAIXA.equals(entity.getSituacaoConciliacaoTrn()) || 
				SituacaoConciliacaoTrn.CAIXA_BAIXADO_SEM_CONCILIACAO.equals(entity.getSituacaoConciliacaoTrn()) ) || 
				isNotEmpty(entity.getConciliacaoTransacaoTitulos()) && 
				entity.getConciliacaoTransacaoTitulos().stream().anyMatch(it -> SituacaoConciliacaoTrn.CONCILIADO_CAIXA.equals(it.getSituacaoConciliacaoTrn()) || 
						SituacaoConciliacaoTrn.CONCILIAR_CAIXA.equals(it.getSituacaoConciliacaoTrn()) ||
						SituacaoConciliacaoTrn.CAIXA_BAIXADO_SEM_CONCILIACAO.equals(it.getSituacaoConciliacaoTrn()));
	}
	
	private PlanoContaEntity toEntity(PlanoContaDTO tituloPlanoContas) {
		if (tituloPlanoContas == null) {
			return null;
		}
		
		PlanoContaEntity entity = new PlanoContaEntity();
		entity.setId(tituloPlanoContas.getId());
		//entity.setCodigo(tituloPlanoContas.getCodigo());
		//entity.setDescricao(tituloPlanoContas.getDescricao());
		
		return entity;
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
	        	transacoesMatched.forEach(transacaoDTO -> {
	        		ConciliacaoTransacaoEntity transacaoEntity = transacoesCredito.stream().filter(it2 -> it2.getId().equals(transacaoDTO.getId())).findFirst().orElse(null);
	        		updateTransacaoEntity(transacaoEntity, transacaoDTO);
	        	});
	        }
	        
	        List<ConciliacaoTransacaoEntity> transacoesAlteradas = transacoesCredito.stream().filter(it -> isNotEmpty(it.getTituloConciliadoId())).collect(Collectors.toList());
	        if (!transacoesAlteradas.isEmpty()) {
	        	conciliacaoTransacaoService.salvarTransacoes(transacoesAlteradas);
	        }
	        
			return transacoesCredito;
			
		});
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public long countConciliacaoTransacaoComMaisDeUmTitulo(UUID id) {
		
		long count = conciliacaoServiceHelper.countConciliacaoTransacaoComMaisDeUmTituloCandidato(id);
		
		return count;
	}
	
	
	
	

}
