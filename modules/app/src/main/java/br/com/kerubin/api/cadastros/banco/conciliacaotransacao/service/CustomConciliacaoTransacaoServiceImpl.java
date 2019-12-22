package br.com.kerubin.api.cadastros.banco.conciliacaotransacao.service;

import static br.com.kerubin.api.servicecore.util.CoreUtils.isNotEmpty;
import static br.com.kerubin.api.servicecore.util.CoreUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.TipoPlanoContaFinanceiro;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacao;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.QConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoCompleteImpl;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaEntity;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.QPlanoContaEntity;

@Primary
@Service
public class CustomConciliacaoTransacaoServiceImpl extends ConciliacaoTransacaoServiceImpl {
	
	private static final String EMPTY_PLANO_CONTA_PAI_DESC =  " -  / ";
	
	@Autowired
	private EntityManager em;
	
	@Override
	public ConciliacaoTransacaoEntity update(UUID id, ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		ConciliacaoTransacaoEntity atual = read(id);
		// em.detach(atual);
		
		boolean atualTemTitulos = isNotEmpty(atual.getConciliacaoTransacaoTitulos());
		
		// Se removeu todos os títulos, entende que deseja lançar no caixa.
		if (atualTemTitulos && isEmpty(conciliacaoTransacaoEntity.getConciliacaoTransacaoTitulos())) {
			// conciliacaoTransacaoEntity.setTituloConciliadoDesc(null); esse o usuário pode querer alterar
			conciliacaoTransacaoEntity.setTituloConciliadoId(null);
			conciliacaoTransacaoEntity.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIAR_CAIXA);
			conciliacaoTransacaoEntity.setDataConciliacao(null);
			
			conciliacaoTransacaoEntity.setTituloConciliadoDesc(null);
			conciliacaoTransacaoEntity.setTituloConciliadoValor(null);
			conciliacaoTransacaoEntity.setTituloConciliadoDataVen(null);
			conciliacaoTransacaoEntity.setTituloConciliadoDataPag(null);
			conciliacaoTransacaoEntity.setTituloPlanoContas(null);
			conciliacaoTransacaoEntity.setConciliadoMsg(null);
			conciliacaoTransacaoEntity.setConciliadoComErro(false);
		}
		
		ConciliacaoTransacaoEntity result =  super.update(id, conciliacaoTransacaoEntity);
		
		decoratePlanoContas(result);
		
		return result;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ConciliacaoTransacaoEntity> list(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter,
			Pageable pageable) {
		
		String fieldId = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.id.getMetadata().getName();
		String fieldTrnValor = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnValor.getMetadata().getName();
		String fieldTrnTipo = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnTipo.getMetadata().getName();
		String fieldTrnData = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnData.getMetadata().getName();
		String fieldTrnDocumento = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnDocumento.getMetadata().getName();
		
		
		boolean conciliadoComMaisDeUmTitulo = false;
		boolean conciliacaoTransacaoComMesmoTitulo = false;
		if (isNotEmpty(conciliacaoTransacaoListFilter.getCustomParams())) {
			Map<Object, Object> customParams = conciliacaoTransacaoListFilter.getCustomParams();
			
			
			Object objConciliadoComMaisDeUmTitulo = customParams.get("conciliadoComMaisDeUmTitulo");
			if (isNotEmpty(objConciliadoComMaisDeUmTitulo)) {
				conciliadoComMaisDeUmTitulo = (boolean) objConciliadoComMaisDeUmTitulo;
			}
			
			Object objConciliacaoTransacaoComMesmoTitulo = customParams.get("conciliacaoTransacaoComMesmoTitulo");
			if (isNotEmpty(objConciliacaoTransacaoComMesmoTitulo)) {
				conciliacaoTransacaoComMesmoTitulo = (boolean) objConciliacaoTransacaoComMesmoTitulo;
			}
		}
		
		
		if (!conciliadoComMaisDeUmTitulo && !conciliacaoTransacaoComMesmoTitulo) {
			Iterator<Order> orders = pageable.getSort().iterator();
			if (pageable.getSort().isSorted() && orders.hasNext()) {
				Order order = orders.next();
				if (fieldId.equals(order.getProperty()) && !orders.hasNext()) {
					Sort sort = Sort.by(fieldTrnData).ascending()
							.and(Sort.by(fieldTrnTipo).descending())
							.and(Sort.by(fieldTrnValor).descending())
							.and(Sort.by(fieldTrnDocumento).ascending());
					
					pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
					
				}
			}
		}
		
		if (conciliadoComMaisDeUmTitulo) {
			
			Sort sort = Sort.by(fieldTrnValor).descending()
					.and(Sort.by(fieldTrnTipo).descending())
					.and(Sort.by(fieldTrnData).ascending())
					.and(Sort.by(fieldTrnDocumento).ascending());
					//.and(Sort.by(fieldTrnHistorico).ascending());
			
			if (pageable.getSort().isSorted()) {
				sort.and(pageable.getSort());
			}
			
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
	
		if (conciliacaoTransacaoComMesmoTitulo) {
			
			String tituloConciliadoId = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.tituloConciliadoId.getMetadata().getName();
			
			Sort sort = Sort.by(tituloConciliadoId)
					.and(Sort.by(fieldTrnTipo).descending())
					.and(Sort.by(fieldTrnData).ascending())
					.and(Sort.by(fieldTrnDocumento).ascending());
			
			if (pageable.getSort().isSorted()) {
				sort = sort.and(pageable.getSort());
			}
			
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
			
		}
			
		
		Page<ConciliacaoTransacaoEntity> result = super.list(conciliacaoTransacaoListFilter, pageable);
		
		if (isNotEmpty(result)) {
			result.forEach(item -> decoratePlanoContas(item));
		}
		
		return result;
	}
	
	private void decoratePlanoContas(ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		PlanoContaEntity planoContas = conciliacaoTransacaoEntity.getTituloPlanoContas();
		if (isNotEmpty(planoContas)) { // Adjusts the field descricao of plano de contas and plano de contas pai.
			planoContas = planoContas.clone(); 
			conciliacaoTransacaoEntity.setTituloPlanoContas(planoContas);
			String descricao = planoContas.getCodigo() + " - " + planoContas.getDescricao();
			PlanoContaEntity planoContasPai = planoContas.getPlanoContaPai();
			if (isNotEmpty(planoContasPai)) {
				descricao = planoContasPai.getCodigo() + " - " + planoContasPai.getDescricao() + " / " + descricao;
			}
			planoContas.setDescricao(descricao);
		} 
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(String query, 
			ConciliacaoTransacao conciliacaoTransacao) {
		
		TipoPlanoContaFinanceiro tipoPlanoContaFinanceiro = null;
		
		if (isNotEmpty(conciliacaoTransacao)) {
			switch (conciliacaoTransacao.getTrnTipo()) {
			case CREDITO:
				tipoPlanoContaFinanceiro = TipoPlanoContaFinanceiro.RECEITA;
				break;
				
			case DEBITO:
				tipoPlanoContaFinanceiro = TipoPlanoContaFinanceiro.DESPESA;
				break;
				
			default:
				break;
			}
		}
		
		JPAQueryFactory queryDSL = new JPAQueryFactory(em);
		QPlanoContaEntity qPlanoConta = QPlanoContaEntity.planoContaEntity;
		QPlanoContaEntity qPlanoContaPai = new QPlanoContaEntity("planoContaPai");
		
		BooleanBuilder where = new BooleanBuilder();
		if (isNotEmpty(query)) {
			where.and(qPlanoConta.descricao.containsIgnoreCase(query).or(qPlanoContaPai.descricao.containsIgnoreCase(query)));
		}
		
		where.and(qPlanoConta.ativo.isTrue())
		.and(qPlanoConta.deleted.isFalse().or(qPlanoConta.deleted.isNull()));
		
		if (isNotEmpty(tipoPlanoContaFinanceiro)) {
			where.and(qPlanoConta.tipoFinanceiro.eq(tipoPlanoContaFinanceiro));
		}
		
		StringExpression descricaoConcatenation = emptyIfNull(qPlanoContaPai.codigo)
				.concat(" - ")
				.concat(emptyIfNull(qPlanoContaPai.descricao))
				.concat(" / ")
				.concat(qPlanoConta.codigo)
				.concat(" - ")
				.concat(qPlanoConta.descricao);
		
		JPAQuery<Tuple> query_ = queryDSL.select(
				qPlanoConta.id, 
				qPlanoConta.codigo,
				descricaoConcatenation
				)
		.from(qPlanoConta)
		.leftJoin(qPlanoConta.planoContaPai, qPlanoContaPai).on(qPlanoContaPai.id.eq(qPlanoConta.planoContaPai.id))
		.where(where)
		.orderBy(qPlanoConta.codigo.asc());
		
		List<Tuple> tuples = query_.fetch();
		
		List<PlanoContaAutoComplete> items = new ArrayList<>();
		if (tuples != null && !tuples.isEmpty()) {
			for (Tuple tuple: tuples) {
				PlanoContaAutoCompleteImpl plano = new PlanoContaAutoCompleteImpl();
				plano.setId(tuple.get(0, UUID.class));
				plano.setCodigo(tuple.get(1, String.class));
				plano.setDescricao(tuple.get(2, String.class));
				items.add(plano);
			} // for
		}
		
		items = items.stream()
				.peek(it -> it.setDescricao(it.getDescricao().replace(EMPTY_PLANO_CONTA_PAI_DESC, "")))
				.sorted(Comparator.comparingInt(this::codigoToInt))
				.collect(Collectors.toList());
		
		return items;
		
	}
	
	private int codigoToInt(Object toOrderObj) {
		if (toOrderObj != null && toOrderObj instanceof PlanoContaAutoComplete) {
			PlanoContaAutoComplete toOrder = (PlanoContaAutoComplete) toOrderObj;
			String codigo = toOrder.getCodigo();
			if (isNotEmpty(codigo)) {
				codigo = codigo.replace(".", "");
				try {
					return Integer.parseInt(codigo);
				} catch(Exception e) {
					return 0;
				}
			}
		}
		
		return 0;
	}
	
	private static StringExpression emptyIfNull(StringExpression expression) {
	    return expression.coalesce("").asString();
	}
	
	

}
