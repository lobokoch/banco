/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.bandeiracartao;

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
import java.util.Optional;
 
@Service
public class BandeiraCartaoServiceImpl implements BandeiraCartaoService {
	private static final String ENTITY_KEY = "entity.BandeiraCartao";
	
	@Autowired
	private BandeiraCartaoRepository bandeiraCartaoRepository;
	
	@Autowired
	private BandeiraCartaoListFilterPredicate bandeiraCartaoListFilterPredicate;
	
	@Autowired
	DomainEntityEventsPublisher publisher;
	
	@Transactional
	@Override
	public BandeiraCartaoEntity create(BandeiraCartaoEntity bandeiraCartaoEntity) {
		BandeiraCartaoEntity entity = bandeiraCartaoRepository.save(bandeiraCartaoEntity);
		publishEvent(entity, BandeiraCartaoEvent.BANDEIRA_CARTAO_CREATED);
		return entity;
	}
	
	@Transactional(readOnly = true)
	@Override
	public BandeiraCartaoEntity read(java.util.UUID id) {
		return getBandeiraCartaoEntity(id);
	}
	
	@Transactional
	@Override
	public BandeiraCartaoEntity update(java.util.UUID id, BandeiraCartaoEntity bandeiraCartaoEntity) {
		// BandeiraCartaoEntity entity = getBandeiraCartaoEntity(id);
		// BeanUtils.copyProperties(bandeiraCartaoEntity, entity, "id");
		// entity = bandeiraCartaoRepository.save(entity);
		
		BandeiraCartaoEntity entity = bandeiraCartaoRepository.save(bandeiraCartaoEntity);
		
		publishEvent(entity, BandeiraCartaoEvent.BANDEIRA_CARTAO_UPDATED);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// First load the delete candidate entity.
		BandeiraCartaoEntity entity = getBandeiraCartaoEntity(id);
		
		// Delete it.
		bandeiraCartaoRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		bandeiraCartaoRepository.flush();
		
		// Replicate the delete event.
		publishEvent(entity, BandeiraCartaoEvent.BANDEIRA_CARTAO_DELETED);
	}
	
	protected void publishEvent(BandeiraCartaoEntity entity, String eventName) {
		DomainEvent event = new BandeiraCartaoEvent(entity.getId(), 
			entity.getNomeBandeira());
		
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
	public Page<BandeiraCartaoEntity> list(BandeiraCartaoListFilter bandeiraCartaoListFilter, Pageable pageable) {
		Predicate predicate = bandeiraCartaoListFilterPredicate.mountAndGetPredicate(bandeiraCartaoListFilter);
		
		Page<BandeiraCartaoEntity> resultPage = bandeiraCartaoRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected BandeiraCartaoEntity getBandeiraCartaoEntity(java.util.UUID id) {
		Optional<BandeiraCartaoEntity> bandeiraCartaoEntity = bandeiraCartaoRepository.findById(id);
		if (!bandeiraCartaoEntity.isPresent()) {
			throw new IllegalArgumentException("BandeiraCartao not found:" + id.toString());
		}
		return bandeiraCartaoEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<BandeiraCartaoAutoComplete> autoComplete(String query) {
		Collection<BandeiraCartaoAutoComplete> result = bandeiraCartaoRepository.autoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<BandeiraCartaoNomeBandeiraAutoComplete> bandeiraCartaoNomeBandeiraAutoComplete(String query) {
		Collection<BandeiraCartaoNomeBandeiraAutoComplete> result = bandeiraCartaoRepository.bandeiraCartaoNomeBandeiraAutoComplete(query);
		return result;
	}
	
	
}
