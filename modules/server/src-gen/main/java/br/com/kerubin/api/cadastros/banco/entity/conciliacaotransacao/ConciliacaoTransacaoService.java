/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloAutoComplete;

public interface ConciliacaoTransacaoService {
	
	public ConciliacaoTransacaoEntity create(ConciliacaoTransacaoEntity conciliacaoTransacaoEntity);
	
	public ConciliacaoTransacaoEntity read(java.util.UUID id);
	
	public ConciliacaoTransacaoEntity update(java.util.UUID id, ConciliacaoTransacaoEntity conciliacaoTransacaoEntity);
	
	public void delete(java.util.UUID id);
	
	public Page<ConciliacaoTransacaoEntity> list(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter, Pageable pageable);
	
	public Collection<ConciliacaoTransacaoAutoComplete> autoComplete(String query);
	
	// Begin relationships autoComplete 
	public Collection<ConciliacaoBancariaAutoComplete> conciliacaoBancariaConciliacaoBancariaAutoComplete(String query);
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(String query, ConciliacaoTransacao conciliacaoTransacao);
	public Collection<ConciliacaoTransacaoTituloAutoComplete> conciliacaoTransacaoTituloConciliacaoTransacaoTitulosAutoComplete(String query);
	// End relationships autoComplete
	 
	
	public Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> conciliacaoTransacaoTrnHistoricoAutoComplete(String query);
	
	public Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> conciliacaoTransacaoTrnDocumentoAutoComplete(String query);
	// findBy methods
	public Collection<ConciliacaoTransacaoEntity> findConciliacaoTransacaoByConciliacaoBancaria(java.util.UUID id);
}
