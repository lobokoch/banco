/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;

import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaLookupResult;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoLookupResult;
import br.com.kerubin.api.cadastros.banco.TipoContaBancaria;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import javax.inject.Inject;
import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaRepository;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.kerubin.api.messaging.core.DomainEntityEventsPublisher;
import br.com.kerubin.api.cadastros.banco.entity.banco.BancoEntity;
import br.com.kerubin.api.cadastros.banco.entity.banco.BancoLookupResult;
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
import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoAutoComplete;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.kerubin.api.cadastros.banco.CadastrosBancoBaseEntityTest;


@RunWith(SpringRunner.class)
public class ContaBancariaServiceTest extends CadastrosBancoBaseEntityTest {
	
	private static final String[] IGNORED_FIELDS = { "id", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" };
	
	@TestConfiguration
	static class ContaBancariaServiceTestConfig {
		
		@Bean
		public ContaBancariaListFilterPredicate contaBancariaListFilterPredicate() {
			return new ContaBancariaListFilterPredicateImpl();
		}
		
		@Bean
		public ContaBancariaService contaBancariaService() {
			return new ContaBancariaServiceImpl();
		}
		
		@Bean
		public ContaBancariaDTOConverter contaBancariaDTOConverter() {
			return new ContaBancariaDTOConverter();
		}
		
	}
	
	
	@Inject
	protected ContaBancariaService contaBancariaService;
	
	@Inject
	protected ContaBancariaDTOConverter contaBancariaDTOConverter;
	
	@Inject
	protected ContaBancariaRepository contaBancariaRepository;
	
	@Inject
	protected AgenciaBancariaRepository agenciaBancariaRepository;
	
	@Inject
	protected BandeiraCartaoRepository bandeiraCartaoRepository;
	
	@MockBean
	protected DomainEntityEventsPublisher publisher;
	
	// BEGIN CREATE TESTS
	
	@Test
	public void testCreateWithAllFields() throws Exception {
		ContaBancaria contaBancaria = new ContaBancaria();
		
		contaBancaria.setId(java.util.UUID.randomUUID());
		contaBancaria.setNomeTitular(generateRandomString(255));
		contaBancaria.setCpfCnpjTitular("92020472007");
		contaBancaria.setTipoContaBancaria(TipoContaBancaria.CONTA_CORRENTE);
		
		AgenciaBancariaEntity agenciaBancariaEntityParam = newAgenciaBancariaEntity();
		AgenciaBancariaLookupResult agencia = newAgenciaBancariaLookupResult(agenciaBancariaEntityParam);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setNumeroConta(generateRandomString(30));
		contaBancaria.setDigito(generateRandomString(10));
		contaBancaria.setSaldo(new java.math.BigDecimal("516.17232"));
		contaBancaria.setMaisOpcoes(false);
		contaBancaria.setAtivo(true);
		contaBancaria.setDataValidade(getNextDate());
		contaBancaria.setNumeroCartao(generateRandomString(50));
		contaBancaria.setCodigoSeguranca(generateRandomString(10));
		
		BandeiraCartaoEntity bandeiraCartaoEntityParam = newBandeiraCartaoEntity();
		BandeiraCartaoLookupResult bandeiraCartao = newBandeiraCartaoLookupResult(bandeiraCartaoEntityParam);
		contaBancaria.setBandeiraCartao(bandeiraCartao);
		
		
		// BEGIN check event created.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			ContaBancariaEvent event = (ContaBancariaEvent) envelope.getPayload();
			assertThat(event.getId()).isNotNull();
			assertThat(event.getNomeTitular()).isEqualTo(contaBancaria.getNomeTitular());
			assertThat(event.getCpfCnpjTitular()).isEqualTo(contaBancaria.getCpfCnpjTitular());
			assertThat(event.getTipoContaBancaria()).isEqualTo(contaBancaria.getTipoContaBancaria());
			
			if (contaBancaria.getAgencia() == null) {
				assertThat(event.getAgencia()).isNull();
			}
			else {
				assertThat(event.getAgencia()).isEqualTo(contaBancaria.getAgencia().getId());
			}
			
			assertThat(event.getNumeroConta()).isEqualTo(contaBancaria.getNumeroConta());
			assertThat(event.getDigito()).isEqualTo(contaBancaria.getDigito());
			assertThat(event.getAtivo()).isEqualTo(contaBancaria.getAtivo());
			assertThat(event.getDataValidade()).isEqualTo(contaBancaria.getDataValidade());
			
			if (contaBancaria.getBandeiraCartao() == null) {
				assertThat(event.getBandeiraCartao()).isNull();
			}
			else {
				assertThat(event.getBandeiraCartao()).isEqualTo(contaBancaria.getBandeiraCartao().getId());
			}
			
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("contaBancariaCreated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.ContaBancaria").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event created.
		
		ContaBancariaEntity contaBancariaEntity = contaBancariaService.create(contaBancariaDTOConverter.convertDtoToEntity(contaBancaria));
		em.flush();
		verify(publisher, times(1)).publish(any());
		ContaBancaria actual = contaBancariaDTOConverter.convertEntityToDto(contaBancariaEntity);
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(contaBancaria, IGNORED_FIELDS);
		
		
		assertThat(actual.getAgencia().getId()).isNotNull();
		assertThat(actual.getAgencia()).isEqualToIgnoringGivenFields(contaBancaria.getAgencia(), IGNORED_FIELDS);
		
		
		assertThat(actual.getBandeiraCartao().getId()).isNotNull();
		assertThat(actual.getBandeiraCartao()).isEqualToIgnoringGivenFields(contaBancaria.getBandeiraCartao(), IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testCreateWithOnlyRecairedFields() throws Exception {
		ContaBancaria contaBancaria = new ContaBancaria();
		
		contaBancaria.setId(java.util.UUID.randomUUID());
		contaBancaria.setNomeTitular(generateRandomString(255));
		contaBancaria.setTipoContaBancaria(TipoContaBancaria.CONTA_CORRENTE);
		
		AgenciaBancariaEntity agenciaBancariaEntityParam = newAgenciaBancariaEntity();
		AgenciaBancariaLookupResult agencia = newAgenciaBancariaLookupResult(agenciaBancariaEntityParam);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setNumeroConta(generateRandomString(30));
		contaBancaria.setAtivo(true);
		
		// BEGIN check event created.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			ContaBancariaEvent event = (ContaBancariaEvent) envelope.getPayload();
			assertThat(event.getId()).isNotNull();
			assertThat(event.getNomeTitular()).isEqualTo(contaBancaria.getNomeTitular());
			assertThat(event.getCpfCnpjTitular()).isEqualTo(contaBancaria.getCpfCnpjTitular());
			assertThat(event.getTipoContaBancaria()).isEqualTo(contaBancaria.getTipoContaBancaria());
			
			if (contaBancaria.getAgencia() == null) {
				assertThat(event.getAgencia()).isNull();
			}
			else {
				assertThat(event.getAgencia()).isEqualTo(contaBancaria.getAgencia().getId());
			}
			
			assertThat(event.getNumeroConta()).isEqualTo(contaBancaria.getNumeroConta());
			assertThat(event.getDigito()).isEqualTo(contaBancaria.getDigito());
			assertThat(event.getAtivo()).isEqualTo(contaBancaria.getAtivo());
			assertThat(event.getDataValidade()).isEqualTo(contaBancaria.getDataValidade());
			
			if (contaBancaria.getBandeiraCartao() == null) {
				assertThat(event.getBandeiraCartao()).isNull();
			}
			else {
				assertThat(event.getBandeiraCartao()).isEqualTo(contaBancaria.getBandeiraCartao().getId());
			}
			
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("contaBancariaCreated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.ContaBancaria").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event created.
		
		ContaBancariaEntity contaBancariaEntity = contaBancariaService.create(contaBancariaDTOConverter.convertDtoToEntity(contaBancaria));
		em.flush();
		verify(publisher, times(1)).publish(any());
		ContaBancaria actual = contaBancariaDTOConverter.convertEntityToDto(contaBancariaEntity);
		
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(contaBancaria, IGNORED_FIELDS);
		
		
		assertThat(actual.getAgencia().getId()).isNotNull();
		assertThat(actual.getAgencia()).isEqualToIgnoringGivenFields(contaBancaria.getAgencia(), IGNORED_FIELDS);
		
		assertThat(actual.getBandeiraCartao()).isNull();
		
	}
	// END CREATE TESTS
	
	// BEGIN READ TESTS
	
	@Test
	public void testRead1() {
		ContaBancariaEntity expectedContaBancariaEntity = newContaBancariaEntity();
		java.util.UUID id = expectedContaBancariaEntity.getId();
		ContaBancaria expected = contaBancariaDTOConverter.convertEntityToDto(expectedContaBancariaEntity);
		ContaBancariaEntity readContaBancariaEntity = contaBancariaService.read(id);
		ContaBancaria actual = contaBancariaDTOConverter.convertEntityToDto(readContaBancariaEntity);
		
		assertThat(actual).isEqualToComparingFieldByField(expected);
		
	}
	// END READ TESTS
	
	// BEGIN UPDATE TESTS
	
	@Test
	public void testUpdateWithAllFields() throws Exception {
		ContaBancariaEntity oldContaBancariaEntity = newContaBancariaEntity();
		java.util.UUID id = oldContaBancariaEntity.getId();
				
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setId(id);
		
		contaBancaria.setNomeTitular(generateRandomString(255));
		contaBancaria.setCpfCnpjTitular("92020472007");
		contaBancaria.setTipoContaBancaria(TipoContaBancaria.CONTA_CORRENTE);
		
		AgenciaBancariaEntity agenciaBancariaEntityParam = newAgenciaBancariaEntity();
		AgenciaBancariaLookupResult agencia = newAgenciaBancariaLookupResult(agenciaBancariaEntityParam);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setNumeroConta(generateRandomString(30));
		contaBancaria.setDigito(generateRandomString(10));
		contaBancaria.setSaldo(new java.math.BigDecimal("30684.3256"));
		contaBancaria.setMaisOpcoes(false);
		contaBancaria.setAtivo(true);
		contaBancaria.setDataValidade(getNextDate());
		contaBancaria.setNumeroCartao(generateRandomString(50));
		contaBancaria.setCodigoSeguranca(generateRandomString(10));
		
		BandeiraCartaoEntity bandeiraCartaoEntityParam = newBandeiraCartaoEntity();
		BandeiraCartaoLookupResult bandeiraCartao = newBandeiraCartaoLookupResult(bandeiraCartaoEntityParam);
		contaBancaria.setBandeiraCartao(bandeiraCartao);
		
		
		// BEGIN check event updated.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			ContaBancariaEvent event = (ContaBancariaEvent) envelope.getPayload();
			assertThat(event.getId()).isEqualTo(contaBancaria.getId());
			assertThat(event.getNomeTitular()).isEqualTo(contaBancaria.getNomeTitular());
			assertThat(event.getCpfCnpjTitular()).isEqualTo(contaBancaria.getCpfCnpjTitular());
			assertThat(event.getTipoContaBancaria()).isEqualTo(contaBancaria.getTipoContaBancaria());
			
			if (contaBancaria.getAgencia() == null) {
				assertThat(event.getAgencia()).isNull();
			}
			else {
				assertThat(event.getAgencia()).isEqualTo(contaBancaria.getAgencia().getId());
			}
			
			assertThat(event.getNumeroConta()).isEqualTo(contaBancaria.getNumeroConta());
			assertThat(event.getDigito()).isEqualTo(contaBancaria.getDigito());
			assertThat(event.getAtivo()).isEqualTo(contaBancaria.getAtivo());
			assertThat(event.getDataValidade()).isEqualTo(contaBancaria.getDataValidade());
			
			if (contaBancaria.getBandeiraCartao() == null) {
				assertThat(event.getBandeiraCartao()).isNull();
			}
			else {
				assertThat(event.getBandeiraCartao()).isEqualTo(contaBancaria.getBandeiraCartao().getId());
			}
			
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("contaBancariaUpdated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.ContaBancaria").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event updated.
		
		ContaBancariaEntity contaBancariaEntity = contaBancariaService.update(id, contaBancariaDTOConverter.convertDtoToEntity(contaBancaria));
		em.flush();
		verify(publisher, times(1)).publish(any());
		
		ContaBancaria actual = contaBancariaDTOConverter.convertEntityToDto(contaBancariaEntity);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(contaBancaria, IGNORED_FIELDS);
		
		
		assertThat(actual.getAgencia().getId()).isNotNull();
		assertThat(actual.getAgencia()).isEqualToIgnoringGivenFields(contaBancaria.getAgencia(), IGNORED_FIELDS);
		
		
		assertThat(actual.getBandeiraCartao().getId()).isNotNull();
		assertThat(actual.getBandeiraCartao()).isEqualToIgnoringGivenFields(contaBancaria.getBandeiraCartao(), IGNORED_FIELDS);
		
		
	}
	
	@Test
	public void testUpdateWithOnlyRecairedFields() throws Exception {
		ContaBancariaEntity oldContaBancariaEntity = newContaBancariaEntity();
		java.util.UUID id = oldContaBancariaEntity.getId();
				
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setId(id);
		
		contaBancaria.setNomeTitular(generateRandomString(255));
		contaBancaria.setTipoContaBancaria(TipoContaBancaria.CONTA_CORRENTE);
		
		AgenciaBancariaEntity agenciaBancariaEntityParam = newAgenciaBancariaEntity();
		AgenciaBancariaLookupResult agencia = newAgenciaBancariaLookupResult(agenciaBancariaEntityParam);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setNumeroConta(generateRandomString(30));
		contaBancaria.setAtivo(true);
		
		// BEGIN check event updated.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			ContaBancariaEvent event = (ContaBancariaEvent) envelope.getPayload();
			assertThat(event.getId()).isEqualTo(contaBancaria.getId());
			assertThat(event.getNomeTitular()).isEqualTo(contaBancaria.getNomeTitular());
			assertThat(event.getCpfCnpjTitular()).isEqualTo(contaBancaria.getCpfCnpjTitular());
			assertThat(event.getTipoContaBancaria()).isEqualTo(contaBancaria.getTipoContaBancaria());
			
			if (contaBancaria.getAgencia() == null) {
				assertThat(event.getAgencia()).isNull();
			}
			else {
				assertThat(event.getAgencia()).isEqualTo(contaBancaria.getAgencia().getId());
			}
			
			assertThat(event.getNumeroConta()).isEqualTo(contaBancaria.getNumeroConta());
			assertThat(event.getDigito()).isEqualTo(contaBancaria.getDigito());
			assertThat(event.getAtivo()).isEqualTo(contaBancaria.getAtivo());
			assertThat(event.getDataValidade()).isEqualTo(contaBancaria.getDataValidade());
			
			if (contaBancaria.getBandeiraCartao() == null) {
				assertThat(event.getBandeiraCartao()).isNull();
			}
			else {
				assertThat(event.getBandeiraCartao()).isEqualTo(contaBancaria.getBandeiraCartao().getId());
			}
			
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("contaBancariaUpdated").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.ContaBancaria").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event updated.
		
		ContaBancariaEntity contaBancariaEntity = contaBancariaService.update(id, contaBancariaDTOConverter.convertDtoToEntity(contaBancaria));
		em.flush();
		verify(publisher, times(1)).publish(any());
		
		ContaBancaria actual = contaBancariaDTOConverter.convertEntityToDto(contaBancariaEntity);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isNotNull();
		assertThat(actual).isEqualToIgnoringGivenFields(contaBancaria, IGNORED_FIELDS);
		
		
		assertThat(actual.getAgencia().getId()).isNotNull();
		assertThat(actual.getAgencia()).isEqualToIgnoringGivenFields(contaBancaria.getAgencia(), IGNORED_FIELDS);
		
		assertThat(actual.getBandeiraCartao()).isNull();
		
	}
	// END UPDATE TESTS
	
	// BEGIN DELETE TESTS
	
	@Test
	public void testDelete1() {
		ContaBancariaEntity expected = newContaBancariaEntity();
		java.util.UUID id = expected.getId();
		
		ContaBancariaEntity contaBancaria = expected;
		
		expected = em.find(ContaBancariaEntity.class, id);
		assertThat(expected).isNotNull();
		
		// BEGIN check event deleted.
		doAnswer(invocation -> {
			DomainEventEnvelope<DomainEvent> envelope = invocation.getArgument(0);
			
			assertThat(envelope).isNotNull();
			assertThat(envelope.getPayload()).isNotNull();
			
			ContaBancariaEvent event = (ContaBancariaEvent) envelope.getPayload();
			assertThat(event.getId()).isEqualTo(contaBancaria.getId());
			assertThat(event.getNomeTitular()).isEqualTo(contaBancaria.getNomeTitular());
			assertThat(event.getCpfCnpjTitular()).isEqualTo(contaBancaria.getCpfCnpjTitular());
			assertThat(event.getTipoContaBancaria()).isEqualTo(contaBancaria.getTipoContaBancaria());
			
			if (contaBancaria.getAgencia() == null) {
				assertThat(event.getAgencia()).isNull();
			}
			else {
				assertThat(event.getAgencia()).isEqualTo(contaBancaria.getAgencia().getId());
			}
			
			assertThat(event.getNumeroConta()).isEqualTo(contaBancaria.getNumeroConta());
			assertThat(event.getDigito()).isEqualTo(contaBancaria.getDigito());
			assertThat(event.getAtivo()).isEqualTo(contaBancaria.getAtivo());
			assertThat(event.getDataValidade()).isEqualTo(contaBancaria.getDataValidade());
			
			if (contaBancaria.getBandeiraCartao() == null) {
				assertThat(event.getBandeiraCartao()).isNull();
			}
			else {
				assertThat(event.getBandeiraCartao()).isEqualTo(contaBancaria.getBandeiraCartao().getId());
			}
			
			
			assertThat(CadastrosBancoConstants.DOMAIN).isEqualTo(envelope.getDomain());
			assertThat(CadastrosBancoConstants.SERVICE).isEqualTo(envelope.getService());
			
			assertThat("contaBancariaDeleted").isEqualTo(envelope.getPrimitive());
			assertThat("kerubin").isEqualTo(envelope.getTenant());
			assertThat("kerubin").isEqualTo(envelope.getUser());
			assertThat("kerubin").isEqualTo(envelope.getApplication());
			assertThat("entity.ContaBancaria").isEqualTo(envelope.getKey());
			
			return null;
		}).when(publisher).publish(any());
		// END check event deleted.
		
		contaBancariaService.delete(id);
		verify(publisher, times(1)).publish(any());
		
		expected = em.find(ContaBancariaEntity.class, id);
		assertThat(expected).isNull();
	}
	// END DELETE TESTS
	
	// BEGIN LIST TESTS
	
	@Test
	public void testList_FilteringByNumeroConta() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
		
		// Generate 33 records of data for ContaBancariaEntity for this test.
		List<ContaBancariaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newContaBancariaEntity());
		}
		
		// Check if 33 records of ContaBancariaEntity was generated.
		long count = contaBancariaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Creates a list filter for entity ContaBancaria.
		ContaBancariaListFilter listFilter = new ContaBancariaListFilter();
		
		// Extracts 7 records of ContaBancariaEntity randomly from testData.
		final int resultSize = 7;
		List<ContaBancariaEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ContaBancariaEntity.numeroConta field and configure this list as a filter.
		List<String> numeroContaListFilter = filterTestData.stream().map(ContaBancariaEntity::getNumeroConta).collect(Collectors.toList());
		listFilter.setNumeroConta(numeroContaListFilter);
		
		// Generates a pageable configuration, without sorting.
		int pageIndex = 0; // First page starts at index zero.
		int size = 33; // Max of 33 records per page.
		Pageable pageable = PageRequest.of(pageIndex, size);
		// Call service list method.
		Page<ContaBancariaEntity> page = contaBancariaService.list(listFilter, pageable);
		
		// Converts found entities to DTOs and mount the result page.
		List<ContaBancaria> content = page.getContent().stream().map(it -> contaBancariaDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		PageResult<ContaBancaria> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		
		// Asserts that result has size 7, in any order and has only rows with numeroContaListFilter elements based on numeroConta field.
		assertThat(pageResult.getContent())
		.hasSize(7)
		.extracting(ContaBancaria::getNumeroConta)
		.containsExactlyInAnyOrderElementsOf(numeroContaListFilter);
		
		// Asserts some page result elements.
		assertThat(pageResult.getNumber()).isEqualTo(pageIndex);
		assertThat(pageResult.getNumberOfElements()).isEqualTo(7);
		assertThat(pageResult.getTotalElements()).isEqualTo(7);
		assertThat(pageResult.getTotalPages()).isEqualTo(1);
		
	}
	
	@Test
	public void testList_FilteringByNumeroContaWithoutResults() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ContaBancariaEntity for this test.
		List<ContaBancariaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newContaBancariaEntity());
		}
		
		// Check if 33 records of ContaBancariaEntity was generated.
		long count = contaBancariaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Creates a list filter for entity ContaBancaria.
		ContaBancariaListFilter listFilter = new ContaBancariaListFilter();
		
		// Generates a list with only ContaBancariaEntity.numeroConta field with 1 not found data in the database and configure this list as a filter.
		List<String> numeroContaListFilter = Arrays.asList(generateRandomString(30));
		listFilter.setNumeroConta(numeroContaListFilter);
		
		// Generates a pageable configuration, without sorting.
		int pageIndex = 0; // First page starts at index zero.
		int size = 33; // Max of 33 records per page.
		Pageable pageable = PageRequest.of(pageIndex, size);
		// Call service list method.
		Page<ContaBancariaEntity> page = contaBancariaService.list(listFilter, pageable);
		
		// Converts found entities to DTOs and mount the result page.
		List<ContaBancaria> content = page.getContent().stream().map(it -> contaBancariaDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		PageResult<ContaBancaria> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		
		// Asserts that result has size 0 for unknown numeroConta field.
		assertThat(pageResult.getContent()).hasSize(0);
		
	}
	// END LIST TESTS
	
	// BEGIN Autocomplete TESTS
	@Test
	public void testAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ContaBancariaEntity for this test.
		List<ContaBancariaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newContaBancariaEntity());
		}
		
		// Check if 33 records of ContaBancariaEntity was generated.
		long count = contaBancariaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ContaBancariaEntity randomly from testData.
		final int resultSize = 1;
		List<ContaBancariaEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ContaBancariaEntity.nomeTitular field and configure this list as a filter.
		List<String> nomeTitularListFilter = filterTestData.stream().map(ContaBancariaEntity::getNomeTitular).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = nomeTitularListFilter.get(0);
		Collection<ContaBancariaAutoComplete> result = contaBancariaService.autoComplete(query);
		
		// Assert ContaBancariaAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ContaBancariaAutoComplete::getNomeTitular)
		.containsExactlyInAnyOrderElementsOf(nomeTitularListFilter);
	}
	
	// END Autocomplete TESTS
	
	// BEGIN ListFilter Autocomplete TESTS
	
	@Test
	public void testContaBancariaNumeroContaAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for ContaBancariaEntity for this test.
		List<ContaBancariaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newContaBancariaEntity());
		}
		
		// Check if 33 records of ContaBancariaEntity was generated.
		long count = contaBancariaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of ContaBancariaEntity randomly from testData.
		final int resultSize = 1;
		List<ContaBancariaEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only ContaBancariaEntity.numeroConta field and configure this list as a filter.
		List<String> numeroContaListFilter = filterTestData.stream().map(ContaBancariaEntity::getNumeroConta).collect(Collectors.toList());
		// Mount the autocomplete query expression and call it.
		String query = numeroContaListFilter.get(0);
		Collection<ContaBancariaNumeroContaAutoComplete> result = contaBancariaService.contaBancariaNumeroContaAutoComplete(query);
		// Assert ContaBancariaNumeroContaAutoComplete results.
		assertThat(result).isNotNull().hasSize(1)
		.extracting(ContaBancariaNumeroContaAutoComplete::getNumeroConta)
		.containsExactlyInAnyOrderElementsOf(numeroContaListFilter);
	}
	
	// END ListFilter Autocomplete TESTS
	
	// BEGIN Relationships Autocomplete TESTS
	
	@Test
	public void testContaBancariaAgenciaAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for AgenciaBancariaEntity for this test.
		List<AgenciaBancariaEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newAgenciaBancariaEntity());
		}
		
		// Check if 33 records of AgenciaBancariaEntity was generated.
		long count = agenciaBancariaRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of AgenciaBancariaEntity randomly from testData.
		final int resultSize = 1;
		List<AgenciaBancariaEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only AgenciaBancariaEntity.numeroAgencia field and configure this list as a filter.
		List<String> numeroAgenciaListFilter = filterTestData.stream().map(AgenciaBancariaEntity::getNumeroAgencia).collect(Collectors.toList());
		String query = numeroAgenciaListFilter.get(0);
		
		Collection<AgenciaBancariaAutoComplete> result = contaBancariaService.agenciaBancariaAgenciaAutoComplete(query);
		
		assertThat(result).isNotNull().hasSize(1)
		.extracting(AgenciaBancariaAutoComplete::getNumeroAgencia)
		.containsExactlyInAnyOrderElementsOf(numeroAgenciaListFilter);
	}
	
	
	@Test
	public void testContaBancariaBandeiraCartaoAutoComplete() {
		// Reset lastDate field to start LocalDate fields with today in this test. 
		resetNextDate();
					
		// Generate 33 records of data for BandeiraCartaoEntity for this test.
		List<BandeiraCartaoEntity> testData = new ArrayList<>();
		final int lastRecord = 33;
		final int firstRecord = 1;
		for (int i = firstRecord; i <= lastRecord; i++) {
			testData.add(newBandeiraCartaoEntity());
		}
		
		// Check if 33 records of BandeiraCartaoEntity was generated.
		long count = bandeiraCartaoRepository.count();
		assertThat(count).isEqualTo(lastRecord);
		
		// Extracts 1 records of BandeiraCartaoEntity randomly from testData.
		final int resultSize = 1;
		List<BandeiraCartaoEntity> filterTestData = getRandomItemsOf(testData, resultSize);
		
		// Extracts a list with only BandeiraCartaoEntity.nomeBandeira field and configure this list as a filter.
		List<String> nomeBandeiraListFilter = filterTestData.stream().map(BandeiraCartaoEntity::getNomeBandeira).collect(Collectors.toList());
		String query = nomeBandeiraListFilter.get(0);
		
		Collection<BandeiraCartaoAutoComplete> result = contaBancariaService.bandeiraCartaoBandeiraCartaoAutoComplete(query);
		
		assertThat(result).isNotNull().hasSize(1)
		.extracting(BandeiraCartaoAutoComplete::getNomeBandeira)
		.containsExactlyInAnyOrderElementsOf(nomeBandeiraListFilter);
	}
	
	// END Relationships Autocomplete TESTS
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN tests for Sum Fields
	// END tests for Sum Fields
	
	// BEGIN TESTS DEPENDENCIES
	
	
	protected ContaBancariaEntity newContaBancariaEntity() {
		ContaBancariaEntity contaBancariaEntity = new ContaBancariaEntity();
		
		contaBancariaEntity.setNomeTitular(generateRandomString(255));
		contaBancariaEntity.setCpfCnpjTitular("92020472007");
		contaBancariaEntity.setTipoContaBancaria(TipoContaBancaria.CONTA_CORRENTE);
		contaBancariaEntity.setAgencia(newAgenciaBancariaEntity());
		contaBancariaEntity.setNumeroConta(generateRandomString(30));
		contaBancariaEntity.setDigito(generateRandomString(10));
		contaBancariaEntity.setSaldo(new java.math.BigDecimal("12810.6901"));
		contaBancariaEntity.setMaisOpcoes(false);
		contaBancariaEntity.setAtivo(true);
		contaBancariaEntity.setDataValidade(getNextDate());
		contaBancariaEntity.setNumeroCartao(generateRandomString(50));
		contaBancariaEntity.setCodigoSeguranca(generateRandomString(10));
		contaBancariaEntity.setBandeiraCartao(newBandeiraCartaoEntity());
		
		contaBancariaEntity = em.persistAndFlush(contaBancariaEntity);
		return contaBancariaEntity;
	}
	
	
	protected ContaBancariaLookupResult newContaBancariaLookupResult(ContaBancariaEntity contaBancariaEntity) {
		ContaBancariaLookupResult contaBancaria = new ContaBancariaLookupResult();
		
		contaBancaria.setId(contaBancariaEntity.getId());
		contaBancaria.setNomeTitular(contaBancariaEntity.getNomeTitular());
		contaBancaria.setCpfCnpjTitular(contaBancariaEntity.getCpfCnpjTitular());
		contaBancaria.setNumeroConta(contaBancariaEntity.getNumeroConta());
		contaBancaria.setDigito(contaBancariaEntity.getDigito());
		
		return contaBancaria;
	}
	
	
	protected AgenciaBancariaEntity newAgenciaBancariaEntity() {
		AgenciaBancariaEntity agenciaBancariaEntity = new AgenciaBancariaEntity();
		
		agenciaBancariaEntity.setBanco(newBancoEntity());
		agenciaBancariaEntity.setNumeroAgencia(generateRandomString(50));
		agenciaBancariaEntity.setDigitoAgencia(generateRandomString(10));
		agenciaBancariaEntity.setMaisOpcoes(false);
		agenciaBancariaEntity.setEndereco(generateRandomString(255));
		agenciaBancariaEntity.setNomeGerente(generateRandomString(255));
		agenciaBancariaEntity.setFone(generateRandomString(50));
		
		agenciaBancariaEntity = em.persistAndFlush(agenciaBancariaEntity);
		return agenciaBancariaEntity;
	}
	
	
	protected AgenciaBancariaLookupResult newAgenciaBancariaLookupResult(AgenciaBancariaEntity agenciaBancariaEntity) {
		AgenciaBancariaLookupResult agenciaBancaria = new AgenciaBancariaLookupResult();
		
		agenciaBancaria.setId(agenciaBancariaEntity.getId());
		agenciaBancaria.setNumeroAgencia(agenciaBancariaEntity.getNumeroAgencia());
		agenciaBancaria.setDigitoAgencia(agenciaBancariaEntity.getDigitoAgencia());
		agenciaBancaria.setEndereco(agenciaBancariaEntity.getEndereco());
		
		return agenciaBancaria;
	}
	
	
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
	
	
	protected BandeiraCartaoEntity newBandeiraCartaoEntity() {
		BandeiraCartaoEntity bandeiraCartaoEntity = new BandeiraCartaoEntity();
		
		bandeiraCartaoEntity.setNomeBandeira(generateRandomString(255));
		
		bandeiraCartaoEntity = em.persistAndFlush(bandeiraCartaoEntity);
		return bandeiraCartaoEntity;
	}
	
	
	protected BandeiraCartaoLookupResult newBandeiraCartaoLookupResult(BandeiraCartaoEntity bandeiraCartaoEntity) {
		BandeiraCartaoLookupResult bandeiraCartao = new BandeiraCartaoLookupResult();
		
		bandeiraCartao.setId(bandeiraCartaoEntity.getId());
		bandeiraCartao.setNomeBandeira(bandeiraCartaoEntity.getNomeBandeira());
		
		return bandeiraCartao;
	}
	// END TESTS DEPENDENCIES

}
