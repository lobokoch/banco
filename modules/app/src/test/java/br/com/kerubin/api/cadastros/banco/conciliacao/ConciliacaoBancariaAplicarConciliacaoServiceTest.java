/**********************************************************************************************
Code generated with MKL Plug-in version: 30.0.1
Code generated at time stamp: 2019-11-10T19:31:44.455
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.conciliacao;

import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_CONTASPAGAR_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_CONTASRECEBER_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.FINANCEIRO_FLUXOCAIXA_SERVICE;
import static br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaConstants.HTTP;
import static br.com.kerubin.api.servicecore.util.CoreUtils.toLocalDate;
import static br.com.kerubin.api.servicecore.util.CoreUtils.toPositive;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webcohesion.ofx4j.domain.data.common.Transaction;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoBancariaRuleFunctionsServiceImpl;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelper;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelperImpl;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancaria;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaDTOConverter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaListFilterPredicate;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaListFilterPredicateImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaRuleFunctions;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaService;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicate;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicateImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoService;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoServiceImpl;

@RunWith(SpringRunner.class)
public class ConciliacaoBancariaAplicarConciliacaoServiceTest extends ConciliacaoBancariaBaseTest {
	
	private static final String _CX = "_cx";
	private static final String _CR = "_cr";
	private static final String _CP = "_cp";
	private static final String FLUXO_CAIXA = "fluxo_caixa";
	private static final String CONTAS_RECEBER = "contas_receber";
	private static final String CONTAS_PAGAR = "contas_pagar";
	private static final String BANCO_ID = "237";
	private static final String AGENCIA_ID = "7225";
	private static final String CONTA_BANCARIA_ID = "12563";
	
	private static final List<String> DOCS_CONCILIAR_CONTAS_PAGAR = Arrays.asList("5370001", "0936033", "2208008");
	private static final List<String> DOCS_CONCILIAR_CONTAS_RECEBER = Arrays.asList("4307413", "1020378");
	private static final List<String> DOCS_CONCILIAR_FLUXO_CAIXA = Arrays.asList("0570606", "3694326");
	
	
	@TestConfiguration
	static class ConciliacaoBancariaServiceTestConfig {
		
		@Bean
		public ConciliacaoService conciliacaoService() {
			return new ConciliacaoServiceImpl();
		}
		
		@Bean
		public RestTemplate restTemplate() {
		    return Mockito.mock(RestTemplate.class);
		}
		
		@Bean
		public ConciliacaoServiceHelper conciliacaoServiceHelper() {
			return new ConciliacaoServiceHelperImpl();
		}
		
		@Bean
		public ConciliacaoTransacaoService conciliacaoTransacaoService() {
			return new ConciliacaoTransacaoServiceImpl();
		}
		
		@Bean
		public ConciliacaoTransacaoListFilterPredicate conciliacaoTransacaoListFilterPredicate() {
			return new ConciliacaoTransacaoListFilterPredicateImpl();
		}
		
		@Bean
		public ConciliacaoBancariaListFilterPredicate conciliacaoBancariaListFilterPredicate() {
			return new ConciliacaoBancariaListFilterPredicateImpl();
		}
		
		
		//////
		@Bean
		public ConciliacaoBancariaRuleFunctions conciliacaoBancariaRuleFunctions() {
			return new ConciliacaoBancariaRuleFunctionsServiceImpl();
		}
		
		@Bean
		public ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter() {
			return new ConciliacaoBancariaDTOConverter();
		}
		
		@Bean
		public ConciliacaoBancariaService conciliacaoBancariaService() {
			return new ConciliacaoBancariaServiceImpl();
		}
		
	}
	
	@Inject
	private ConciliacaoTransacaoService conciliacaoTransacaoService;
	
	@Inject
	private RestTemplate restTemplate;
	
	@Inject
	protected ConciliacaoBancariaService conciliacaoBancariaService;
	
	@Inject
	private ConciliacaoBancariaRuleFunctions conciliacaoBancariaRuleFunctions;
	
	@Inject
	protected ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter;
	
	@Inject
	public ConciliacaoServiceHelper conciliacaoServiceHelper;
	
	@Inject
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
    private Map<String, List<ConciliacaoTransacaoDTO>> transacoesProcessadas = new HashMap<>();
    
    @Before
    public void init() {
    	
    }
    
    @Transactional(propagation = Propagation.NEVER)
    @Test
    public void testAplicarConciliacaoBancaria() throws FileNotFoundException, JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {
    	
    	ConciliacaoBancaria conciliacaoBancaria = newConciliacaoBancaria();
    	Collection<ConciliacaoTransacaoEntity> transacoes = prepararPreDados(conciliacaoBancaria);
    	assertThat(transacoes).isNotNull().hasSize(18);
    	
    	// Contas a Pagar
    	String url_ContasPagar = HTTP + FINANCEIRO_CONTASPAGAR_SERVICE + "/aplicarConciliacaoBancaria";
    	doAnswer(invocation -> {
    		HttpEntity<ConciliacaoBancariaDTO> request = invocation.getArgument(2);
    		ConciliacaoBancariaDTO dto = gerarRespostaContasPagar(request.getBody());
    		return ResponseEntity.ok(dto);
    		
    	}).when(restTemplate).exchange(
    			ArgumentMatchers.contains(url_ContasPagar),
                ArgumentMatchers.eq(HttpMethod.POST),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<ConciliacaoBancariaDTO>>any());
    	
    	// Contas a Receber
    	String url_ContasReceber = HTTP + FINANCEIRO_CONTASRECEBER_SERVICE + "/aplicarConciliacaoBancaria";
    	doAnswer(invocation -> {
    		HttpEntity<ConciliacaoBancariaDTO> request = invocation.getArgument(2);
    		ConciliacaoBancariaDTO dto = gerarRespostaContasReceber(request.getBody());
    		return ResponseEntity.ok(dto);
    		
    	}).when(restTemplate).exchange(
    			ArgumentMatchers.contains(url_ContasReceber),
    			ArgumentMatchers.eq(HttpMethod.POST),
    			ArgumentMatchers.any(),
    			ArgumentMatchers.<Class<ConciliacaoBancariaDTO>>any());
    	
    	// Caixa
    	String url_FluxoCaixa = HTTP + FINANCEIRO_FLUXOCAIXA_SERVICE + "/aplicarConciliacaoBancaria";
    	doAnswer(invocation -> {
    		HttpEntity<ConciliacaoBancariaDTO> request = invocation.getArgument(2);
    		ConciliacaoBancariaDTO dto = gerarRespostaFluxoCaixa(request.getBody());
    		return ResponseEntity.ok(dto);
    		
    	}).when(restTemplate).exchange(
    			ArgumentMatchers.contains(url_FluxoCaixa),
    			ArgumentMatchers.eq(HttpMethod.POST),
    			ArgumentMatchers.any(),
    			ArgumentMatchers.<Class<ConciliacaoBancariaDTO>>any());
    		
		
		// ******* EXECUTA O SERVIÇO *********
		ConciliacaoBancariaAsyncExecution execution = ((ConciliacaoBancariaRuleFunctionsServiceImpl) conciliacaoBancariaRuleFunctions)
				.aplicarConciliacaoBancariaAsync(conciliacaoBancaria.getId(), conciliacaoBancaria);
		// ***********************************
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = execution.getConciliacaoBancaria();
    	assertThat(conciliacaoBancariaEntity).isNotNull()
    	.extracting(ConciliacaoBancariaEntity::getId, ConciliacaoBancariaEntity::getSituacaoConciliacao)
    	//.contains(not(null), SituacaoConciliacao.CONCILIANDO_TRANSACOES);
    	.contains(conciliacaoBancaria.getId(), SituacaoConciliacao.CONCILIANDO_TRANSACOES);
    	
    	conciliacaoBancariaEntity = execution.getFuture().get();
    	
    	
    	
    	assertThat(conciliacaoBancariaEntity).isNotNull();
		assertThat(conciliacaoBancariaEntity.getId()).isNotNull();
		
		assertThat(conciliacaoBancariaEntity)
		.extracting(ConciliacaoBancariaEntity::getBancoId,
				ConciliacaoBancariaEntity::getAgenciaId,
				ConciliacaoBancariaEntity::getContaId,
				ConciliacaoBancariaEntity::getDataIni,
				ConciliacaoBancariaEntity::getDataFim,
				ConciliacaoBancariaEntity::getSituacaoConciliacao)
		//.contains(BANCO_ID, AGENCIA_ID, CONTA_BANCARIA_ID, LocalDate.of(2019, 8, 15), LocalDate.of(2019, 10, 14));
		.contains(BANCO_ID, AGENCIA_ID, CONTA_BANCARIA_ID, 
				LocalDate.of(2019, 8, 15-1), LocalDate.of(2019, 10, 14-1), 
				SituacaoConciliacao.CONCILIADO);
		
		transacoes = conciliacaoTransacaoService.findConciliacaoTransacaoByConciliacaoBancaria(conciliacaoBancariaEntity.getId());
		
		int count = 18;
		assertThat(transacoes).isNotNull().hasSize(count);
		
		validarTransacoesDosModulos(CONTAS_PAGAR, transacoes);
		validarTransacoesDosModulos(CONTAS_RECEBER, transacoes);
		validarTransacoesDosModulos(FLUXO_CAIXA, transacoes);
    }
    
    private List<ConciliacaoTransacaoEntity> prepararPreDados(ConciliacaoBancaria conciliacaoBancaria) {
    	ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.create(conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
    	// Gets and sets the generated id
    	conciliacaoBancaria.setId(conciliacaoBancariaEntity.getId());
		
		List<ConciliacaoTransacaoEntity> result = prepararTransacoes(conciliacaoBancariaEntity);
		return result;    	
	}

	private List<ConciliacaoTransacaoEntity> prepararTransacoes(ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
		ConciliacaoOFXReader reader = new ConciliacaoOFXReader();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ofx/Bradesco2.ofx").getFile());
		reader.readOFXFile(file);
		
		List<Transaction> transactions = reader.getTransactions();
		
		// Desconsidera lançamentos futuros. Cuidar que se tiver lançamentos futuros do dia, vão ser computados.
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		transactions = transactions.stream().filter(it -> toLocalDate(it.getDatePosted()).isBefore(tomorrow)).collect(Collectors.toList());
		
		assertThat(transactions).isNotNull().hasSize(18);
		
		List<ConciliacaoTransacaoEntity> transacoes = new ArrayList<>(transactions.size());
		for (Transaction transacao : transactions) {
			ConciliacaoTransacaoEntity entity = new ConciliacaoTransacaoEntity();
			entity.setConciliacaoBancaria(conciliacaoBancariaEntity);
			
			entity.setTrnData(toLocalDate(transacao.getDatePosted()));
			entity.setTrnHistorico(transacao.getMemo());
			entity.setTrnDocumento(reader.getTransactionDocument(transacao));
			
			TipoTransacao trnTipo = TipoTransacao.OUTROS;
			switch (transacao.getTransactionType()) {
			case CREDIT:
				trnTipo = TipoTransacao.CREDITO;
				break;
				
			case DEBIT:
				trnTipo = TipoTransacao.DEBITO;
				break;

			default:
				break;
			}
			entity.setTrnTipo(trnTipo);
			
			BigDecimal valor = BigDecimal.valueOf(transacao.getAmount());
			valor = toPositive(valor); // DEBIT, geralmente virá como valor negativo
			
			entity.setTrnValor(valor);
			
			SituacaoConciliacaoTrn situacao = SituacaoConciliacaoTrn.NAO_CONCILIADO;
			String doc = reader.getTransactionDocument(transacao);
			if (DOCS_CONCILIAR_CONTAS_PAGAR.contains(doc)) {
				situacao = SituacaoConciliacaoTrn.CONCILIAR_CONTAS_PAGAR;
			}
			else if (DOCS_CONCILIAR_CONTAS_RECEBER.contains(doc)) {
				situacao = SituacaoConciliacaoTrn.CONCILIAR_CONTAS_RECEBER;
			}
			else if (DOCS_CONCILIAR_FLUXO_CAIXA.contains(doc)) {
				situacao = SituacaoConciliacaoTrn.CONCILIAR_CAIXA;
			}
			
			entity.setSituacaoConciliacaoTrn(situacao);
			
			entity = conciliacaoTransacaoRepository.saveAndFlush(entity);
			transacoes.add(entity);
		} // for
		
		return transacoes;
		
	}

	private ConciliacaoBancaria newConciliacaoBancaria() {
    	ConciliacaoBancaria conciliacaoBancaria = new ConciliacaoBancaria();
		
		conciliacaoBancaria.setId(null);
		conciliacaoBancaria.setBancoId(BANCO_ID);
		conciliacaoBancaria.setAgenciaId(AGENCIA_ID);
		conciliacaoBancaria.setContaId(CONTA_BANCARIA_ID);
		conciliacaoBancaria.setDataIni(LocalDate.of(2019, 8, 15-1));
		conciliacaoBancaria.setDataFim(LocalDate.of(2019, 10, 14-1));
		conciliacaoBancaria.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		
		return conciliacaoBancaria;
	}

	private void validarTransacoesDosModulos(String modulo, Collection<ConciliacaoTransacaoEntity> transacoes) {
    	List<ConciliacaoTransacaoDTO> tps = transacoesProcessadas.get(modulo);
    	
		assertThat(tps).isNotNull();
		assertThat(tps.size()).isGreaterThan(0);
		
		boolean isContasPagar = CONTAS_PAGAR.contentEquals(modulo);
		boolean isContasReceber = CONTAS_RECEBER.contentEquals(modulo);
		boolean isFluxoCaixa = FLUXO_CAIXA.contentEquals(modulo);
		
		if (isContasPagar) {
			assertThat(tps.size()).isEqualTo(3);
		}
		
		if (isContasReceber) {
			assertThat(tps.size()).isEqualTo(2);
		}
		
		if (isFluxoCaixa) {
			assertThat(tps.size()).isEqualTo(2);
		}
    	
		for (ConciliacaoTransacaoDTO tp: tps) {
			ConciliacaoTransacaoEntity trn = transacoes.stream().filter(it -> it.getId().equals(tp.getId())).findFirst().orElse(null);
			assertThat(trn).isNotNull()
			.extracting(ConciliacaoTransacaoEntity::getSituacaoConciliacaoTrn,
					ConciliacaoTransacaoEntity::getTituloConciliadoId,
					ConciliacaoTransacaoEntity::getTituloConciliadoDesc,
					ConciliacaoTransacaoEntity::getDataConciliacao,
					ConciliacaoTransacaoEntity::getConciliadoComErro,
					ConciliacaoTransacaoEntity::getConciliadoMsg,
					ConciliacaoTransacaoEntity::getTrnData,
					ConciliacaoTransacaoEntity::getTrnHistorico,
					ConciliacaoTransacaoEntity::getTrnDocumento,
					ConciliacaoTransacaoEntity::getTrnTipo,
					ConciliacaoTransacaoEntity::getTrnValor
					)
			.contains(tp.getSituacaoConciliacaoTrn(),
					tp.getTituloConciliadoId(),
					tp.getTituloConciliadoDesc(),
					tp.getDataConciliacao(),
					tp.getConciliadoComErro(),
					tp.getConciliadoMsg(),
					tp.getTrnData(),
					tp.getTrnHistorico(),
					tp.getTrnDocumento(),
					tp.getTrnTipo(),
					tp.getTrnValor()
					);
			
			
			if (isContasPagar) {
				assertThat(trn.getSituacaoConciliacaoTrn()).isEqualTo(SituacaoConciliacaoTrn.CONCILIADO_CONTAS_PAGAR);
				assertThat(trn.getTituloConciliadoDesc()).endsWith(_CP);
			}
			else if (isContasReceber) {
				assertThat(trn.getSituacaoConciliacaoTrn()).isEqualTo(SituacaoConciliacaoTrn.CONCILIADO_CONTAS_RECEBER);
				assertThat(trn.getTituloConciliadoDesc()).endsWith(_CR);
			}
			else if (isFluxoCaixa) {
				assertThat(trn.getSituacaoConciliacaoTrn()).isEqualTo(SituacaoConciliacaoTrn.CONCILIADO_CAIXA);
				assertThat(trn.getTituloConciliadoDesc()).endsWith(_CX);
			}
			else {
				fail("Módulo inválido:" + modulo);
			}
		} //for
		
    }
	
    private ConciliacaoBancariaDTO gerarRespostaContasPagar(ConciliacaoBancariaDTO dto) {
    	List<ConciliacaoTransacaoDTO>  transacoes = dto.getTransacoes();
    	
    	assertThat(transacoes).isNotNull().hasSize(3)
    	.extracting(ConciliacaoTransacaoDTO::getTrnDocumento)
    	.containsExactlyElementsOf(DOCS_CONCILIAR_CONTAS_PAGAR);
    	
    	// Não testando erros ainda.
    	for (ConciliacaoTransacaoDTO transacao : transacoes) {
    		transacao.setConciliadoComErro(false);
			transacao.setConciliadoMsg("Sucesso");
			transacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIADO_CONTAS_PAGAR);
			transacao.setDataConciliacao(LocalDate.now());
			transacao.setTituloConciliadoDesc(transacao.getTrnHistorico() + _CP);
    	}
    	
    	List<ConciliacaoTransacaoDTO> transacoesCopy = new ArrayList<>(transacoes);
    	transacoesProcessadas.put(CONTAS_PAGAR, transacoesCopy);
    	
    	return dto;
	}
    
    private ConciliacaoBancariaDTO gerarRespostaContasReceber(ConciliacaoBancariaDTO dto) {
    	List<ConciliacaoTransacaoDTO>  transacoes = dto.getTransacoes();
    	
    	assertThat(transacoes).isNotNull().hasSize(2)
    	.extracting(ConciliacaoTransacaoDTO::getTrnDocumento)
    	.containsExactlyElementsOf(DOCS_CONCILIAR_CONTAS_RECEBER);
    	
    	// Não testando erros ainda.
    	for (ConciliacaoTransacaoDTO transacao : transacoes) {
    		transacao.setConciliadoComErro(false);
			transacao.setConciliadoMsg("Sucesso");
			transacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIADO_CONTAS_RECEBER);
			transacao.setDataConciliacao(LocalDate.now());
			transacao.setTituloConciliadoDesc(transacao.getTrnHistorico() + _CR);
    	}
    	
    	List<ConciliacaoTransacaoDTO> transacoesCopy = new ArrayList<>(transacoes);
    	transacoesProcessadas.put(CONTAS_RECEBER, transacoesCopy);
    	
    	return dto;
    }
    
    private ConciliacaoBancariaDTO gerarRespostaFluxoCaixa(ConciliacaoBancariaDTO dto) {
    	List<ConciliacaoTransacaoDTO>  transacoes = dto.getTransacoes();
    	
    	assertThat(transacoes).isNotNull().hasSize(2)
    	.extracting(ConciliacaoTransacaoDTO::getTrnDocumento)
    	.containsExactlyElementsOf(DOCS_CONCILIAR_FLUXO_CAIXA);
    	
    	// Não testando erros ainda.
    	for (ConciliacaoTransacaoDTO transacao : transacoes) {
    		transacao.setConciliadoComErro(false);
			transacao.setConciliadoMsg("Sucesso");
			transacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIADO_CAIXA);
			transacao.setDataConciliacao(LocalDate.now());
			transacao.setTituloConciliadoDesc(transacao.getTrnHistorico() + _CX);
    	}
    	
    	List<ConciliacaoTransacaoDTO> transacoesCopy = new ArrayList<>(transacoes);
    	transacoesProcessadas.put(FLUXO_CAIXA, transacoesCopy);
    	
    	return dto;
    }
    
}
