/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;

public interface ConciliacaoTransacaoTituloService {
	
	public ConciliacaoTransacaoTituloEntity create(ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity);
	
	public ConciliacaoTransacaoTituloEntity read(java.util.UUID id);
	
	public ConciliacaoTransacaoTituloEntity update(java.util.UUID id, ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity);
	
	public void delete(java.util.UUID id);
	
	public Page<ConciliacaoTransacaoTituloEntity> list(ConciliacaoTransacaoTituloListFilter conciliacaoTransacaoTituloListFilter, Pageable pageable);
	
	public Collection<ConciliacaoTransacaoTituloAutoComplete> autoComplete(String query);
	
	// Begin relationships autoComplete 
	public Collection<ConciliacaoTransacaoAutoComplete> conciliacaoTransacaoConciliacaoTransacaoAutoComplete(String query);
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(String query);
	// End relationships autoComplete
	 
}
