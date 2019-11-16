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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelper;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelperImpl;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicate;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicateImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoService;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoServiceImpl;

@RunWith(SpringRunner.class)
public class ConciliacaoBancariaProcessarArquivoServiceTest extends ConciliacaoBancariaBaseTest {
	
	private static final String BANCO_ID = "237";
	private static final String AGENCIA_ID = "7225";
	private static final String CONTA_BANCARIA_ID = "12563";
	
	
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
		
	}
	
	@Inject
	private ConciliacaoTransacaoService conciliacaoTransacaoService;
	
	@Inject
	private RestTemplate restTemplate;
	
	@Inject
	private ConciliacaoService conciliacaoService;
	
    private Map<String, List<ConciliacaoTransacaoDTO>> transacoesProcessadas = new HashMap<>();
    
    @Before
    public void init() {
    	
    }
    
    @Transactional(propagation = Propagation.NEVER)
    @Test
    public void testProcessarArquivo() throws FileNotFoundException, JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {
    	
    	// Contas a Pagar
    	String url_ContasPagar = HTTP + FINANCEIRO_CONTASPAGAR_SERVICE + "/verificarTransacoes";
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
    	String url_ContasReceber = HTTP + FINANCEIRO_CONTASRECEBER_SERVICE + "/verificarTransacoes";
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
    	String url_FluxoCaixa = HTTP + FINANCEIRO_FLUXOCAIXA_SERVICE + "/verificarTransacoes";
    	doAnswer(invocation -> {
    		HttpEntity<ConciliacaoBancariaDTO> request = invocation.getArgument(2);
    		ConciliacaoBancariaDTO dto = gerarRespostaFluxoCaixa(request.getBody());
    		return ResponseEntity.ok(dto);
    		
    	}).when(restTemplate).exchange(
    			ArgumentMatchers.contains(url_FluxoCaixa),
    			ArgumentMatchers.eq(HttpMethod.POST),
    			ArgumentMatchers.any(),
    			ArgumentMatchers.<Class<ConciliacaoBancariaDTO>>any());
    		
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ofx/Bradesco2.ofx").getFile());
		
		// ******* EXECUTA O SERVIÇO *********
		ConciliacaoBancariaAsyncExecution execution = conciliacaoService.processarArquivo(new FileInputStream(file));
		// ***********************************
		
		assertThat(execution.getConciliacaoBancaria()).isNotNull();
    	assertThat(execution.getConciliacaoBancaria().getId()).isNotNull();
    	
    	ConciliacaoBancariaEntity conciliacaoBancariaEntity = execution.getFuture().get();
    	
    	assertThat(conciliacaoBancariaEntity).isNotNull();
		assertThat(conciliacaoBancariaEntity.getId()).isNotNull();
		
		assertThat(conciliacaoBancariaEntity)
		.extracting(ConciliacaoBancariaEntity::getBancoId,
				ConciliacaoBancariaEntity::getAgenciaId,
				ConciliacaoBancariaEntity::getContaId,
				ConciliacaoBancariaEntity::getDataIni,
				ConciliacaoBancariaEntity::getDataFim)
		//.contains(BANCO_ID, AGENCIA_ID, CONTA_BANCARIA_ID, LocalDate.of(2019, 8, 15), LocalDate.of(2019, 10, 14));
		.contains(BANCO_ID, AGENCIA_ID, CONTA_BANCARIA_ID, LocalDate.of(2019, 8, 15-1), LocalDate.of(2019, 10, 14-1));
		
		Collection<ConciliacaoTransacaoEntity> transacoes = conciliacaoTransacaoService.findConciliacaoTransacaoByConciliacaoBancaria(conciliacaoBancariaEntity.getId());
		
		int count = 18;
		assertThat(transacoes).isNotNull().hasSize(count);
		
		validarTransacoesDosModulos("contas_pagar", transacoes);
		validarTransacoesDosModulos("contas_receber", transacoes);
		validarTransacoesDosModulos("fluxo_caixa", transacoes);
    }
    
    private void validarTransacoesDosModulos(String modulo, Collection<ConciliacaoTransacaoEntity> transacoes) {
    	List<ConciliacaoTransacaoDTO> tps = transacoesProcessadas.get(modulo);
		
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
		}
    }
	
    private ConciliacaoBancariaDTO gerarRespostaContasPagar(ConciliacaoBancariaDTO dto) {
    	List<ConciliacaoTransacaoDTO>  transacoes = dto.getTransacoes().stream().limit(6).collect(Collectors.toList());
    	
    	List<SituacaoConciliacaoTrn> situacoes = Arrays.asList(SituacaoConciliacaoTrn.CONTAS_PAGAR_BAIXADO_SEM_CONCILIACAO,
    			SituacaoConciliacaoTrn.CONCILIADO_CONTAS_PAGAR,
    			SituacaoConciliacaoTrn.CONCILIAR_CONTAS_PAGAR);
    	
    	int index = 0;
    	for (ConciliacaoTransacaoDTO it : transacoes) {
    		SituacaoConciliacaoTrn situacao = situacoes.get(index);
			it.setTituloConciliadoId(UUID.randomUUID()); // já conciliou ou vai conciliar com esse título no contas a pagar
			it.setTituloConciliadoDesc(it.getTrnHistorico() + "_x"); // Essa é a descrição do título no contas a pagar cadastrado pelo usuário
			
			if (SituacaoConciliacaoTrn.CONCILIADO_CONTAS_PAGAR.equals(situacao)) {
				it.setDataConciliacao(LocalDate.of(2019, 11, 15));
			}
			
			it.setSituacaoConciliacaoTrn(situacao);
			index++;
			if (index >= situacoes.size()) {
				index = 0;
			}
    	}
    	
    	List<ConciliacaoTransacaoDTO> transacoesCopy = new ArrayList<>(transacoes);
    	transacoesProcessadas.put("contas_pagar", transacoesCopy);
    	
    	return dto;
	}
    
    private ConciliacaoBancariaDTO gerarRespostaContasReceber(ConciliacaoBancariaDTO dto) {
    	List<ConciliacaoTransacaoDTO>  transacoes = dto.getTransacoes().stream().limit(6).collect(Collectors.toList());
    	
    	List<SituacaoConciliacaoTrn> situacoes = Arrays.asList(SituacaoConciliacaoTrn.CONTAS_RECEBER_BAIXADO_SEM_CONCILIACAO,
    			SituacaoConciliacaoTrn.CONCILIADO_CONTAS_RECEBER,
    			SituacaoConciliacaoTrn.CONCILIAR_CONTAS_RECEBER);
    	
    	int index = 0;
    	for (ConciliacaoTransacaoDTO it : transacoes) {
    		SituacaoConciliacaoTrn situacao = situacoes.get(index);
    		it.setTituloConciliadoId(UUID.randomUUID());
    		it.setTituloConciliadoDesc(it.getTrnHistorico());
    		
    		if (SituacaoConciliacaoTrn.CONCILIADO_CONTAS_RECEBER.equals(situacao)) {
    			it.setDataConciliacao(LocalDate.of(2019, 10, 20));
    		}
    		
    		it.setSituacaoConciliacaoTrn(situacao);
    		index++;
    		if (index >= situacoes.size()) {
    			index = 0;
    		}
    	}
    	
    	List<ConciliacaoTransacaoDTO> transacoesCopy = new ArrayList<>(transacoes);
    	transacoesProcessadas.put("contas_receber", transacoesCopy);
    	
    	return dto;
    }
    
    private ConciliacaoBancariaDTO gerarRespostaFluxoCaixa(ConciliacaoBancariaDTO dto) {
    	List<ConciliacaoTransacaoDTO>  transacoes = dto.getTransacoes() // Pega os restos. Tudo o que não fez match.
    			.stream()
    			.filter(it -> it.getTituloConciliadoId() == null)
    			.collect(Collectors.toList());
    	
    	List<SituacaoConciliacaoTrn> situacoes = Arrays.asList(SituacaoConciliacaoTrn.CAIXA_BAIXADO_SEM_CONCILIACAO,
    			SituacaoConciliacaoTrn.CONCILIADO_CAIXA,
    			SituacaoConciliacaoTrn.CONCILIAR_CAIXA);
    	
    	int index = 0;
    	for (ConciliacaoTransacaoDTO it : transacoes) {
    		SituacaoConciliacaoTrn situacao = situacoes.get(index);
    		if (SituacaoConciliacaoTrn.CAIXA_BAIXADO_SEM_CONCILIACAO.equals(situacao) || SituacaoConciliacaoTrn.CONCILIADO_CAIXA.equals(situacao)) {
    			it.setTituloConciliadoId(UUID.randomUUID());
    			it.setTituloConciliadoDesc(it.getTrnHistorico());
    			//it.setConciliadoComErro(false);
    			//it.setConciliadoMsg("Sucesso");
    			
    			if (SituacaoConciliacaoTrn.CONCILIADO_CAIXA.equals(situacao)) {
    				it.setDataConciliacao(LocalDate.of(2019, 10, 10));
    			}
    		}
    		it.setSituacaoConciliacaoTrn(situacao);
    		index++;
    		if (index >= situacoes.size()) {
    			index = 0;
    		}
    	}
    	
    	List<ConciliacaoTransacaoDTO> transacoesCopy = new ArrayList<>(transacoes);
    	transacoesProcessadas.put("fluxo_caixa", transacoesCopy);
    	
    	return dto;
    }
    
}
