/**********************************************************************************************
Code generated with MKL Plug-in version: 30.0.1
Code generated at time stamp: 2019-11-10T19:31:44.455
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.conciliacao;

import static br.com.kerubin.api.servicecore.util.CoreUtils.uuids;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.doNothing;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelper;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelperImpl;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicate;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicateImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoService;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoServiceImpl;

@RunWith(SpringRunner.class)
public class ConciliacaoBancariaReprocessarArquivoServiceTest extends ConciliacaoBancariaBaseTest {
	
	
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
	
	@SpyBean
	private ConciliacaoService conciliacaoService;
	
	@Inject
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@Sql("/sql/conciliacao/conciliacao_transacao.sql")
	@Test
	public void testConciliacaoTransacao_findByIdIn() {
		List<UUID> ids = uuids("7d9872d9-892f-49bd-a91d-373ccf6f9658", 
				"ea2b8618-e9ac-41d1-9945-bde034e76eb8", 
				"e0459fdb-0cd0-42b2-944c-25dd98a0eef7");
		
		List<ConciliacaoTransacaoEntity> result = conciliacaoTransacaoService.findByIdIn(ids);
		assertThat(result).isEmpty();
		
		ids = uuids("2714e069-b412-4b82-9dfd-503a423b58a0", "854bf700-a3d6-4506-b840-4922ec6cb982");
		result = conciliacaoTransacaoService.findByIdIn(ids);
		assertThat(result).hasSize(2);		
	}
	
    @Sql("/sql/conciliacao/conciliacao_transacao.sql")
    @Test
    public void testReprocessar() {
    	List<UUID> ids = uuids("2714e069-b412-4b82-9dfd-503a423b58a0", "854bf700-a3d6-4506-b840-4922ec6cb982");
    	
    	doNothing().when(conciliacaoService).reprocessarConciliacaoTransacaoAsync(Mockito.anyList());
    	
    	
    	List<ConciliacaoTransacaoEntity> transacoesBeforeReprocessar = conciliacaoTransacaoRepository.findAllById(ids);
    	List<UUID> ids1 = transacoesBeforeReprocessar.stream().map(it -> it.getConciliacaoBancaria().getId()).collect(Collectors.toList());
    	transacoesBeforeReprocessar.forEach(it -> em.detach(it));
    	
    	conciliacaoService.reprocessar(ids);
    	List<ConciliacaoTransacaoEntity> transacoes = conciliacaoTransacaoRepository.findAllById(ids);
    	List<UUID> ids2 = transacoes.stream().map(it -> it.getConciliacaoBancaria().getId()).collect(Collectors.toList());
    	
    	assertThat(transacoes).hasSize(2)
    	.extracting(ConciliacaoTransacaoEntity::getSituacaoConciliacaoTrn,
    		ConciliacaoTransacaoEntity::getTituloConciliadoId,
    		ConciliacaoTransacaoEntity::getTituloConciliadoDesc,
    		ConciliacaoTransacaoEntity::getTituloConciliadoValor,
    		ConciliacaoTransacaoEntity::getTituloConciliadoDataVen,
    		ConciliacaoTransacaoEntity::getTituloConciliadoDataPag,
    		ConciliacaoTransacaoEntity::getTituloPlanoContas,
    		ConciliacaoTransacaoEntity::getTituloConciliadoMultiple,
    		ConciliacaoTransacaoEntity::getDataConciliacao,
    		ConciliacaoTransacaoEntity::getConciliacaoTransacaoTitulos,
    		ConciliacaoTransacaoEntity::getConciliadoComErro,
    		ConciliacaoTransacaoEntity::getConciliadoMsg
    		)
    	.contains(
    			tuple(SituacaoConciliacaoTrn.NAO_CONCILIADO, null, null, null, null, null, null, null, null, Collections.emptySet(), null, null),
    			tuple(SituacaoConciliacaoTrn.NAO_CONCILIADO, null, null, null, null, null, null, null, null, Collections.emptySet(), null, null)
    			);
    	
    	// Testar aqui os valores antigos que devem ter permanecido inalterados.
    	assertThat(transacoes).hasSize(2)
    	.extracting(ConciliacaoTransacaoEntity::getTrnId,
    		ConciliacaoTransacaoEntity::getTrnData,
    		ConciliacaoTransacaoEntity::getTrnHistorico,
    		ConciliacaoTransacaoEntity::getTrnDocumento,
    		ConciliacaoTransacaoEntity::getTrnTipo,
    		ConciliacaoTransacaoEntity::getTrnValor
    		)
    	.contains(
    			tuple(
	    			transacoesBeforeReprocessar.get(0).getTrnId(), 
	    			transacoesBeforeReprocessar.get(0).getTrnData(),
	    			transacoesBeforeReprocessar.get(0).getTrnHistorico(),
	    			transacoesBeforeReprocessar.get(0).getTrnDocumento(),
	    			transacoesBeforeReprocessar.get(0).getTrnTipo(),
	    			transacoesBeforeReprocessar.get(0).getTrnValor()),
    			tuple(
    					transacoesBeforeReprocessar.get(1).getTrnId(), 
    					transacoesBeforeReprocessar.get(1).getTrnData(),
    					transacoesBeforeReprocessar.get(1).getTrnHistorico(),
    					transacoesBeforeReprocessar.get(1).getTrnDocumento(),
    					transacoesBeforeReprocessar.get(1).getTrnTipo(),
    					transacoesBeforeReprocessar.get(1).getTrnValor())
    			);
    	
    	assertThat(ids1).isEqualTo(ids2);
    	
    }
        
}
