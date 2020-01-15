/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria;

// import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.apache.commons.lang3.StringUtils.stripStart;
import java.util.Optional;
 
@Service
public class ConciliacaoBancariaServiceImpl implements ConciliacaoBancariaService {
	
	@Autowired
	private ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@Autowired
	private ConciliacaoBancariaListFilterPredicate conciliacaoBancariaListFilterPredicate;
	
	
	@Transactional
	@Override
	public ConciliacaoBancariaEntity create(ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
		
		// Begin Rules AppyModifierFunction
		if (conciliacaoBancariaEntity.getBancoId() != null) {
			conciliacaoBancariaEntity.setBancoId(stripStart(conciliacaoBancariaEntity.getBancoId(), "0"));
		}
		if (conciliacaoBancariaEntity.getAgenciaId() != null) {
			conciliacaoBancariaEntity.setAgenciaId(stripStart(conciliacaoBancariaEntity.getAgenciaId(), "0"));
		}
		if (conciliacaoBancariaEntity.getContaId() != null) {
			conciliacaoBancariaEntity.setContaId(stripStart(conciliacaoBancariaEntity.getContaId(), "0"));
		}
		// End Rules AppyModifierFunction
		
		return conciliacaoBancariaRepository.save(conciliacaoBancariaEntity);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ConciliacaoBancariaEntity read(java.util.UUID id) {
		return getConciliacaoBancariaEntity(id);
	}
	
	@Transactional
	@Override
	public ConciliacaoBancariaEntity update(java.util.UUID id, ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
						
		// Begin Rules AppyModifierFunction
		if (conciliacaoBancariaEntity.getBancoId() != null) {
			conciliacaoBancariaEntity.setBancoId(stripStart(conciliacaoBancariaEntity.getBancoId(), "0"));
		}
		if (conciliacaoBancariaEntity.getAgenciaId() != null) {
			conciliacaoBancariaEntity.setAgenciaId(stripStart(conciliacaoBancariaEntity.getAgenciaId(), "0"));
		}
		if (conciliacaoBancariaEntity.getContaId() != null) {
			conciliacaoBancariaEntity.setContaId(stripStart(conciliacaoBancariaEntity.getContaId(), "0"));
		}
		// End Rules AppyModifierFunction
		
		// ConciliacaoBancariaEntity entity = getConciliacaoBancariaEntity(id);
		// BeanUtils.copyProperties(conciliacaoBancariaEntity, entity, "id");
		// entity = conciliacaoBancariaRepository.save(entity);
		
		ConciliacaoBancariaEntity entity = conciliacaoBancariaRepository.save(conciliacaoBancariaEntity);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// Delete it.
		conciliacaoBancariaRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		conciliacaoBancariaRepository.flush();
		
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Page<ConciliacaoBancariaEntity> list(ConciliacaoBancariaListFilter conciliacaoBancariaListFilter, Pageable pageable) {
		Predicate predicate = conciliacaoBancariaListFilterPredicate.mountAndGetPredicate(conciliacaoBancariaListFilter);
		
		Page<ConciliacaoBancariaEntity> resultPage = conciliacaoBancariaRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected ConciliacaoBancariaEntity getConciliacaoBancariaEntity(java.util.UUID id) {
		Optional<ConciliacaoBancariaEntity> conciliacaoBancariaEntity = conciliacaoBancariaRepository.findById(id);
		if (!conciliacaoBancariaEntity.isPresent()) {
			throw new IllegalArgumentException("ConciliacaoBancaria not found:" + id.toString());
		}
		return conciliacaoBancariaEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoBancariaAutoComplete> autoComplete(String query) {
		Collection<ConciliacaoBancariaAutoComplete> result = conciliacaoBancariaRepository.autoComplete(query);
		return result;
	}
	
	
}
