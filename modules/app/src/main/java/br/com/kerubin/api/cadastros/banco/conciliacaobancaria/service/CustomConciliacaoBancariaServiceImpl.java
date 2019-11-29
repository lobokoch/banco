package br.com.kerubin.api.cadastros.banco.conciliacaobancaria.service;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.QConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.QConciliacaoTransacaoTituloEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Service
public class CustomConciliacaoBancariaServiceImpl extends ConciliacaoBancariaServiceImpl {
	
	@PersistenceContext
	private EntityManager em;
	
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
