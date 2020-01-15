/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import javax.inject.Inject;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.kerubin.api.messaging.core.DomainEntityEventsPublisher;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collection;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.kerubin.api.cadastros.banco.CadastrosBancoBaseEntityTest;


@RunWith(SpringRunner.class)
public class ConciliacaoBancariaServiceTest extends CadastrosBancoBaseEntityTest {
	
	private static final String[] IGNORED_FIELDS = { "id", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" };
	
	@TestConfiguration
	static class ConciliacaoBancariaServiceTestConfig {
		
		@Bean
		public ConciliacaoBancariaListFilterPredicate conciliacaoBancariaListFilterPredicate() {
			return new ConciliacaoBancariaListFilterPredicateImpl();
		}
		
		@Bean
		public ConciliacaoBancariaService conciliacaoBancariaService() {
			return new ConciliacaoBancariaServiceImpl();
		}
		
		@Bean
		public ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter() {
			return new ConciliacaoBancariaDTOConverter();
		}
		
	}
	
	
	@Inject
	protected ConciliacaoBancariaService conciliacaoBancariaService;
	
	@Inject
	protected ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter;
	
	@Inject
	protected ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@MockBean
	protected DomainEntityEventsPublisher publisher;
	
	// BEGIN CREATE TESTS
	
	@Test
	public void testCreateWithAllFields() throws Exception {
		ConciliacaoBancaria conciliacaoBancaria = new ConciliacaoBancaria();
		
		conciliacaoBancaria.setId(java.util.UUID.randomUUID());
		conciliacaoBancaria.setBancoId(generateRandomString(255));
		conciliacaoBancaria.setAgenciaId(generateRandomString(255));
		conciliacaoBancaria.setContaId(generateRandomString(255));
		conciliacaoBancaria.setDataIni(getNextDate());
		conciliacaoBancaria.setDataFim(getNextDate());
		conciliacaoBancaria.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.create(conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
		em.flush();
		verify(publisher, times(0)).publish(any());
		ConciliacaoBancaria actual = conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity);
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoBancaria, IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testCreateWithOnlyRecairedFields() throws Exception {
		ConciliacaoBancaria conciliacaoBancaria = new ConciliacaoBancaria();
		
		conciliacaoBancaria.setId(java.util.UUID.randomUUID());
		conciliacaoBancaria.setBancoId(generateRandomString(255));
		conciliacaoBancaria.setAgenciaId(generateRandomString(255));
		conciliacaoBancaria.setContaId(generateRandomString(255));
		conciliacaoBancaria.setDataIni(getNextDate());
		conciliacaoBancaria.setDataFim(getNextDate());
		conciliacaoBancaria.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.create(conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
		em.flush();
		verify(publisher, times(0)).publish(any());
		ConciliacaoBancaria actual = conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity);
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoBancaria, IGNORED_FIELDS);
		
		
	}
	// END CREATE TESTS
	
	// BEGIN READ TESTS
	
	@Test
	public void testRead1() {
		ConciliacaoBancariaEntity expectedConciliacaoBancariaEntity = newConciliacaoBancariaEntity();
		java.util.UUID id = expectedConciliacaoBancariaEntity.getId();
		ConciliacaoBancaria expected = conciliacaoBancariaDTOConverter.convertEntityToDto(expectedConciliacaoBancariaEntity);
		ConciliacaoBancariaEntity readConciliacaoBancariaEntity = conciliacaoBancariaService.read(id);
		ConciliacaoBancaria actual = conciliacaoBancariaDTOConverter.convertEntityToDto(readConciliacaoBancariaEntity);
		
		assertThat(actual).isEqualToComparingFieldByField(expected);
		
	}
	// END READ TESTS
	
	// BEGIN UPDATE TESTS
	
	@Test
	public void testUpdateWithAllFields() throws Exception {
		ConciliacaoBancariaEntity oldConciliacaoBancariaEntity = newConciliacaoBancariaEntity();
		java.util.UUID id = oldConciliacaoBancariaEntity.getId();
				
		ConciliacaoBancaria conciliacaoBancaria = new ConciliacaoBancaria();
		conciliacaoBancaria.setId(id);
		
		conciliacaoBancaria.setBancoId(generateRandomString(255));
		conciliacaoBancaria.setAgenciaId(generateRandomString(255));
		conciliacaoBancaria.setContaId(generateRandomString(255));
		conciliacaoBancaria.setDataIni(getNextDate());
		conciliacaoBancaria.setDataFim(getNextDate());
		conciliacaoBancaria.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.update(id, conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
		em.flush();
		verify(publisher, times(0)).publish(any());
		
		ConciliacaoBancaria actual = conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoBancaria, IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testUpdateWithOnlyRecairedFields() throws Exception {
		ConciliacaoBancariaEntity oldConciliacaoBancariaEntity = newConciliacaoBancariaEntity();
		java.util.UUID id = oldConciliacaoBancariaEntity.getId();
				
		ConciliacaoBancaria conciliacaoBancaria = new ConciliacaoBancaria();
		conciliacaoBancaria.setId(id);
		
		conciliacaoBancaria.setBancoId(generateRandomString(255));
		conciliacaoBancaria.setAgenciaId(generateRandomString(255));
		conciliacaoBancaria.setContaId(generateRandomString(255));
		conciliacaoBancaria.setDataIni(getNextDate());
		conciliacaoBancaria.setDataFim(getNextDate());
		conciliacaoBancaria.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.update(id, conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
		em.flush();
		verify(publisher, times(0)).publish(any());
		
		ConciliacaoBancaria actual = conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(conciliacaoBancaria, IGNORED_FIELDS);
		
		
	}
	// END UPDATE TESTS
	
	// BEGIN DELETE TESTS
	
	@Test
	public void testDelete1() {
		ConciliacaoBancariaEntity expected = newConciliacaoBancariaEntity();
		java.util.UUID id = expected.getId();
		
		
		expected = em.find(ConciliacaoBancariaEntity.class, id);
		assertThat(expected).isNotNull();
		conciliacaoBancariaService.delete(id);
		verify(publisher, times(0)).publish(any());
		
		expected = em.find(ConciliacaoBancariaEntity.class, id);
		assertThat(expected).isNull();
	}
	// END DELETE TESTS
	
	// BEGIN LIST TESTS
	// END LIST TESTS
	
	// BEGIN Autocomplete TESTS
	@Test
	public void testAutoComplete() {
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
		// Mount the autocomplete query expression and call it.
		String query = bancoIdListFilter.get(0);
		Collection<ConciliacaoBancariaAutoComplete> result = conciliacaoBancariaService.autoComplete(query);
		
		// Assert ConciliacaoBancariaAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ConciliacaoBancariaAutoComplete::getBancoId)
		.containsExactlyInAnyOrderElementsOf(bancoIdListFilter);
	}
	
	// END Autocomplete TESTS
	
	
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN TESTS DEPENDENCIES
	
	
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
	// END TESTS DEPENDENCIES

}
