/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import org.springframework.util.CollectionUtils;

import org.springframework.stereotype.Component;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@Component
public class ConciliacaoTransacaoListFilterPredicateImpl implements ConciliacaoTransacaoListFilterPredicate {
	
	@Override
	public Predicate mountAndGetPredicate(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter) {
		if (conciliacaoTransacaoListFilter == null) {
			return null;
		}
		
		QConciliacaoTransacaoEntity qEntity = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity;
		BooleanBuilder where = new BooleanBuilder();
		
		
		// Begin field: TrnData
		java.time.LocalDate fieldFromTrnData = conciliacaoTransacaoListFilter.getTrnDataFrom();
		java.time.LocalDate fieldToTrnData = conciliacaoTransacaoListFilter.getTrnDataTo();
		
		if (fieldFromTrnData != null && fieldToTrnData != null) {
			if (fieldFromTrnData.isAfter(fieldToTrnData)) {
				throw new IllegalArgumentException("Valor de \"Movimento de\" não pode ser maior do que valor de \"Até\".");
			}
			
			BooleanExpression between = qEntity.trnData.between(fieldFromTrnData, fieldToTrnData);
			where.and(between);
		}
		else {
			if (fieldFromTrnData != null) {
				where.and(qEntity.trnData.goe(fieldFromTrnData));
			}
			else if (fieldToTrnData != null) {
				where.and(qEntity.trnData.loe(fieldToTrnData));				
			}
		}
		// End field: TrnData
		
		// Begin field: trnHistorico
		if (!CollectionUtils.isEmpty(conciliacaoTransacaoListFilter.getTrnHistorico())) {
			BooleanExpression inExpression = qEntity.trnHistorico.in(conciliacaoTransacaoListFilter.getTrnHistorico());
			where.and(inExpression);
		}
		// End field: trnHistorico
		
		// Begin field: trnDocumento
		if (!CollectionUtils.isEmpty(conciliacaoTransacaoListFilter.getTrnDocumento())) {
			BooleanExpression inExpression = qEntity.trnDocumento.in(conciliacaoTransacaoListFilter.getTrnDocumento());
			where.and(inExpression);
		}
		// End field: trnDocumento
		
		// Begin field: trnTipo
		if (conciliacaoTransacaoListFilter.getTrnTipo() != null) {
			BooleanExpression trnTipoIsEqualTo = qEntity.trnTipo.eq(conciliacaoTransacaoListFilter.getTrnTipo());
			where.and(trnTipoIsEqualTo);
		}
		// End field: trnTipo
		
		
		// Begin field: TrnValor
		java.math.BigDecimal fieldFromTrnValor = conciliacaoTransacaoListFilter.getTrnValorFrom();
		java.math.BigDecimal fieldToTrnValor = conciliacaoTransacaoListFilter.getTrnValorTo();
		
		if (fieldFromTrnValor != null && fieldToTrnValor != null) {
			
			BooleanExpression between = qEntity.trnValor.between(fieldFromTrnValor, fieldToTrnValor);
			where.and(between);
		}
		else {
			if (fieldFromTrnValor != null) {
				where.and(qEntity.trnValor.goe(fieldFromTrnValor));
			}
			else if (fieldToTrnValor != null) {
				where.and(qEntity.trnValor.loe(fieldToTrnValor));				
			}
		}
		// End field: TrnValor
		
		// Begin field: conciliacaoBancariaId
		if (conciliacaoTransacaoListFilter.getConciliacaoBancariaId() != null) {
			BooleanExpression conciliacaoBancariaIdIsEqualTo = qEntity.conciliacaoBancaria.id.eq(conciliacaoTransacaoListFilter.getConciliacaoBancariaId());
			where.and(conciliacaoBancariaIdIsEqualTo);
		}
		// End field: conciliacaoBancariaId
		
		// Begin field: situacaoConciliacaoTrn
		if (conciliacaoTransacaoListFilter.getSituacaoConciliacaoTrn() != null) {
			BooleanExpression situacaoConciliacaoTrnIsEqualTo = qEntity.situacaoConciliacaoTrn.eq(conciliacaoTransacaoListFilter.getSituacaoConciliacaoTrn());
			where.and(situacaoConciliacaoTrnIsEqualTo);
		}
		// End field: situacaoConciliacaoTrn
		
		// Begin field: ConciliadoComErro
		if (conciliacaoTransacaoListFilter.getConciliadoComErroIsNotNull() != null) {		
			if (conciliacaoTransacaoListFilter.isConciliadoComErroIsNotNull()) {
				where.and(qEntity.conciliadoComErro.isNotNull().and(qEntity.conciliadoComErro.isTrue()));
			}
			else {
				where.and(qEntity.conciliadoComErro.isNull().or(qEntity.conciliadoComErro.isFalse()));
			}
		}
		// End field: ConciliadoComErro
		
		return where;
	}

}

