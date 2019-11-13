package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import static br.com.kerubin.api.servicecore.util.CoreUtils.toPositive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webcohesion.ofx4j.domain.data.common.Transaction;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoOFXReader;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.QConciliacaoTransacaoEntity;
import lombok.extern.slf4j.Slf4j;

import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

@Slf4j
@Service
public class ConciliacaoServiceHelperImpl implements ConciliacaoServiceHelper {
	
	@Inject
	private ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@Inject
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public List<ConciliacaoTransacaoEntity> criarTransacoes(ConciliacaoBancariaEntity conciliacaoBancariaEntity, ConciliacaoOFXReader reader) {
		
		List<Transaction> transactions = reader.getTransactions();
		
		// Desconsidera lançamentos futuros. Cuidar que se tiver lançamentos futuros do dia, vão ser computados.
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		transactions = transactions.stream().filter(it -> toLocalDate(it.getDatePosted()).isBefore(tomorrow)).collect(Collectors.toList());
		
		List<ConciliacaoTransacaoEntity> transacoes = new ArrayList<>(transactions.size());
		for (Transaction transacao : transactions) {
			ConciliacaoTransacaoEntity entity = new ConciliacaoTransacaoEntity();
			entity.setConciliacaoBancaria(conciliacaoBancariaEntity);
			
			entity.setTrnData(toLocalDate(transacao.getDatePosted()));
			entity.setTrnHistorico(transacao.getMemo());
			entity.setTrnDocumento(reader.getTransactionDocument(transacao));
			
			TipoTransacao trnTipo = TipoTransacao.OUTROS;
			switch (transacao.getTransactionType()) {
			case CREDIT:
				trnTipo = TipoTransacao.CREDITO;
				break;
				
			case DEBIT:
				trnTipo = TipoTransacao.DEBITO;
				break;

			default:
				break;
			}
			entity.setTrnTipo(trnTipo);
			
			BigDecimal valor = BigDecimal.valueOf(transacao.getAmount());
			valor = toPositive(valor); // DEBIT, geralmente virá como valor negativo
			
			entity.setTrnValor(valor);
			
			entity.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.NAO_CONCILIADO);
			
			try {
				entity = conciliacaoTransacaoRepository.saveAndFlush(entity);
				transacoes.add(entity);
			} catch(Exception e) {
				log.error("Erro ao salvar transação da conciliação bancária: " + e.getMessage(), e);
				throw e;
			}
			
		} // for
		
		return transacoes;
	}
	
	@Transactional
	@Override
	public List<ConciliacaoTransacaoEntity> salvarTransacoes(List<ConciliacaoTransacaoEntity> transacoes) {
		try {
			transacoes = conciliacaoTransacaoRepository.saveAll(transacoes);
			conciliacaoTransacaoRepository.flush();
			return transacoes;
		} catch (Exception e) {
			log.error("Erro ao salvar lote de trandações de conciliação bancária. Erro: " + e.getMessage(), e);
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ConciliacaoBancariaEntity createConciliacaoBancaria(ConciliacaoOFXReader reader) {
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = new ConciliacaoBancariaEntity();
		conciliacaoBancariaEntity.setBancoId(reader.getBankId());
		conciliacaoBancariaEntity.setAgenciaId(reader.getBranchId());
		conciliacaoBancariaEntity.setContaId(reader.getAccountNumber());
		conciliacaoBancariaEntity.setDataIni(reader.getTransactionsStartDate());
		conciliacaoBancariaEntity.setDataFim(reader.getTransactionsEndDate());
		conciliacaoBancariaEntity.setSituacaoConciliacao(SituacaoConciliacao.NAO_CONCILIADO);
		
		conciliacaoBancariaEntity = salvarConciliacao(conciliacaoBancariaEntity);
		
		return conciliacaoBancariaEntity;
	}
	
	@Transactional
	@Override
	public ConciliacaoBancariaEntity salvarConciliacao(ConciliacaoBancariaEntity conciliacaoBancariaEntity) {
		try {
			conciliacaoBancariaEntity = conciliacaoBancariaRepository.saveAndFlush(conciliacaoBancariaEntity);
			return conciliacaoBancariaEntity;
		} catch (Exception e) {
			log.error("Erro ao salvar conciliação bancária. Erro: " + e.getMessage(), e);
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ConciliacaoTransacaoEntity> buscarTransacoesAConciliar(
			ConciliacaoBancariaEntity conciliacaoBancariaEntity) {

		JPAQueryFactory query = new JPAQueryFactory(em);
		QConciliacaoTransacaoEntity qConciliacaoTransacaoEntity = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity;
		
		List<SituacaoConciliacaoTrn> situacoes = Arrays.asList(SituacaoConciliacaoTrn.CONCILIAR_CONTAS_PAGAR, 
				SituacaoConciliacaoTrn.CONCILIAR_CONTAS_RECEBER, 
				SituacaoConciliacaoTrn.CONCILIAR_CAIXA);
		
		BooleanExpression filtro = qConciliacaoTransacaoEntity.conciliacaoBancaria.id.eq(conciliacaoBancariaEntity.getId());
		filtro.and(qConciliacaoTransacaoEntity.situacaoConciliacaoTrn.in(situacoes));
		
		
		List<ConciliacaoTransacaoEntity> result = query.
			selectFrom(qConciliacaoTransacaoEntity)
			.where(filtro)
			.orderBy(qConciliacaoTransacaoEntity.situacaoConciliacaoTrn.asc())
			.fetch();
		
		return result;
	}

}
