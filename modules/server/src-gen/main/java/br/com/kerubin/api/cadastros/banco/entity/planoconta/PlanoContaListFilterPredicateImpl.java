/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.planoconta;


import org.springframework.stereotype.Component;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.BooleanBuilder;

@Component
public class PlanoContaListFilterPredicateImpl implements PlanoContaListFilterPredicate {
	
	@Override
	public Predicate mountAndGetPredicate(PlanoContaListFilter planoContaListFilter) {
		if (planoContaListFilter == null) {
			return null;
		}
		
		BooleanBuilder where = new BooleanBuilder();
		
		
		return where;
	}

}

