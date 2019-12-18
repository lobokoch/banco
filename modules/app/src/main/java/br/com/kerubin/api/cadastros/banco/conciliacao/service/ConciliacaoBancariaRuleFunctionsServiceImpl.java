package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.buildHttpHeaders;
import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.toDTO;
import static br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoUtils.toEntity;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.HTTP;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.MODULOS_FINANCEIRO;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.MODULOS_FINANCEIRO_DESC;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.MODULO_FINANCEIRO_CONTASPAGAR;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.MODULO_FINANCEIRO_CONTASRECEBER;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.MODULO_FINANCEIRO_FLUXOCAIXA;
import static br.com.kerubin.api.servicecore.util.CoreUtils.format;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import br.com.kerubin.api.cadastros.banco.conciliacao.exception.ConciliacaoBancariaException;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoContext;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancaria;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaDTOConverter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaRuleFunctions;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.database.core.ServiceContext;
import br.com.kerubin.api.database.core.ServiceContextData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciliacaoBancariaRuleFunctionsServiceImpl implements ConciliacaoBancariaRuleFunctions {
	
	@Inject
	private ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter;
	
	@Inject
	private ConciliacaoServiceHelper conciliacaoServiceHelper;
	
	@Inject
	private ConciliacaoServiceHelper conciliacaoTransacaoService;
	
	@Inject
	private RestTemplate restTemplate;
	
	@Override
	public ConciliacaoBancariaEntity aplicarConciliacaoBancaria(UUID id, ConciliacaoBancaria conciliacaoBancaria) {
		
		validarConciliacao(id);
		
		ConciliacaoBancariaAsyncExecution execAsync = aplicarConciliacaoBancariaAsync(id, conciliacaoBancaria);
		
		return execAsync.getConciliacaoBancaria();
	}

	private void validarConciliacao(UUID id) {
		validarConciliacaoTransacaoComTituloRepetido(id);
		validarConciliacaoTransacaoComMaisDeUmTituloCandidato(id);
		
	}

	private void validarConciliacaoTransacaoComTituloRepetido(UUID id) {
		long count = conciliacaoServiceHelper.countConciliacaoTransacaoComTitulosRepetidos(id);
		if (count > 0) {
			String message = MessageFormat.format("Existem {0} títulos, do contas a pagar e/ou contas a receber, que estão associados a mais de uma transação, " + 
					"sendo que um título pode estar associado a apenas uma transação. Por favor corrija e tente novamente.", count);
			throw new ConciliacaoBancariaException(message);
		}
		
	}

	private void validarConciliacaoTransacaoComMaisDeUmTituloCandidato(UUID id) {
		// Validar se tem lançamentos com mais de 1 título candidato.
		long count = conciliacaoServiceHelper.countConciliacaoTransacaoComMaisDeUmTituloCandidato(id);
		if (count > 0) {
			String message = MessageFormat.format("Existem {0} transações com mais de 1 título associado para coniliação, sendo que pode haver no máximo um título para cada transação. Por favor corrija e tente novamente.", count);
			throw new ConciliacaoBancariaException(message);
		}
	}
	
	public ConciliacaoBancariaAsyncExecution aplicarConciliacaoBancariaAsync(UUID id, ConciliacaoBancaria conciliacaoBancaria) {
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria);
		
		conciliacaoBancariaEntity = salvarConciliandoTransacoes(conciliacaoBancariaEntity);
		
		CompletableFuture<ConciliacaoBancariaEntity> future = aplicarConciliacaoBancariaAsync(conciliacaoBancariaEntity);
		
		ConciliacaoBancariaAsyncExecution result = ConciliacaoBancariaAsyncExecution.builder()
				.conciliacaoBancaria(conciliacaoBancariaEntity)
				.future(future)
				.build();
		
		return result;
	}
	
	private ConciliacaoBancariaEntity salvarConciliandoTransacoes(ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
		conciliacaoBancariaEntity.setSituacaoConciliacao(SituacaoConciliacao.CONCILIANDO_TRANSACOES);
		conciliacaoBancariaEntity = conciliacaoServiceHelper.salvarConciliacao(conciliacaoBancariaEntity);
		return conciliacaoBancariaEntity;
	}

	private CompletableFuture<ConciliacaoBancariaEntity> aplicarConciliacaoBancariaAsync(ConciliacaoBancariaEntity conciliacaoBancariaEntity) {

		ConciliacaoContext contextoInicial = ConciliacaoContext.builder()
				.conciliacaoBancariaEntity(conciliacaoBancariaEntity)
				.serviceContextData(ServiceContext.getServiceContextData())
				.build();
		
		return CompletableFuture
			.supplyAsync(() -> buscarTransacoesAConciliar(contextoInicial, conciliacaoBancariaEntity))
			.thenApply(contexto -> applicarConciliacaoNosModulos(contexto))
			.thenApply(contexto -> finalizarProcessamentoConciliacao(contexto));
	}

	private ConciliacaoContext buscarTransacoesAConciliar(ConciliacaoContext contexto,
			ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
		
		log.info("INICIO buscarTransacoesAConciliar para id: {} ...", conciliacaoBancariaEntity.getId());
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		
		List<ConciliacaoTransacaoEntity> transacoes =  conciliacaoTransacaoService.buscarTransacoesAConciliar(conciliacaoBancariaEntity);
		contexto.setTransacoes(transacoes);
		
		log.info("FIM buscarTransacoesAConciliar para id: {}, quantidade transações: {} ...", conciliacaoBancariaEntity.getId(), transacoes.size());
		
		return contexto;
	}
	
	private ConciliacaoContext applicarConciliacaoNosModulos(ConciliacaoContext contexto) {
		
		log.info("INICIO applicarConciliacaoNosModulos...");
		
		List<ConciliacaoTransacaoEntity> transacoes = contexto.getTransacoes();
		
		// Obter as transações do contas a pagar.
		List<ConciliacaoTransacaoEntity> transacoesContasPagar = transacoes.stream()
				.filter(it -> SituacaoConciliacaoTrn.CONCILIAR_CONTAS_PAGAR.equals(it.getSituacaoConciliacaoTrn())).collect(Collectors.toList());
		
		List<ConciliacaoTransacaoEntity> transacoesContasReceber = transacoes.stream()
				.filter(it -> SituacaoConciliacaoTrn.CONCILIAR_CONTAS_RECEBER.equals(it.getSituacaoConciliacaoTrn())).collect(Collectors.toList());
		
		List<ConciliacaoTransacaoEntity> transacoesFluxoCaixa = transacoes.stream()
				.filter(it -> SituacaoConciliacaoTrn.CONCILIAR_CAIXA.equals(it.getSituacaoConciliacaoTrn())).collect(Collectors.toList());
		
		CompletableFuture<List<ConciliacaoTransacaoEntity>> transacoesContasPagarFuture = aplicarConciliacaoContasPagar(contexto, transacoesContasPagar);
		CompletableFuture<List<ConciliacaoTransacaoEntity>> transacoesContasReceberFuture = aplicarConciliacaoContasReceber(contexto, transacoesContasReceber);
		CompletableFuture<List<ConciliacaoTransacaoEntity>> transacoesFluxoCaixaFuture = aplicarConciliacaoFluxoCaixa(contexto, transacoesFluxoCaixa);
		
		List<CompletableFuture<List<ConciliacaoTransacaoEntity>>> futures = Arrays.asList(transacoesContasPagarFuture, 
				transacoesContasReceberFuture, 
				transacoesFluxoCaixaFuture);
		
		CompletableFuture<Void> allOfFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		
		CompletableFuture<List<List<ConciliacaoTransacaoEntity>>> transacoesAplicadasNosServicosFuture = allOfFutures.thenApply(v -> {
			
			List<List<ConciliacaoTransacaoEntity>> resultadosDosServicos = futures.stream().map(future -> future.join()).collect(Collectors.toList());
			return resultadosDosServicos;
		});

		
		List<List<ConciliacaoTransacaoEntity>> transacoesAplicadasNosServicos;
		try {
			// Aguarda até processar tudo nos serviços de Contas a Pagar e Contas a Receber.
			transacoesAplicadasNosServicos = transacoesAplicadasNosServicosFuture.get();
			
			List<ConciliacaoTransacaoEntity> processadasA = transacoesAplicadasNosServicos.get(0);
			List<ConciliacaoTransacaoEntity> processadasB = transacoesAplicadasNosServicos.get(1);
			List<ConciliacaoTransacaoEntity> processadasC = transacoesAplicadasNosServicos.get(2);
			
			List<ConciliacaoTransacaoEntity> todasProcessadas = new ArrayList<>();
			
			todasProcessadas.addAll(processadasA);
			todasProcessadas.addAll(processadasB);
			todasProcessadas.addAll(processadasC);
			
			contexto.setTransacoes(todasProcessadas);
			
			log.info("FIM1 applicarConciliacaoNosModulos...");
			
			return contexto;
			
		} catch (InterruptedException | ExecutionException e) {
			log.error("Erro ao buscar verificar transações nos serviços.", e);
		}
		
		log.info("FIM2 applicarConciliacaoNosModulos...");
		return contexto;
	}
	
	private CompletableFuture<List<ConciliacaoTransacaoEntity>> aplicarConciliacaoContasPagar(
			ConciliacaoContext contexto, List<ConciliacaoTransacaoEntity> transacoes) {
		
		return aplicarConciliacao(contexto, transacoes, MODULO_FINANCEIRO_CONTASPAGAR);
		
	}
	
	private CompletableFuture<List<ConciliacaoTransacaoEntity>> aplicarConciliacaoContasReceber(
			ConciliacaoContext contexto, List<ConciliacaoTransacaoEntity> transacoes) {
		
		return aplicarConciliacao(contexto, transacoes, MODULO_FINANCEIRO_CONTASRECEBER);
		
	}
	
	private CompletableFuture<List<ConciliacaoTransacaoEntity>> aplicarConciliacaoFluxoCaixa(
			ConciliacaoContext contexto, List<ConciliacaoTransacaoEntity> transacoes) {
		
		return aplicarConciliacao(contexto, transacoes, MODULO_FINANCEIRO_FLUXOCAIXA);
		
	}
	
	
	private CompletableFuture<List<ConciliacaoTransacaoEntity>> aplicarConciliacao(
			ConciliacaoContext contexto, List<ConciliacaoTransacaoEntity> transacoes, int moduloIndex) {
		
		return CompletableFuture.supplyAsync(() -> {
			
			String modulo = MODULOS_FINANCEIRO[moduloIndex];
			log.info("BEGIN aplicarConciliacao para módulo: {}", modulo);
			
			if (transacoes == null || transacoes.isEmpty()) {
				return Collections.emptyList();
			}
			
			ServiceContextData serviceContextData = contexto.getServiceContextData();
			
			ServiceContext.applyServiceContextData(serviceContextData);
			
			ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
			
			ConciliacaoBancariaDTO conciliacaoBancariaDTO = null;
			try {
				conciliacaoBancariaDTO = toDTO(conciliacaoBancariaEntity, transacoes);
			} catch (Exception e) {
				String msg = MessageFormat.format("Error at toDTO:{0}", e.getMessage());
				log.error(msg, e);
				
				transacoes.forEach(it -> {
					it.setConciliadoComErro(true);
					it.setConciliadoMsg(msg);
				});
				
				try {
					conciliacaoTransacaoService.salvarTransacoes(transacoes);
				} catch (Exception e2) {
					log.error(MessageFormat.format("Error saving error at toDTO:{0}", e.getMessage()), e);
				}
				return transacoes;
			}
			
			String url = HTTP + modulo + "/aplicarConciliacaoBancaria";
			
			HttpHeaders headers = buildHttpHeaders(serviceContextData);
			
			HttpEntity<ConciliacaoBancariaDTO> request = new HttpEntity<ConciliacaoBancariaDTO>(conciliacaoBancariaDTO, headers);
			ResponseEntity<ConciliacaoBancariaDTO> response = null;
			
			log.info("ANTES de chamar: {}", url);
			try {
				response = restTemplate.exchange(url, HttpMethod.POST, request, ConciliacaoBancariaDTO.class);
			} catch (Exception e) {
				String msg = MessageFormat.format("Erro ao executar URL:{0}, erro: {1}", url, e.getMessage());
				log.error(msg, e);
				
				transacoes.forEach(it -> {
					it.setConciliadoComErro(true);
					it.setConciliadoMsg(msg);
				});
				
				try {
					conciliacaoTransacaoService.salvarTransacoes(transacoes);
				} catch (Exception e2) {
					log.error(MessageFormat.format("Erro ao salvar erro ao executar URL:{0}, erro: {1}", url, e.getMessage()), e);
				}
				
				return transacoes;
			}
			log.info("DEPOIS de chamar: {}", url);
			
			ConciliacaoBancariaDTO serviceResponse = response.getBody();
			
			List<ConciliacaoTransacaoDTO> transacoesResponse = (serviceResponse != null && serviceResponse.getTransacoes() != null) ? 
					serviceResponse.getTransacoes() : Collections.emptyList();
					
			// Checa as respostas
			transacoes.forEach(transacao -> {
				
				ConciliacaoTransacaoDTO trnDto = transacoesResponse.stream().filter(it -> it.getId().equals(transacao.getId())).findFirst().orElse(null);
				if (trnDto != null) {
					//transacao.setTituloConciliadoId(trnDto.getTituloConciliadoId());
					transacao.setTituloConciliadoDesc(trnDto.getTituloConciliadoDesc());
					transacao.setSituacaoConciliacaoTrn(trnDto.getSituacaoConciliacaoTrn());
					transacao.setConciliadoComErro(trnDto.getConciliadoComErro());
					transacao.setConciliadoMsg(trnDto.getConciliadoMsg());
					transacao.setDataConciliacao(trnDto.getDataConciliacao());
					transacao.setTituloPlanoContas(toEntity(trnDto.getTituloPlanoContas()));
				}
				else {
					log.error(format("Service não retornou a transação id: {0}, trnDoc: {1}, trnHis: {2}", 
							transacao.getId(), transacao.getTrnDocumento(), transacao.getTrnHistorico()));
					
					//transacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.ERRO);
					transacao.setConciliadoComErro(true);
					transacao.setConciliadoMsg("Item não devolvido pelo módulo de " + MODULOS_FINANCEIRO_DESC[moduloIndex] + ".");
					transacao.setDataConciliacao(null);
				}
				
			});
			
			conciliacaoTransacaoService.salvarTransacoes(transacoes);
			
			log.info("FIM aplicarConciliacao para módulo: {}", modulo);
			
			return transacoes;
					
		});
		
	}

	private ConciliacaoBancariaEntity finalizarProcessamentoConciliacao(ConciliacaoContext contexto) {
		
		log.info("INICIO finalizarProcessamentoConciliacao...");
		
		ServiceContextData serviceContextData = contexto.getServiceContextData();
		
		ServiceContext.applyServiceContextData(serviceContextData);
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
		
		List<ConciliacaoTransacaoEntity> transacoes = contexto.getTransacoes();
		
		long processadasComErro = transacoes.stream().filter(it -> it.getConciliadoComErro()).count();
		
		if (processadasComErro > 0) {
			log.warn("Transações processadas com erro: {}.", processadasComErro);
		}
		else {
			log.info("Transações processadas com erro: {}.", processadasComErro);
		}
		
		SituacaoConciliacao situacaoConciliacao = processadasComErro == 0 ? SituacaoConciliacao.CONCILIADO : SituacaoConciliacao.CONCILIADO_COM_ERRO; 
		conciliacaoBancariaEntity.setSituacaoConciliacao(situacaoConciliacao);
		conciliacaoBancariaEntity = conciliacaoServiceHelper.salvarConciliacao(conciliacaoBancariaEntity);	
		
		log.info("FIM finalizarProcessamentoConciliacao.");
		
		return conciliacaoBancariaEntity;
	}

}
