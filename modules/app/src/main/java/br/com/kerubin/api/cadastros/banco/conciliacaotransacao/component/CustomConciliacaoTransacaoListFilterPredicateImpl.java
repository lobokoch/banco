package br.com.kerubin.api.cadastros.banco.conciliacaotransacao.component;

import static br.com.kerubin.api.servicecore.util.CoreUtils.isNotEmpty;

import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilterPredicateImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.QConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.QConciliacaoTransacaoTituloEntity;

@Primary
@Component
public class CustomConciliacaoTransacaoListFilterPredicateImpl extends ConciliacaoTransacaoListFilterPredicateImpl {
	
	@Override
	public Predicate mountAndGetPredicate(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter) {
		Predicate predicate =  super.mountAndGetPredicate(conciliacaoTransacaoListFilter);
		
		if (isNotEmpty(conciliacaoTransacaoListFilter.getCustomParams())) {
			Map<Object, Object> customParams = conciliacaoTransacaoListFilter.getCustomParams();
			Object objConciliadoComMaisDeUmTitulo = customParams.get("conciliadoComMaisDeUmTitulo");
			if (isNotEmpty(objConciliadoComMaisDeUmTitulo)) {
				boolean conciliadoComMaisDeUmTitulo = (boolean) objConciliadoComMaisDeUmTitulo;
				if (conciliadoComMaisDeUmTitulo) {
					QConciliacaoTransacaoEntity qConciliacaoTransacao = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity;
					QConciliacaoTransacaoTituloEntity qConciliacaoTransacaoTitulo = QConciliacaoTransacaoTituloEntity.conciliacaoTransacaoTituloEntity;
					
					BooleanExpression existsQuery = JPAExpressions.selectOne()
							.from(qConciliacaoTransacaoTitulo)
							.where(qConciliacaoTransacaoTitulo.conciliacaoTransacao.id.eq(qConciliacaoTransacao.id))
							.having(qConciliacaoTransacaoTitulo.conciliacaoTransacao.id.count().gt(1))
							.groupBy(qConciliacaoTransacaoTitulo.conciliacaoTransacao.id)
							.exists();
					
					((BooleanBuilder) predicate).and(existsQuery);
				}
				
			}
		}
		
		return predicate;
	}

}
