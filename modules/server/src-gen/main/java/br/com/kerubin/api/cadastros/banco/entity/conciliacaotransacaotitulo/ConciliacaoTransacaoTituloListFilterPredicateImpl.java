/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo;


import org.springframework.stereotype.Component;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@Component
public class ConciliacaoTransacaoTituloListFilterPredicateImpl implements ConciliacaoTransacaoTituloListFilterPredicate {
	
	@Override
	public Predicate mountAndGetPredicate(ConciliacaoTransacaoTituloListFilter conciliacaoTransacaoTituloListFilter) {
		if (conciliacaoTransacaoTituloListFilter == null) {
			return null;
		}
		
		QConciliacaoTransacaoTituloEntity qEntity = QConciliacaoTransacaoTituloEntity.conciliacaoTransacaoTituloEntity;
		BooleanBuilder where = new BooleanBuilder();
		
		// Begin field: situacaoConciliacaoTrn
		if (conciliacaoTransacaoTituloListFilter.getSituacaoConciliacaoTrn() != null) {
			BooleanExpression situacaoConciliacaoTrnIsEqualTo = qEntity.situacaoConciliacaoTrn.eq(conciliacaoTransacaoTituloListFilter.getSituacaoConciliacaoTrn());
			where.and(situacaoConciliacaoTrnIsEqualTo);
		}
		// End field: situacaoConciliacaoTrn
		
		return where;
	}

}

