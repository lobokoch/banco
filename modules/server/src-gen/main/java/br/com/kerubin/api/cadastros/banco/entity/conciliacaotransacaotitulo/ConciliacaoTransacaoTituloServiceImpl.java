/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo;

// import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;


import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoRepository;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaRepository;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
 
@Service
public class ConciliacaoTransacaoTituloServiceImpl implements ConciliacaoTransacaoTituloService {
	
	@Autowired
	private ConciliacaoTransacaoTituloRepository conciliacaoTransacaoTituloRepository;
	
	@Autowired
	private ConciliacaoTransacaoTituloListFilterPredicate conciliacaoTransacaoTituloListFilterPredicate;
	
	
	@Autowired
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@Autowired
	private PlanoContaRepository planoContaRepository;
	
	
	@Transactional
	@Override
	public ConciliacaoTransacaoTituloEntity create(ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity) {
		return conciliacaoTransacaoTituloRepository.save(conciliacaoTransacaoTituloEntity);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ConciliacaoTransacaoTituloEntity read(java.util.UUID id) {
		return getConciliacaoTransacaoTituloEntity(id);
	}
	
	@Transactional
	@Override
	public ConciliacaoTransacaoTituloEntity update(java.util.UUID id, ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity) {
		// ConciliacaoTransacaoTituloEntity entity = getConciliacaoTransacaoTituloEntity(id);
		// BeanUtils.copyProperties(conciliacaoTransacaoTituloEntity, entity, "id");
		// entity = conciliacaoTransacaoTituloRepository.save(entity);
		
		ConciliacaoTransacaoTituloEntity entity = conciliacaoTransacaoTituloRepository.save(conciliacaoTransacaoTituloEntity);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// Delete it.
		conciliacaoTransacaoTituloRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		conciliacaoTransacaoTituloRepository.flush();
		
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Page<ConciliacaoTransacaoTituloEntity> list(ConciliacaoTransacaoTituloListFilter conciliacaoTransacaoTituloListFilter, Pageable pageable) {
		Predicate predicate = conciliacaoTransacaoTituloListFilterPredicate.mountAndGetPredicate(conciliacaoTransacaoTituloListFilter);
		
		Page<ConciliacaoTransacaoTituloEntity> resultPage = conciliacaoTransacaoTituloRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected ConciliacaoTransacaoTituloEntity getConciliacaoTransacaoTituloEntity(java.util.UUID id) {
		Optional<ConciliacaoTransacaoTituloEntity> conciliacaoTransacaoTituloEntity = conciliacaoTransacaoTituloRepository.findById(id);
		if (!conciliacaoTransacaoTituloEntity.isPresent()) {
			throw new IllegalArgumentException("ConciliacaoTransacaoTitulo not found:" + id.toString());
		}
		return conciliacaoTransacaoTituloEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoTituloAutoComplete> autoComplete(String query) {
		Collection<ConciliacaoTransacaoTituloAutoComplete> result = conciliacaoTransacaoTituloRepository.autoComplete(query);
		return result;
	}
	
	// Begin relationships autoComplete 
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoAutoComplete> conciliacaoTransacaoConciliacaoTransacaoAutoComplete(String query) {
		Collection<ConciliacaoTransacaoAutoComplete> result = conciliacaoTransacaoRepository.autoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(String query) {
		Collection<PlanoContaAutoComplete> result = planoContaRepository.autoComplete(query);
		return result;
	}
	
	// End relationships autoComplete
	
	
	
}
