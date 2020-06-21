/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

// import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;


import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloAutoComplete;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaRepository;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloRepository;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;
 
@Service
public class ConciliacaoTransacaoServiceImpl implements ConciliacaoTransacaoService {
	
	@Autowired
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@Autowired
	private ConciliacaoTransacaoListFilterPredicate conciliacaoTransacaoListFilterPredicate;
	
	
	@Autowired
	private ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@Autowired
	private PlanoContaRepository planoContaRepository;
	
	@Autowired
	private ConciliacaoTransacaoTituloRepository conciliacaoTransacaoTituloRepository;
	
	
	@Transactional
	@Override
	public ConciliacaoTransacaoEntity create(ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		return conciliacaoTransacaoRepository.save(conciliacaoTransacaoEntity);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ConciliacaoTransacaoEntity read(java.util.UUID id) {
		return getConciliacaoTransacaoEntity(id);
	}
	
	@Transactional
	@Override
	public ConciliacaoTransacaoEntity update(java.util.UUID id, ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		// ConciliacaoTransacaoEntity entity = getConciliacaoTransacaoEntity(id);
		// BeanUtils.copyProperties(conciliacaoTransacaoEntity, entity, "id");
		// entity = conciliacaoTransacaoRepository.save(entity);
		
		ConciliacaoTransacaoEntity entity = conciliacaoTransacaoRepository.save(conciliacaoTransacaoEntity);
		
		return entity;
	}
	
	@Transactional
	@Override
	public void delete(java.util.UUID id) {
		
		// Delete it.
		conciliacaoTransacaoRepository.deleteById(id);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		conciliacaoTransacaoRepository.flush();
		
	}
	
	@Transactional
	@Override
	public void deleteInBulk(java.util.List<java.util.UUID> idList) {
		// Delete it.
		conciliacaoTransacaoRepository.deleteInBulk(idList);
		
		// Force flush to the database, for relationship validation and must throw exception because of this here.
		conciliacaoTransacaoRepository.flush();
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Page<ConciliacaoTransacaoEntity> list(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter, Pageable pageable) {
		Predicate predicate = conciliacaoTransacaoListFilterPredicate.mountAndGetPredicate(conciliacaoTransacaoListFilter);
		
		Page<ConciliacaoTransacaoEntity> resultPage = conciliacaoTransacaoRepository.findAll(predicate, pageable);
		return resultPage;
	}
	
	@Transactional(readOnly = true)
	protected ConciliacaoTransacaoEntity getConciliacaoTransacaoEntity(java.util.UUID id) {
		Optional<ConciliacaoTransacaoEntity> conciliacaoTransacaoEntity = conciliacaoTransacaoRepository.findById(id);
		if (!conciliacaoTransacaoEntity.isPresent()) {
			throw new IllegalArgumentException("ConciliacaoTransacao not found:" + id.toString());
		}
		return conciliacaoTransacaoEntity.get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoAutoComplete> autoComplete(String query) {
		Collection<ConciliacaoTransacaoAutoComplete> result = conciliacaoTransacaoRepository.autoComplete(query);
		return result;
	}
	
	// Begin relationships autoComplete 
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoBancariaAutoComplete> conciliacaoBancariaConciliacaoBancariaAutoComplete(String query) {
		Collection<ConciliacaoBancariaAutoComplete> result = conciliacaoBancariaRepository.autoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(String query, ConciliacaoTransacao conciliacaoTransacao) {
		Collection<PlanoContaAutoComplete> result = planoContaRepository.autoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoTituloAutoComplete> conciliacaoTransacaoTituloConciliacaoTransacaoTitulosAutoComplete(String query) {
		Collection<ConciliacaoTransacaoTituloAutoComplete> result = conciliacaoTransacaoTituloRepository.autoComplete(query);
		return result;
	}
	
	// End relationships autoComplete
	
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> conciliacaoTransacaoTrnHistoricoAutoComplete(String query) {
		Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> result = conciliacaoTransacaoRepository.conciliacaoTransacaoTrnHistoricoAutoComplete(query);
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> conciliacaoTransacaoTrnDocumentoAutoComplete(String query) {
		Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> result = conciliacaoTransacaoRepository.conciliacaoTransacaoTrnDocumentoAutoComplete(query);
		return result;
	}
	
	
	
	// Begin findBy methods
	
	@Transactional(readOnly = true)
	@Override
	public List<ConciliacaoTransacaoEntity> findByIdIn(List<java.util.UUID> ids) {
		return conciliacaoTransacaoRepository.findByIdIn(ids);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<ConciliacaoTransacaoEntity> findConciliacaoTransacaoByConciliacaoBancaria(java.util.UUID id) {
		return conciliacaoTransacaoRepository.findByConciliacaoBancariaId(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ConciliacaoTransacaoEntity> findByIdIsNotAndTituloConciliadoIdIs(java.util.UUID id, java.util.UUID tituloConciliadoId) {
		return conciliacaoTransacaoRepository.findByIdIsNotAndTituloConciliadoIdIs(id, tituloConciliadoId);
	}
	// End findBy methods
}
