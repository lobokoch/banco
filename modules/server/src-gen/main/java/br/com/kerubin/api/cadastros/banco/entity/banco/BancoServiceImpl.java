/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.banco;

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
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.apache.commons.lang3.StringUtils.stripStart;
import java.util.Optional;
 
@Service
public class BancoServiceImpl implements BancoService {
	private static final String ENTITY_KEY = "entity.Banco";
	
	@Autowired
	private BancoRepository bancoRepository;
	
	@Autowired
	private BancoListFilterPredicate bancoListFilterPredicate;
	
	@Autowired
	DomainEntityEventsPublisher publisher;
	
	@Transactional
	@Override
	public BancoEntity create(BancoEntity bancoEntity) {
		
		// Begin Rules AppyModifierFunction
		if (bancoEntity.getNumero() != null) {
			bancoEntity.setNumero(stripStart(bancoEntity.getNumero(), "0"));
		}
		// End Rules AppyModifierFunction
		
		BancoEntity entity = bancoRepository.save(bancoEntity);
		publishEvent(entity, BancoEvent.BANCO_CREATED);
		return entity;
	}
	
	@Transactional(readOnly = true)
	@Override
	public BancoEntity read(java.util.UUID id) {
		return getBancoEntity(id);
	}
	
	@Transactional
	@Override
	public BancoEntity update(java.util.UUID id, BancoEntity bancoEntity) {
						
		// Begin Rules AppyModifierFunction
		if (bancoEntity.getNumero() != null) {
			bancoEntity.setNumero(stripStart(bancoEntity.getNumero(), "0"));
		}
		// End Rules AppyModifierFunction
		
		// BancoEntity entity = getBancoEntity(id);
		// BeanUtils.copyProperties(bancoEntity, entity, "id");
		// entity = bancoRepository.save(entity);
		
		BancoEntity entity = bancoRepository.save(bancoEntity);
		
		publishEvent(entity, BancoEvent.BANCO_UPDATED);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// First load the delete candidate entity.
		BancoEntity entity = getBancoEntity(id);
		
		// Delete it.
		bancoRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		bancoRepository.flush();
		
		// Replicate the delete event.
		publishEvent(entity, BancoEvent.BANCO_DELETED);
	}
	
	protected void publishEvent(BancoEntity entity, String eventName) {
		DomainEvent event = new BancoEvent(entity.getId(), 
			entity.getNumero(), 
			entity.getNome());
		
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
	public Page<BancoEntity> list(BancoListFilter bancoListFilter, Pageable pageable) {
		Predicate predicate = bancoListFilterPredicate.mountAndGetPredicate(bancoListFilter);
		
		Page<BancoEntity> resultPage = bancoRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected BancoEntity getBancoEntity(java.util.UUID id) {
		Optional<BancoEntity> bancoEntity = bancoRepository.findById(id);
		if (!bancoEntity.isPresent()) {
			throw new IllegalArgumentException("Banco not found:" + id.toString());
		}
		return bancoEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<BancoAutoComplete> autoComplete(String query) {
		Collection<BancoAutoComplete> result = bancoRepository.autoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<BancoNomeAutoComplete> bancoNomeAutoComplete(String query) {
		Collection<BancoNomeAutoComplete> result = bancoRepository.bancoNomeAutoComplete(query);
		return result;
	}
	
	
}
