/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.banco;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import javax.inject.Inject;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.kerubin.api.messaging.core.DomainEntityEventsPublisher;
import static org.mockito.Mockito.doAnswer;
import br.com.kerubin.api.messaging.core.DomainEventEnvelope;
import static org.mockito.ArgumentMatchers.any;
import br.com.kerubin.api.messaging.core.DomainEvent;
import br.com.kerubin.api.cadastros.banco.CadastrosBancoConstants;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import br.com.kerubin.api.cadastros.banco.common.PageResult;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.kerubin.api.cadastros.banco.CadastrosBancoBaseEntityTest;


@RunWith(SpringRunner.class)
public class BancoServiceTest extends CadastrosBancoBaseEntityTest {
	
	private static final String[] IGNORED_FIELDS = { "id", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" };
	
	@TestConfiguration
	static class BancoServiceTestConfig {
		
		@Bean
		public BancoListFilterPredicate bancoListFilterPredicate() {
			return new BancoListFilterPredicateImpl();
		}
		
		@Bean
		public BancoService bancoService() {
			return new BancoServiceImpl();
		}
		
		@Bean
		public BancoDTOConverter bancoDTOConverter() {
			return new BancoDTOConverter();
		}
		
	}
	
	
	@Inject
	protected BancoService bancoService;
	
	@Inject
	protected BancoDTOConverter bancoDTOConverter;
	
	@Inject
	protected BancoRepository bancoRepository;
	
	@MockBean
	protected DomainEntityEventsPublisher publisher;
	
	// BEGIN CREATE TESTS
	
	@Test
	public void testCreateWithAllFields() throws Exception {
		Banco banco = new Banco();
		
		banco.setId(java.util.UUID.randomUUID());
		banco.setNumero(generateRandomString(20));
		banco.setNome(generateRandomString(255));
		
		// BEGIN check event created.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			BancoEvent event = (BancoEvent) envelope.getPayload();
			assertThat(event.getId()).isNotNull();
			assertThat(event.getNumero()).isEqualTo(banco.getNumero());
			assertThat(event.getNome()).isEqualTo(banco.getNome());
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("bancoCreated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.Banco").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event created.
		
		BancoEntity bancoEntity = bancoService.create(bancoDTOConverter.convertDtoToEntity(banco));
		em.flush();
		verify(publisher, times(1)).publish(any());
		Banco actual = bancoDTOConverter.convertEntityToDto(bancoEntity);
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(banco, IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testCreateWithOnlyRecairedFields() throws Exception {
		Banco banco = new Banco();
		
		banco.setId(java.util.UUID.randomUUID());
		banco.setNumero(generateRandomString(20));
		banco.setNome(generateRandomString(255));
		
		// BEGIN check event created.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			BancoEvent event = (BancoEvent) envelope.getPayload();
			assertThat(event.getId()).isNotNull();
			assertThat(event.getNumero()).isEqualTo(banco.getNumero());
			assertThat(event.getNome()).isEqualTo(banco.getNome());
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("bancoCreated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.Banco").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event created.
		
		BancoEntity bancoEntity = bancoService.create(bancoDTOConverter.convertDtoToEntity(banco));
		em.flush();
		verify(publisher, times(1)).publish(any());
		Banco actual = bancoDTOConverter.convertEntityToDto(bancoEntity);
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(banco, IGNORED_FIELDS);
		
		
	}
	// END CREATE TESTS
	
	// BEGIN READ TESTS
	
	@Test
	public void testRead1() {
		BancoEntity expectedBancoEntity = newBancoEntity();
		java.util.UUID id = expectedBancoEntity.getId();
		Banco expected = bancoDTOConverter.convertEntityToDto(expectedBancoEntity);
		BancoEntity readBancoEntity = bancoService.read(id);
		Banco actual = bancoDTOConverter.convertEntityToDto(readBancoEntity);
		
		assertThat(actual).isEqualToComparingFieldByField(expected);
		
	}
	// END READ TESTS
	
	// BEGIN UPDATE TESTS
	
	@Test
	public void testUpdateWithAllFields() throws Exception {
		BancoEntity oldBancoEntity = newBancoEntity();
		java.util.UUID id = oldBancoEntity.getId();
				
		Banco banco = new Banco();
		banco.setId(id);
		
		banco.setNumero(generateRandomString(20));
		banco.setNome(generateRandomString(255));
		
		// BEGIN check event updated.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			BancoEvent event = (BancoEvent) envelope.getPayload();
			assertThat(event.getId()).isEqualTo(banco.getId());
			assertThat(event.getNumero()).isEqualTo(banco.getNumero());
			assertThat(event.getNome()).isEqualTo(banco.getNome());
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("bancoUpdated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.Banco").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event updated.
		
		BancoEntity bancoEntity = bancoService.update(id, bancoDTOConverter.convertDtoToEntity(banco));
		em.flush();
		verify(publisher, times(1)).publish(any());
		
		Banco actual = bancoDTOConverter.convertEntityToDto(bancoEntity);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(banco, IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testUpdateWithOnlyRecairedFields() throws Exception {
		BancoEntity oldBancoEntity = newBancoEntity();
		java.util.UUID id = oldBancoEntity.getId();
				
		Banco banco = new Banco();
		banco.setId(id);
		
		banco.setNumero(generateRandomString(20));
		banco.setNome(generateRandomString(255));
		
		// BEGIN check event updated.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			BancoEvent event = (BancoEvent) envelope.getPayload();
			assertThat(event.getId()).isEqualTo(banco.getId());
			assertThat(event.getNumero()).isEqualTo(banco.getNumero());
			assertThat(event.getNome()).isEqualTo(banco.getNome());
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("bancoUpdated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.Banco").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event updated.
		
		BancoEntity bancoEntity = bancoService.update(id, bancoDTOConverter.convertDtoToEntity(banco));
		em.flush();
		verify(publisher, times(1)).publish(any());
		
		Banco actual = bancoDTOConverter.convertEntityToDto(bancoEntity);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(banco, IGNORED_FIELDS);
		
		
	}
	// END UPDATE TESTS
	
	// BEGIN DELETE TESTS
	
	@Test
	public void testDelete1() {
		BancoEntity expected = newBancoEntity();
		java.util.UUID id = expected.getId();
		
		BancoEntity banco = expected;
		
		expected = em.find(BancoEntity.class, id);
		assertThat(expected).isNotNull();
		
		// BEGIN check event deleted.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			BancoEvent event = (BancoEvent) envelope.getPayload();
			assertThat(event.getId()).isEqualTo(banco.getId());
			assertThat(event.getNumero()).isEqualTo(banco.getNumero());
			assertThat(event.getNome()).isEqualTo(banco.getNome());
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("bancoDeleted").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.Banco").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event deleted.
		
		bancoService.delete(id);
		verify(publisher, times(1)).publish(any());
		
		expected = em.find(BancoEntity.class, id);
		assertThat(expected).isNull();
	}
	// END DELETE TESTS
	
	// BEGIN LIST TESTS
	
	@Test
	public void testList_FilteringByNome() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
		
		// Generate 33 records of data for BancoEntity for this test.
		List<BancoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newBancoEntity());
		}
		
		// Check if 33 records of BancoEntity was generated.
		long count = bancoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Creates a list filter for entity Banco.
		BancoListFilter listFilter = new BancoListFilter();
		
		// Extracts 7 records of BancoEntity randomly from testData.
		final int resultSize = 7;
		List<BancoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only BancoEntity.nome field and configure this list as a filter.
		List<String> nomeListFilter = filterTestData.stream().map(BancoEntity::getNome).collect(Collectors.toList());
		listFilter.setNome(nomeListFilter);
		
		// Generates a pageable configuration, without sorting.
		int pageIndex = 0; // First page starts at index zero.
		int size = 33; // Max of 33 records per page.
		Pageable pageable = PageRequest.of(pageIndex, size);
		// Call service list method.
		Page<BancoEntity> page = bancoService.list(listFilter, pageable);
		
		// Converts found entities to DTOs and mount the result page.
		List<Banco> content = page.getContent().stream().map(it -> bancoDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		PageResult<Banco> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		
		// Asserts that result has size 7, in any order and has only rows with nomeListFilter elements based on nome field.
		assertThat(pageResult.getContent())
		.hasSize(7)
		.extracting(Banco::getNome)
		.containsExactlyInAnyOrderElementsOf(nomeListFilter);
		
		// Asserts some page result elements.
		assertThat(pageResult.getNumber()).isEqualTo(pageIndex);
		assertThat(pageResult.getNumberOfElements()).isEqualTo(7);
		assertThat(pageResult.getTotalElements()).isEqualTo(7);
		assertThat(pageResult.getTotalPages()).isEqualTo(1);
		
	}
	
	@Test
	public void testList_FilteringByNomeWithoutResults() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for BancoEntity for this test.
		List<BancoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newBancoEntity());
		}
		
		// Check if 33 records of BancoEntity was generated.
		long count = bancoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Creates a list filter for entity Banco.
		BancoListFilter listFilter = new BancoListFilter();
		
		// Generates a list with only BancoEntity.nome field with 1 not found data in the database and configure this list as a filter.
		List<String> nomeListFilter = Arrays.asList(generateRandomString(255));
		listFilter.setNome(nomeListFilter);
		
		// Generates a pageable configuration, without sorting.
		int pageIndex = 0; // First page starts at index zero.
		int size = 33; // Max of 33 records per page.
		Pageable pageable = PageRequest.of(pageIndex, size);
		// Call service list method.
		Page<BancoEntity> page = bancoService.list(listFilter, pageable);
		
		// Converts found entities to DTOs and mount the result page.
		List<Banco> content = page.getContent().stream().map(it -> bancoDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		PageResult<Banco> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		
		// Asserts that result has size 0 for unknown nome field.
		assertThat(pageResult.getContent()).hasSize(0);
		
	}
	// END LIST TESTS
	
	// BEGIN Autocomplete TESTS
	@Test
	public void testAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for BancoEntity for this test.
		List<BancoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newBancoEntity());
		}
		
		// Check if 33 records of BancoEntity was generated.
		long count = bancoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of BancoEntity randomly from testData.
		final int resultSize = 1;
		List<BancoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only BancoEntity.numero field and configure this list as a filter.
		List<String> numeroListFilter = filterTestData.stream().map(BancoEntity::getNumero).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = numeroListFilter.get(0);
		Collection<BancoAutoComplete> result = bancoService.autoComplete(query);
		
		// Assert BancoAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(BancoAutoComplete::getNumero)
		.containsExactlyInAnyOrderElementsOf(numeroListFilter);
	}
	
	// END Autocomplete TESTS
	
	// BEGIN ListFilter Autocomplete TESTS
	
	@Test
	public void testBancoNomeAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for BancoEntity for this test.
		List<BancoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newBancoEntity());
		}
		
		// Check if 33 records of BancoEntity was generated.
		long count = bancoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of BancoEntity randomly from testData.
		final int resultSize = 1;
		List<BancoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only BancoEntity.nome field and configure this list as a filter.
		List<String> nomeListFilter = filterTestData.stream().map(BancoEntity::getNome).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = nomeListFilter.get(0);
		Collection<BancoNomeAutoComplete> result = bancoService.bancoNomeAutoComplete(query);
		// Assert BancoNomeAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(BancoNomeAutoComplete::getNome)
		.containsExactlyInAnyOrderElementsOf(nomeListFilter);
	}
	
	// END ListFilter Autocomplete TESTS
	
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN TESTS DEPENDENCIES
	
	
	protected BancoEntity newBancoEntity() {
		BancoEntity bancoEntity = new BancoEntity();
		
		bancoEntity.setNumero(generateRandomString(20));
		bancoEntity.setNome(generateRandomString(255));
		
		bancoEntity = em.persistAndFlush(bancoEntity);
		return bancoEntity;
	}
	
	
	protected BancoLookupResult newBancoLookupResult(BancoEntity bancoEntity) {
		BancoLookupResult banco = new BancoLookupResult();
		
		banco.setId(bancoEntity.getId());
		banco.setNumero(bancoEntity.getNumero());
		banco.setNome(bancoEntity.getNome());
		
		return banco;
	}
	// END TESTS DEPENDENCIES

}
