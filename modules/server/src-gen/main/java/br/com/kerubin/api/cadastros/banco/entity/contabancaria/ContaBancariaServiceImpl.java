/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;

// import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.kerubin.api.messaging.core.DomainEntityEventsPublisher;
import br.com.kerubin.api.messaging.core.DomainEvent;
import br.com.kerubin.api.messaging.core.DomainEventEnvelope;
import br.com.kerubin.api.messaging.core.DomainEventEnvelopeBuilder;
import br.com.kerubin.api.database.core.ServiceContext;
import br.com.kerubin.api.cadastros.banco.CadastrosBancoConstants;

import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoAutoComplete;

import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaRepository;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoRepository;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.apache.commons.lang3.StringUtils.stripStart;
import java.util.Optional;
 
@Service
public class ContaBancariaServiceImpl implements ContaBancariaService {
	private static final String ENTITY_KEY = "entity.ContaBancaria";
	
	@Autowired
	private ContaBancariaRepository contaBancariaRepository;
	
	@Autowired
	private ContaBancariaListFilterPredicate contaBancariaListFilterPredicate;
	
	@Autowired
	DomainEntityEventsPublisher publisher;
	
	@Autowired
	private AgenciaBancariaRepository agenciaBancariaRepository;
	
	@Autowired
	private BandeiraCartaoRepository bandeiraCartaoRepository;
	
	
	@Transactional
	@Override
	public ContaBancariaEntity create(ContaBancariaEntity contaBancariaEntity) {
		
		// Begin Rules AppyModifierFunction
		if (contaBancariaEntity.getNumeroConta() != null) {
			contaBancariaEntity.setNumeroConta(stripStart(contaBancariaEntity.getNumeroConta(), "0"));
		}
		// End Rules AppyModifierFunction
		
		ContaBancariaEntity entity = contaBancariaRepository.save(contaBancariaEntity);
		publishEvent(entity, ContaBancariaEvent.CONTA_BANCARIA_CREATED);
		return entity;
	}
	
	@Transactional(readOnly = true)
	@Override
	public ContaBancariaEntity read(java.util.UUID id) {
		return getContaBancariaEntity(id);
	}
	
	@Transactional
	@Override
	public ContaBancariaEntity update(java.util.UUID id, ContaBancariaEntity contaBancariaEntity) {
						
		// Begin Rules AppyModifierFunction
		if (contaBancariaEntity.getNumeroConta() != null) {
			contaBancariaEntity.setNumeroConta(stripStart(contaBancariaEntity.getNumeroConta(), "0"));
		}
		// End Rules AppyModifierFunction
		
		// ContaBancariaEntity entity = getContaBancariaEntity(id);
		// BeanUtils.copyProperties(contaBancariaEntity, entity, "id");
		// entity = contaBancariaRepository.save(entity);
		
		ContaBancariaEntity entity = contaBancariaRepository.save(contaBancariaEntity);
		
		publishEvent(entity, ContaBancariaEvent.CONTA_BANCARIA_UPDATED);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// First load the delete candidate entity.
		ContaBancariaEntity entity = getContaBancariaEntity(id);
		
		// Delete it.
		contaBancariaRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		contaBancariaRepository.flush();
		
		// Replicate the delete event.
		publishEvent(entity, ContaBancariaEvent.CONTA_BANCARIA_DELETED);
	}
	
	protected void publishEvent(ContaBancariaEntity entity, String eventName) {
		DomainEvent event = new ContaBancariaEvent(entity.getId(), 
			entity.getNomeTitular(), 
			entity.getAgencia() != null ? entity.getAgencia().getId() : null, 
			entity.getTipoContaBancaria(), 
			entity.getNumeroConta(), 
			entity.getDigito(), 
			entity.getDataValidade(), 
			entity.getBandeiraCartao() != null ? entity.getBandeiraCartao().getId() : null, 
			entity.getAtivo());
		
		DomainEventEnvelope<DomainEvent> envelope = DomainEventEnvelopeBuilder
				.getBuilder(eventName, event)
				.domain(CadastrosBancoConstants.DOMAIN)
				.service(CadastrosBancoConstants.SERVICE)
				.key(ENTITY_KEY)
				.tenant(ServiceContext.getTenant())
				.user(ServiceContext.getUser())
				.build();
		
		publisher.publish(envelope);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ContaBancariaEntity> list(ContaBancariaListFilter contaBancariaListFilter, Pageable pageable) {
		Predicate predicate = contaBancariaListFilterPredicate.mountAndGetPredicate(contaBancariaListFilter);
		
		Page<ContaBancariaEntity> resultPage = contaBancariaRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected ContaBancariaEntity getContaBancariaEntity(java.util.UUID id) {
		Optional<ContaBancariaEntity> contaBancariaEntity = contaBancariaRepository.findById(id);
		if (!contaBancariaEntity.isPresent()) {
			throw new IllegalArgumentException("ContaBancaria not found:" + id.toString());
		}
		return contaBancariaEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ContaBancariaAutoComplete> autoComplete(String query) {
		Collection<ContaBancariaAutoComplete> result = contaBancariaRepository.autoComplete(query);
		return result;
	}
	
	// Begin relationships autoComplete 
	@Transactional(readOnly = true)
	@Override
	public Collection<AgenciaBancariaAutoComplete> agenciaBancariaAgenciaAutoComplete(String query) {
		Collection<AgenciaBancariaAutoComplete> result = agenciaBancariaRepository.autoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<BandeiraCartaoAutoComplete> bandeiraCartaoBandeiraCartaoAutoComplete(String query) {
		Collection<BandeiraCartaoAutoComplete> result = bandeiraCartaoRepository.autoComplete(query);
		return result;
	}
	
	// End relationships autoComplete
	
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ContaBancariaNumeroContaAutoComplete> contaBancariaNumeroContaAutoComplete(String query) {
		Collection<ContaBancariaNumeroContaAutoComplete> result = contaBancariaRepository.contaBancariaNumeroContaAutoComplete(query);
		return result;
	}
	
	
}
