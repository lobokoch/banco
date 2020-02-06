/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaLookupResult;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaLookupResult;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloLookupResult;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import javax.inject.Inject;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaRepository;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.kerubin.api.messaging.core.DomainEntityEventsPublisher;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import br.com.kerubin.api.cadastros.banco.TipoPlanoContaFinanceiro;
import br.com.kerubin.api.cadastros.banco.TipoReceitaDespesa;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoLookupResult;
import java.util.Arrays;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import br.com.kerubin.api.cadastros.banco.common.PageResult;
import java.util.Collection;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloAutoComplete;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.kerubin.api.cadastros.banco.CadastrosBancoBaseEntityTest;


@RunWith(SpringRunner.class)
public class ConciliacaoTransacaoServiceTest extends CadastrosBancoBaseEntityTest {
	
	private static final String[] IGNORED_FIELDS = { "id", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "conciliacaoTransacaoTitulos" };
	
	@TestConfiguration
	static class ConciliacaoTransacaoServiceTestConfig {
		
		@Bean
		public ConciliacaoTransacaoListFilterPredicate conciliacaoTransacaoListFilterPredicate() {
			return new ConciliacaoTransacaoListFilterPredicateImpl();
		}
		
		@Bean
		public ConciliacaoTransacaoService conciliacaoTransacaoService() {
			return new ConciliacaoTransacaoServiceImpl();
		}
		
		@Bean
		public ConciliacaoTransacaoDTOConverter conciliacaoTransacaoDTOConverter() {
			return new ConciliacaoTransacaoDTOConverter();
		}
		
	}
	
	
	@Inject
	protected ConciliacaoTransacaoService conciliacaoTransacaoService;
	
	@Inject
	protected ConciliacaoTransacaoDTOConverter conciliacaoTransacaoDTOConverter;
	
	@Inject
	protected ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@Inject
	protected ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@Inject
	protected PlanoContaRepository planoContaRepository;
	
	@Inject
	protected ConciliacaoTransacaoTituloRepository conciliacaoTransacaoTituloRepository;
	
	@MockBean
	protected DomainEntityEventsPublisher publisher;
	
	// BEGIN CREATE TESTS
	
	@Test
	public void testCreateWithAllFields() throws Exception {
		ConciliacaoTransacaoEntity conciliacaoTransacao = new ConciliacaoTransacaoEntity();
		
		conciliacaoTransacao.setTrnId(generateRandomString(255));
		conciliacaoTransacao.setTrnData(getNextDate());
		conciliacaoTransacao.setTrnHistorico(generateRandomString(255));
		conciliacaoTransacao.setTrnDocumento(generateRandomString(255));
		conciliacaoTransacao.setTrnTipo(TipoTransacao.CREDITO);
		conciliacaoTransacao.setTrnValor(new java.math.BigDecimal("2321.29704"));
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntityParam = newConciliacaoBancariaEntity();
		conciliacaoTransacao.setConciliacaoBancaria(conciliacaoBancariaEntityParam);
		
		conciliacaoTransacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
		conciliacaoTransacao.setTituloConciliadoId(java.util.UUID.randomUUID());
		conciliacaoTransacao.setTituloConciliadoDesc(generateRandomString(255));
		conciliacaoTransacao.setTituloConciliadoValor(new java.math.BigDecimal("12105.25533"));
		conciliacaoTransacao.setTituloConciliadoDataVen(getNextDate());
		conciliacaoTransacao.setTituloConciliadoDataPag(getNextDate());
		
		PlanoContaEntity planoContaEntityParam = newPlanoContaEntity();
		conciliacaoTransacao.setTituloPlanoContas(planoContaEntityParam);
		
		conciliacaoTransacao.setDataConciliacao(getNextDate());
		
		ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntityParam = newConciliacaoTransacaoTituloEntity(conciliacaoTransacao);
		conciliacaoTransacao.setConciliacaoTransacaoTitulos(Arrays.asList(conciliacaoTransacaoTituloEntityParam));
		
		conciliacaoTransacao.setConciliadoComErro(false);
		conciliacaoTransacao.setConciliadoMsg(generateRandomString(255));
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.create(conciliacaoTransacao);
		em.flush();
		verify(publisher, times(0)).publish(any());
		ConciliacaoTransacaoEntity actual = conciliacaoTransacaoEntity;
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoTransacao, IGNORED_FIELDS);
		
		
		assertThat(actual.getConciliacaoBancaria().getId()).isNotNull();
		assertThat(actual.getConciliacaoBancaria()).isEqualToIgnoringGivenFields(conciliacaoTransacao.getConciliacaoBancaria(), IGNORED_FIELDS);
		
		
		assertThat(actual.getTituloPlanoContas().getId()).isNotNull();
		assertThat(actual.getTituloPlanoContas()).isEqualToIgnoringGivenFields(conciliacaoTransacao.getTituloPlanoContas(), IGNORED_FIELDS);
		
		
		assertThat(actual.getConciliacaoTransacaoTitulos()).isNotNull();
		assertThat(actual.getConciliacaoTransacaoTitulos()).hasSize(1);
		assertThat(actual.getConciliacaoTransacaoTitulos().get(0).getId()).isNotNull();
		assertThat(actual.getConciliacaoTransacaoTitulos().get(0)).isEqualToIgnoringGivenFields(conciliacaoTransacao.getConciliacaoTransacaoTitulos().get(0), IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testCreateWithOnlyRecairedFields() throws Exception {
		ConciliacaoTransacaoEntity conciliacaoTransacao = new ConciliacaoTransacaoEntity();
		
		conciliacaoTransacao.setTrnId(generateRandomString(255));
		conciliacaoTransacao.setTrnData(getNextDate());
		conciliacaoTransacao.setTrnHistorico(generateRandomString(255));
		conciliacaoTransacao.setTrnDocumento(generateRandomString(255));
		conciliacaoTransacao.setTrnTipo(TipoTransacao.CREDITO);
		conciliacaoTransacao.setTrnValor(new java.math.BigDecimal("19905.6850"));
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntityParam = newConciliacaoBancariaEntity();
		conciliacaoTransacao.setConciliacaoBancaria(conciliacaoBancariaEntityParam);
		
		conciliacaoTransacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.create(conciliacaoTransacao);
		em.flush();
		verify(publisher, times(0)).publish(any());
		ConciliacaoTransacaoEntity actual = conciliacaoTransacaoEntity;
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoTransacao, IGNORED_FIELDS);
		
		
		assertThat(actual.getConciliacaoBancaria().getId()).isNotNull();
		assertThat(actual.getConciliacaoBancaria()).isEqualToIgnoringGivenFields(conciliacaoTransacao.getConciliacaoBancaria(), IGNORED_FIELDS);
		
		assertThat(actual.getTituloPlanoContas()).isNull();
		assertThat(actual.getConciliacaoTransacaoTitulos()).isEmpty();
		
	}
	// END CREATE TESTS
	
	// BEGIN READ TESTS
	
	@Test
	public void testRead1() {
		ConciliacaoTransacaoEntity expectedConciliacaoTransacaoEntity = newConciliacaoTransacaoEntity();
		java.util.UUID id = expectedConciliacaoTransacaoEntity.getId();
		ConciliacaoTransacao expected = conciliacaoTransacaoDTOConverter.convertEntityToDto(expectedConciliacaoTransacaoEntity);
		ConciliacaoTransacaoEntity readConciliacaoTransacaoEntity = conciliacaoTransacaoService.read(id);
		ConciliacaoTransacao actual = conciliacaoTransacaoDTOConverter.convertEntityToDto(readConciliacaoTransacaoEntity);
		
		assertThat(actual).isEqualToComparingFieldByField(expected);
		
	}
	// END READ TESTS
	
	// BEGIN UPDATE TESTS
	
	@Test
	public void testUpdateWithAllFields() throws Exception {
		ConciliacaoTransacaoEntity oldConciliacaoTransacaoEntity = newConciliacaoTransacaoEntity();
		java.util.UUID id = oldConciliacaoTransacaoEntity.getId();
				
		ConciliacaoTransacaoEntity conciliacaoTransacao = new ConciliacaoTransacaoEntity();
		conciliacaoTransacao.setId(id);
		
		conciliacaoTransacao.setTrnId(generateRandomString(255));
		conciliacaoTransacao.setTrnData(getNextDate());
		conciliacaoTransacao.setTrnHistorico(generateRandomString(255));
		conciliacaoTransacao.setTrnDocumento(generateRandomString(255));
		conciliacaoTransacao.setTrnTipo(TipoTransacao.CREDITO);
		conciliacaoTransacao.setTrnValor(new java.math.BigDecimal("538.24720"));
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntityParam = newConciliacaoBancariaEntity();
		conciliacaoTransacao.setConciliacaoBancaria(conciliacaoBancariaEntityParam);
		
		conciliacaoTransacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
		conciliacaoTransacao.setTituloConciliadoId(java.util.UUID.randomUUID());
		conciliacaoTransacao.setTituloConciliadoDesc(generateRandomString(255));
		conciliacaoTransacao.setTituloConciliadoValor(new java.math.BigDecimal("7155.6969"));
		conciliacaoTransacao.setTituloConciliadoDataVen(getNextDate());
		conciliacaoTransacao.setTituloConciliadoDataPag(getNextDate());
		
		PlanoContaEntity planoContaEntityParam = newPlanoContaEntity();
		conciliacaoTransacao.setTituloPlanoContas(planoContaEntityParam);
		
		conciliacaoTransacao.setDataConciliacao(getNextDate());
		
		ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntityParam = newConciliacaoTransacaoTituloEntity(conciliacaoTransacao);
		conciliacaoTransacao.setConciliacaoTransacaoTitulos(Arrays.asList(conciliacaoTransacaoTituloEntityParam));
		
		conciliacaoTransacao.setConciliadoComErro(false);
		conciliacaoTransacao.setConciliadoMsg(generateRandomString(255));
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.update(id, conciliacaoTransacao);
		em.flush();
		verify(publisher, times(0)).publish(any());
		
		ConciliacaoTransacaoEntity actual = conciliacaoTransacaoEntity;
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoTransacao, IGNORED_FIELDS);
		
		
		assertThat(actual.getConciliacaoBancaria().getId()).isNotNull();
		assertThat(actual.getConciliacaoBancaria()).isEqualToIgnoringGivenFields(conciliacaoTransacao.getConciliacaoBancaria(), IGNORED_FIELDS);
		
		
		assertThat(actual.getTituloPlanoContas().getId()).isNotNull();
		assertThat(actual.getTituloPlanoContas()).isEqualToIgnoringGivenFields(conciliacaoTransacao.getTituloPlanoContas(), IGNORED_FIELDS);
		
		
		assertThat(actual.getConciliacaoTransacaoTitulos()).isNotNull();
		assertThat(actual.getConciliacaoTransacaoTitulos()).hasSize(1);
		assertThat(actual.getConciliacaoTransacaoTitulos().get(0).getId()).isNotNull();
		assertThat(actual.getConciliacaoTransacaoTitulos().get(0)).isEqualToIgnoringGivenFields(conciliacaoTransacao.getConciliacaoTransacaoTitulos().get(0), IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testUpdateWithOnlyRecairedFields() throws Exception {
		ConciliacaoTransacaoEntity oldConciliacaoTransacaoEntity = newConciliacaoTransacaoEntity();
		java.util.UUID id = oldConciliacaoTransacaoEntity.getId();
				
		ConciliacaoTransacaoEntity conciliacaoTransacao = new ConciliacaoTransacaoEntity();
		conciliacaoTransacao.setId(id);
		
		conciliacaoTransacao.setTrnId(generateRandomString(255));
		conciliacaoTransacao.setTrnData(getNextDate());
		conciliacaoTransacao.setTrnHistorico(generateRandomString(255));
		conciliacaoTransacao.setTrnDocumento(generateRandomString(255));
		conciliacaoTransacao.setTrnTipo(TipoTransacao.CREDITO);
		conciliacaoTransacao.setTrnValor(new java.math.BigDecimal("17106.5677"));
		
		ConciliacaoBancariaEntity conciliacaoBancariaEntityParam = newConciliacaoBancariaEntity();
		conciliacaoTransacao.setConciliacaoBancaria(conciliacaoBancariaEntityParam);
		
		conciliacaoTransacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.update(id, conciliacaoTransacao);
		em.flush();
		verify(publisher, times(0)).publish(any());
		
		ConciliacaoTransacaoEntity actual = conciliacaoTransacaoEntity;
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoTransacao, IGNORED_FIELDS);
		
		
		assertThat(actual.getConciliacaoBancaria().getId()).isNotNull();
		assertThat(actual.getConciliacaoBancaria()).isEqualToIgnoringGivenFields(conciliacaoTransacao.getConciliacaoBancaria(), IGNORED_FIELDS);
		
		assertThat(actual.getTituloPlanoContas()).isNull();
		assertThat(actual.getConciliacaoTransacaoTitulos()).isEmpty();
		
	}
	// END UPDATE TESTS
	
	// BEGIN DELETE TESTS
	
	@Test
	public void testDelete1() {
		ConciliacaoTransacaoEntity expected = newConciliacaoTransacaoEntity();
		java.util.UUID id = expected.getId();
		
		
		expected = em.find(ConciliacaoTransacaoEntity.class, id);
		assertThat(expected).isNotNull();
		conciliacaoTransacaoService.delete(id);
		verify(publisher, times(0)).publish(any());
		
		expected = em.find(ConciliacaoTransacaoEntity.class, id);
		assertThat(expected).isNull();
	}
	// END DELETE TESTS
	
	// BEGIN LIST TESTS
	
	@Test
	public void testList_FilteringByTrnHistorico() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
		
		// Generate 33 records of data for ConciliacaoTransacaoEntity for this test.
		List<ConciliacaoTransacaoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newConciliacaoTransacaoEntity());
		}
		
		// Check if 33 records of ConciliacaoTransacaoEntity was generated.
		long count = conciliacaoTransacaoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Creates a list filter for entity ConciliacaoTransacao.
		ConciliacaoTransacaoListFilter listFilter = new ConciliacaoTransacaoListFilter();
		
		// Extracts 7 records of ConciliacaoTransacaoEntity randomly from testData.
		final int resultSize = 7;
		List<ConciliacaoTransacaoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ConciliacaoTransacaoEntity.trnHistorico field and configure this list as a filter.
		List<String> trnHistoricoListFilter = filterTestData.stream().map(ConciliacaoTransacaoEntity::getTrnHistorico).collect(Collectors.toList());
		listFilter.setTrnHistorico(trnHistoricoListFilter);
		
		// Generates a pageable configuration, without sorting.
		int pageIndex = 0; // First page starts at index zero.
		int size = 33; // Max of 33 records per page.
		Pageable pageable = PageRequest.of(pageIndex, size);
		// Call service list method.
		Page<ConciliacaoTransacaoEntity> page = conciliacaoTransacaoService.list(listFilter, pageable);
		
		// Converts found entities to DTOs and mount the result page.
		List<ConciliacaoTransacao> content = page.getContent().stream().map(it -> conciliacaoTransacaoDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		PageResult<ConciliacaoTransacao> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		
		// Asserts that result has size 7, in any order and has only rows with trnHistoricoListFilter elements based on trnHistorico field.
		assertThat(pageResult.getContent())
		.hasSize(7)
		.extracting(ConciliacaoTransacao::getTrnHistorico)
		.containsExactlyInAnyOrderElementsOf(trnHistoricoListFilter);
		
		// Asserts some page result elements.
		assertThat(pageResult.getNumber()).isEqualTo(pageIndex);
		assertThat(pageResult.getNumberOfElements()).isEqualTo(7);
		assertThat(pageResult.getTotalElements()).isEqualTo(7);
		assertThat(pageResult.getTotalPages()).isEqualTo(1);
		
	}
	
	@Test
	public void testList_FilteringByTrnHistoricoWithoutResults() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ConciliacaoTransacaoEntity for this test.
		List<ConciliacaoTransacaoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newConciliacaoTransacaoEntity());
		}
		
		// Check if 33 records of ConciliacaoTransacaoEntity was generated.
		long count = conciliacaoTransacaoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Creates a list filter for entity ConciliacaoTransacao.
		ConciliacaoTransacaoListFilter listFilter = new ConciliacaoTransacaoListFilter();
		
		// Generates a list with only ConciliacaoTransacaoEntity.trnHistorico field with 1 not found data in the database and configure this list as a filter.
		List<String> trnHistoricoListFilter = Arrays.asList(generateRandomString(255));
		listFilter.setTrnHistorico(trnHistoricoListFilter);
		
		// Generates a pageable configuration, without sorting.
		int pageIndex = 0; // First page starts at index zero.
		int size = 33; // Max of 33 records per page.
		Pageable pageable = PageRequest.of(pageIndex, size);
		// Call service list method.
		Page<ConciliacaoTransacaoEntity> page = conciliacaoTransacaoService.list(listFilter, pageable);
		
		// Converts found entities to DTOs and mount the result page.
		List<ConciliacaoTransacao> content = page.getContent().stream().map(it -> conciliacaoTransacaoDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		PageResult<ConciliacaoTransacao> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		
		// Asserts that result has size 0 for unknown trnHistorico field.
		assertThat(pageResult.getContent()).hasSize(0);
		
	}
	// END LIST TESTS
	
	// BEGIN Autocomplete TESTS
	@Test
	public void testAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ConciliacaoTransacaoEntity for this test.
		List<ConciliacaoTransacaoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newConciliacaoTransacaoEntity());
		}
		
		// Check if 33 records of ConciliacaoTransacaoEntity was generated.
		long count = conciliacaoTransacaoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ConciliacaoTransacaoEntity randomly from testData.
		final int resultSize = 1;
		List<ConciliacaoTransacaoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ConciliacaoTransacaoEntity.trnId field and configure this list as a filter.
		List<String> trnIdListFilter = filterTestData.stream().map(ConciliacaoTransacaoEntity::getTrnId).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = trnIdListFilter.get(0);
		Collection<ConciliacaoTransacaoAutoComplete> result = conciliacaoTransacaoService.autoComplete(query);
		
		// Assert ConciliacaoTransacaoAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ConciliacaoTransacaoAutoComplete::getTrnId)
		.containsExactlyInAnyOrderElementsOf(trnIdListFilter);
	}
	
	// END Autocomplete TESTS
	
	// BEGIN ListFilter Autocomplete TESTS
	
	@Test
	public void testConciliacaoTransacaoTrnHistoricoAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ConciliacaoTransacaoEntity for this test.
		List<ConciliacaoTransacaoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newConciliacaoTransacaoEntity());
		}
		
		// Check if 33 records of ConciliacaoTransacaoEntity was generated.
		long count = conciliacaoTransacaoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ConciliacaoTransacaoEntity randomly from testData.
		final int resultSize = 1;
		List<ConciliacaoTransacaoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ConciliacaoTransacaoEntity.trnHistorico field and configure this list as a filter.
		List<String> trnHistoricoListFilter = filterTestData.stream().map(ConciliacaoTransacaoEntity::getTrnHistorico).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = trnHistoricoListFilter.get(0);
		Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> result = conciliacaoTransacaoService.conciliacaoTransacaoTrnHistoricoAutoComplete(query);
		// Assert ConciliacaoTransacaoTrnHistoricoAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ConciliacaoTransacaoTrnHistoricoAutoComplete::getTrnHistorico)
		.containsExactlyInAnyOrderElementsOf(trnHistoricoListFilter);
	}
	
	
	@Test
	public void testConciliacaoTransacaoTrnDocumentoAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ConciliacaoTransacaoEntity for this test.
		List<ConciliacaoTransacaoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newConciliacaoTransacaoEntity());
		}
		
		// Check if 33 records of ConciliacaoTransacaoEntity was generated.
		long count = conciliacaoTransacaoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ConciliacaoTransacaoEntity randomly from testData.
		final int resultSize = 1;
		List<ConciliacaoTransacaoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ConciliacaoTransacaoEntity.trnDocumento field and configure this list as a filter.
		List<String> trnDocumentoListFilter = filterTestData.stream().map(ConciliacaoTransacaoEntity::getTrnDocumento).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = trnDocumentoListFilter.get(0);
		Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> result = conciliacaoTransacaoService.conciliacaoTransacaoTrnDocumentoAutoComplete(query);
		// Assert ConciliacaoTransacaoTrnDocumentoAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ConciliacaoTransacaoTrnDocumentoAutoComplete::getTrnDocumento)
		.containsExactlyInAnyOrderElementsOf(trnDocumentoListFilter);
	}
	
	// END ListFilter Autocomplete TESTS
	
	// BEGIN Relationships Autocomplete TESTS
	
	@Test
	public void testConciliacaoTransacaoConciliacaoBancariaAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ConciliacaoBancariaEntity for this test.
		List<ConciliacaoBancariaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newConciliacaoBancariaEntity());
		}
		
		// Check if 33 records of ConciliacaoBancariaEntity was generated.
		long count = conciliacaoBancariaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ConciliacaoBancariaEntity randomly from testData.
		final int resultSize = 1;
		List<ConciliacaoBancariaEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ConciliacaoBancariaEntity.bancoId field and configure this list as a filter.
		List<String> bancoIdListFilter = filterTestData.stream().map(ConciliacaoBancariaEntity::getBancoId).collect(Collectors.toList());
		String query = bancoIdListFilter.get(0);
		
		Collection<ConciliacaoBancariaAutoComplete> result = conciliacaoTransacaoService.conciliacaoBancariaConciliacaoBancariaAutoComplete(query);
		
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ConciliacaoBancariaAutoComplete::getBancoId)
		.containsExactlyInAnyOrderElementsOf(bancoIdListFilter);
	}
	
	
	@Test
	public void testConciliacaoTransacaoTituloPlanoContasAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for PlanoContaEntity for this test.
		List<PlanoContaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newPlanoContaEntity());
		}
		
		// Check if 33 records of PlanoContaEntity was generated.
		long count = planoContaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of PlanoContaEntity randomly from testData.
		final int resultSize = 1;
		List<PlanoContaEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only PlanoContaEntity.codigo field and configure this list as a filter.
		List<String> codigoListFilter = filterTestData.stream().map(PlanoContaEntity::getCodigo).collect(Collectors.toList());
		String query = codigoListFilter.get(0);
		
		
		ConciliacaoTransacao conciliacaoTransacao = null;
		
		Collection<PlanoContaAutoComplete> result = conciliacaoTransacaoService.planoContaTituloPlanoContasAutoComplete(query, conciliacaoTransacao);
		
		assertThat(result).isNotNull().hasSize(1)
		.extracting(PlanoContaAutoComplete::getCodigo)
		.containsExactlyInAnyOrderElementsOf(codigoListFilter);
	}
	
	
	@Test
	public void testConciliacaoTransacaoConciliacaoTransacaoTitulosAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ConciliacaoTransacaoTituloEntity for this test.
		List<ConciliacaoTransacaoTituloEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = newConciliacaoTransacaoEntity(lastRecord);
		testData.addAll(conciliacaoTransacaoEntity.getConciliacaoTransacaoTitulos());
		
		// Check if 33 records of ConciliacaoTransacaoTituloEntity was generated.
		long count = conciliacaoTransacaoTituloRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ConciliacaoTransacaoTituloEntity randomly from testData.
		final int resultSize = 1;
		List<ConciliacaoTransacaoTituloEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ConciliacaoTransacaoTituloEntity.tituloConciliadoDesc field and configure this list as a filter.
		List<String> tituloConciliadoDescListFilter = filterTestData.stream().map(ConciliacaoTransacaoTituloEntity::getTituloConciliadoDesc).collect(Collectors.toList());
		String query = tituloConciliadoDescListFilter.get(0);
		
		Collection<ConciliacaoTransacaoTituloAutoComplete> result = conciliacaoTransacaoService.conciliacaoTransacaoTituloConciliacaoTransacaoTitulosAutoComplete(query);
		
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ConciliacaoTransacaoTituloAutoComplete::getTituloConciliadoDesc)
		.containsExactlyInAnyOrderElementsOf(tituloConciliadoDescListFilter);
	}
	
	// END Relationships Autocomplete TESTS
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN TESTS DEPENDENCIES
	protected ConciliacaoTransacaoEntity newConciliacaoTransacaoEntity() {
		return newConciliacaoTransacaoEntity(2);
	}
	
	
	
	protected ConciliacaoTransacaoEntity newConciliacaoTransacaoEntity(int oneToManySize) {
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = new ConciliacaoTransacaoEntity();
		
		conciliacaoTransacaoEntity.setTrnId(generateRandomString(255));
		conciliacaoTransacaoEntity.setTrnData(getNextDate());
		conciliacaoTransacaoEntity.setTrnHistorico(generateRandomString(255));
		conciliacaoTransacaoEntity.setTrnDocumento(generateRandomString(255));
		conciliacaoTransacaoEntity.setTrnTipo(TipoTransacao.CREDITO);
		conciliacaoTransacaoEntity.setTrnValor(new java.math.BigDecimal("11817.15978"));
		conciliacaoTransacaoEntity.setConciliacaoBancaria(newConciliacaoBancariaEntity());
		conciliacaoTransacaoEntity.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
		conciliacaoTransacaoEntity.setTituloConciliadoId(java.util.UUID.randomUUID());
		conciliacaoTransacaoEntity.setTituloConciliadoDesc(generateRandomString(255));
		conciliacaoTransacaoEntity.setTituloConciliadoValor(new java.math.BigDecimal("28069.7476"));
		conciliacaoTransacaoEntity.setTituloConciliadoDataVen(getNextDate());
		conciliacaoTransacaoEntity.setTituloConciliadoDataPag(getNextDate());
		conciliacaoTransacaoEntity.setTituloPlanoContas(newPlanoContaEntity());
		conciliacaoTransacaoEntity.setDataConciliacao(getNextDate());
		conciliacaoTransacaoEntity.setConciliadoComErro(false);
		conciliacaoTransacaoEntity.setConciliadoMsg(generateRandomString(255));
		
		conciliacaoTransacaoEntity.setConciliacaoTransacaoTitulos(newConciliacaoTransacaoTituloEntity(conciliacaoTransacaoEntity, oneToManySize));
		conciliacaoTransacaoEntity = em.persistAndFlush(conciliacaoTransacaoEntity);
		return conciliacaoTransacaoEntity;
	}
	
	
	protected ConciliacaoTransacaoLookupResult newConciliacaoTransacaoLookupResult(ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		ConciliacaoTransacaoLookupResult conciliacaoTransacao = new ConciliacaoTransacaoLookupResult();
		
		conciliacaoTransacao.setId(conciliacaoTransacaoEntity.getId());
		conciliacaoTransacao.setTrnId(conciliacaoTransacaoEntity.getTrnId());
		
		return conciliacaoTransacao;
	}
	
	
	protected ConciliacaoBancariaEntity newConciliacaoBancariaEntity() {
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = new ConciliacaoBancariaEntity();
		
		conciliacaoBancariaEntity.setBancoId(generateRandomString(255));
		conciliacaoBancariaEntity.setAgenciaId(generateRandomString(255));
		conciliacaoBancariaEntity.setContaId(generateRandomString(255));
		conciliacaoBancariaEntity.setDataIni(getNextDate());
		conciliacaoBancariaEntity.setDataFim(getNextDate());
		conciliacaoBancariaEntity.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		
		conciliacaoBancariaEntity = em.persistAndFlush(conciliacaoBancariaEntity);
		return conciliacaoBancariaEntity;
	}
	
	
	protected ConciliacaoBancariaLookupResult newConciliacaoBancariaLookupResult(ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
		ConciliacaoBancariaLookupResult conciliacaoBancaria = new ConciliacaoBancariaLookupResult();
		
		conciliacaoBancaria.setId(conciliacaoBancariaEntity.getId());
		conciliacaoBancaria.setBancoId(conciliacaoBancariaEntity.getBancoId());
		
		return conciliacaoBancaria;
	}
	
	
	protected PlanoContaEntity newPlanoContaEntity() {
		PlanoContaEntity planoContaEntity = new PlanoContaEntity();
		
		planoContaEntity.setId(java.util.UUID.randomUUID());
		planoContaEntity.setCodigo(generateRandomString(255));
		planoContaEntity.setDescricao(generateRandomString(255));
		planoContaEntity.setTipoFinanceiro(TipoPlanoContaFinanceiro.RECEITA);
		planoContaEntity.setTipoReceitaDespesa(TipoReceitaDespesa.VARIAVEL);
		planoContaEntity.setPlanoContaPai(null);
		planoContaEntity.setAtivo(true);
		planoContaEntity.setDeleted(false);
		
		planoContaEntity = em.persistAndFlush(planoContaEntity);
		return planoContaEntity;
	}
	
	
	protected PlanoContaLookupResult newPlanoContaLookupResult(PlanoContaEntity planoContaEntity) {
		PlanoContaLookupResult planoConta = new PlanoContaLookupResult();
		
		planoConta.setId(planoContaEntity.getId());
		planoConta.setCodigo(planoContaEntity.getCodigo());
		planoConta.setDescricao(planoContaEntity.getDescricao());
		
		return planoConta;
	}
	
	protected List<ConciliacaoTransacaoTituloEntity> newConciliacaoTransacaoTituloEntity(ConciliacaoTransacaoEntity conciliacaoTransacao, int oneToManySize) {
		List<ConciliacaoTransacaoTituloEntity> items = new ArrayList<>(oneToManySize);
		for(int i = 0; i < oneToManySize; i++) {
			items.add(newConciliacaoTransacaoTituloEntity(conciliacaoTransacao));
		}
		return items;
	}
	
	
	protected ConciliacaoTransacaoTituloEntity newConciliacaoTransacaoTituloEntity(ConciliacaoTransacaoEntity conciliacaoTransacao) {
		ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity = new ConciliacaoTransacaoTituloEntity();
		
		conciliacaoTransacaoTituloEntity.setConciliacaoTransacao(conciliacaoTransacao);
		conciliacaoTransacaoTituloEntity.setTituloConciliadoId(java.util.UUID.randomUUID());
		conciliacaoTransacaoTituloEntity.setTituloConciliadoDesc(generateRandomString(255));
		conciliacaoTransacaoTituloEntity.setTituloConciliadoValor(new java.math.BigDecimal("19253.38"));
		conciliacaoTransacaoTituloEntity.setTituloConciliadoDataVen(getNextDate());
		conciliacaoTransacaoTituloEntity.setTituloConciliadoDataPag(getNextDate());
		conciliacaoTransacaoTituloEntity.setTituloPlanoContas(newPlanoContaEntity());
		conciliacaoTransacaoTituloEntity.setDataConciliacao(getNextDate());
		conciliacaoTransacaoTituloEntity.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
		
		return conciliacaoTransacaoTituloEntity;
	}
	
	
	protected ConciliacaoTransacaoTituloLookupResult newConciliacaoTransacaoTituloLookupResult(ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity) {
		ConciliacaoTransacaoTituloLookupResult conciliacaoTransacaoTitulo = new ConciliacaoTransacaoTituloLookupResult();
		
		conciliacaoTransacaoTitulo.setId(conciliacaoTransacaoTituloEntity.getId());
		conciliacaoTransacaoTitulo.setTituloConciliadoDesc(conciliacaoTransacaoTituloEntity.getTituloConciliadoDesc());
		
		return conciliacaoTransacaoTitulo;
	}
	// END TESTS DEPENDENCIES

}
