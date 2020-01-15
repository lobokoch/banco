/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ConciliacaoBancariaService {
	
	public ConciliacaoBancariaEntity create(ConciliacaoBancariaEntity conciliacaoBancariaEntity);
	
	public ConciliacaoBancariaEntity read(java.util.UUID id);
	
	public ConciliacaoBancariaEntity update(java.util.UUID id, ConciliacaoBancariaEntity conciliacaoBancariaEntity);
	
	public void delete(java.util.UUID id);
	
	public Page<ConciliacaoBancariaEntity> list(ConciliacaoBancariaListFilter conciliacaoBancariaListFilter, Pageable pageable);
	
	public Collection<ConciliacaoBancariaAutoComplete> autoComplete(String query);
	
	 
}
