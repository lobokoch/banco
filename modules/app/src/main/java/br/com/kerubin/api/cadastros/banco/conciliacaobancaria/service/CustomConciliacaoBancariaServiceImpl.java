package br.com.kerubin.api.cadastros.banco.conciliacaobancaria.service;

import java.util.Iterator;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaListFilter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.QConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.QConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.QConciliacaoTransacaoTituloEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Service
public class CustomConciliacaoBancariaServiceImpl extends ConciliacaoBancariaServiceImpl {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	@Override
	public Page<ConciliacaoBancariaEntity> list(ConciliacaoBancariaListFilter conciliacaoBancariaListFilter,
			Pageable pageable) {
		
		String fieldId = QConciliacaoBancariaEntity.conciliacaoBancariaEntity.id.getMetadata().getName();
		String fieldDataFim = QConciliacaoBancariaEntity.conciliacaoBancariaEntity.dataFim.getMetadata().getName();
		String fieldDataIni = QConciliacaoBancariaEntity.conciliacaoBancariaEntity.dataIni.getMetadata().getName();
		
		Iterator<Order> orders = pageable.getSort().iterator();
		if (pageable.getSort().isSorted() && orders.hasNext()) {
			Order order = orders.next();
			if (fieldId.equals(order.getProperty()) && !orders.hasNext()) {
				Sort sort = Sort.by(fieldDataFim).descending()
						.and(Sort.by(fieldDataIni).descending());
				
				pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
				
			}
		}
		
		return super.list(conciliacaoBancariaListFilter, pageable);
		
	}
	
	@Transactional
	@Override
	public void delete(UUID id) {
		log.info("INÍCIO da exclusão da conciliação id: {}", id);
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		QConciliacaoTransacaoTituloEntity titulo = QConciliacaoTransacaoTituloEntity.conciliacaoTransacaoTituloEntity;
		QConciliacaoTransacaoEntity transacao = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity; 
		
		JPQLQuery<UUID> transacoesIds = JPAExpressions.select(transacao.id).from(transacao).where(transacao.conciliacaoBancaria.id.eq(id));
		
		log.info("Excluíndo títulos da conciliação id: {}", id);
		long deletedCount = query.delete(titulo).where(titulo.conciliacaoTransacao.id.in(transacoesIds)).execute();
		log.info("{} títulos excluidos para conciliação id: {}.", deletedCount, id);
		
		log.info("Excluíndo transações da conciliação id: {}", id);
		deletedCount = query.delete(transacao).where(transacao.conciliacaoBancaria.id.eq(id)).execute();
		log.info("{} transações excluidas para conciliação id: {}.", deletedCount, id);
		
		log.info("Excluíndo conciliação id: {}", id);
		super.delete(id);
		log.info("Conciliação id: {} foi excluída com êxito.", id);
		
		log.info("FIM da exclusão da conciliação id: {}", id);
	}

}
