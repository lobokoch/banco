/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.agenciabancaria;

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

import br.com.kerubin.api.cadastros.banco.entity.banco.BancoAutoComplete;

import br.com.kerubin.api.cadastros.banco.entity.banco.BancoRepository;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.apache.commons.lang3.StringUtils.stripStart;
import java.util.Optional;
 
@Service
public class AgenciaBancariaServiceImpl implements AgenciaBancariaService {
	private static final String ENTITY_KEY = "entity.AgenciaBancaria";
	
	@Autowired
	private AgenciaBancariaRepository agenciaBancariaRepository;
	
	@Autowired
	private AgenciaBancariaListFilterPredicate agenciaBancariaListFilterPredicate;
	
	@Autowired
	DomainEntityEventsPublisher publisher;
	
	@Autowired
	private BancoRepository bancoRepository;
	
	
	@Transactional
	@Override
	public AgenciaBancariaEntity create(AgenciaBancariaEntity agenciaBancariaEntity) {
		
		// Begin Rules AppyModifierFunction
		if (agenciaBancariaEntity.getNumeroAgencia() != null) {
			agenciaBancariaEntity.setNumeroAgencia(stripStart(agenciaBancariaEntity.getNumeroAgencia(), "0"));
		}
		// End Rules AppyModifierFunction
		
		AgenciaBancariaEntity entity = agenciaBancariaRepository.save(agenciaBancariaEntity);
		publishEvent(entity, AgenciaBancariaEvent.AGENCIA_BANCARIA_CREATED);
		return entity;
	}
	
	@Transactional(readOnly = true)
	@Override
	public AgenciaBancariaEntity read(java.util.UUID id) {
		return getAgenciaBancariaEntity(id);
	}
	
	@Transactional
	@Override
	public AgenciaBancariaEntity update(java.util.UUID id, AgenciaBancariaEntity agenciaBancariaEntity) {
						
		// Begin Rules AppyModifierFunction
		if (agenciaBancariaEntity.getNumeroAgencia() != null) {
			agenciaBancariaEntity.setNumeroAgencia(stripStart(agenciaBancariaEntity.getNumeroAgencia(), "0"));
		}
		// End Rules AppyModifierFunction
		
		// AgenciaBancariaEntity entity = getAgenciaBancariaEntity(id);
		// BeanUtils.copyProperties(agenciaBancariaEntity, entity, "id");
		// entity = agenciaBancariaRepository.save(entity);
		
		AgenciaBancariaEntity entity = agenciaBancariaRepository.save(agenciaBancariaEntity);
		
		publishEvent(entity, AgenciaBancariaEvent.AGENCIA_BANCARIA_UPDATED);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// First load the delete candidate entity.
		AgenciaBancariaEntity entity = getAgenciaBancariaEntity(id);
		
		// Delete it.
		agenciaBancariaRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		agenciaBancariaRepository.flush();
		
		// Replicate the delete event.
		publishEvent(entity, AgenciaBancariaEvent.AGENCIA_BANCARIA_DELETED);
	}
	
	protected void publishEvent(AgenciaBancariaEntity entity, String eventName) {
		DomainEvent event = new AgenciaBancariaEvent(entity.getId(), 
			entity.getBanco() != null ? entity.getBanco().getId() : null, 
			entity.getNumeroAgencia(), 
			entity.getDigitoAgencia());
		
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
	public Page<AgenciaBancariaEntity> list(AgenciaBancariaListFilter agenciaBancariaListFilter, Pageable pageable) {
		Predicate predicate = agenciaBancariaListFilterPredicate.mountAndGetPredicate(agenciaBancariaListFilter);
		
		Page<AgenciaBancariaEntity> resultPage = agenciaBancariaRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected AgenciaBancariaEntity getAgenciaBancariaEntity(java.util.UUID id) {
		Optional<AgenciaBancariaEntity> agenciaBancariaEntity = agenciaBancariaRepository.findById(id);
		if (!agenciaBancariaEntity.isPresent()) {
			throw new IllegalArgumentException("AgenciaBancaria not found:" + id.toString());
		}
		return agenciaBancariaEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<AgenciaBancariaAutoComplete> autoComplete(String query) {
		Collection<AgenciaBancariaAutoComplete> result = agenciaBancariaRepository.autoComplete(query);
		return result;
	}
	
	// Begin relationships autoComplete 
	@Transactional(readOnly = true)
	@Override
	public Collection<BancoAutoComplete> bancoBancoAutoComplete(String query) {
		Collection<BancoAutoComplete> result = bancoRepository.autoComplete(query);
		return result;
	}
	
	// End relationships autoComplete
	
	
	
}
