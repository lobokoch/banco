package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import static br.com.kerubin.api.servicecore.util.CoreUtils.toLocalDate;
import static br.com.kerubin.api.servicecore.util.CoreUtils.toPositive;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
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
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.QConciliacaoTransacaoTituloEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciliacaoServiceHelperImpl implements ConciliacaoServiceHelper {
	
	@Inject
	private ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@Inject
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@PersistenceContext	
	private EntityManager em;
	
	@Transactional(readOnly = true)
	@Override
	public long countConciliacaoTransacaoComMaisDeUmTituloCandidato(UUID conciliacaoBancariaId) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		QConciliacaoTransacaoEntity qConciliacaoTransacao = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity;
		QConciliacaoTransacaoTituloEntity qConciliacaoTransacaoTitulo = QConciliacaoTransacaoTituloEntity.conciliacaoTransacaoTituloEntity;
		
		NumberExpression<Long> conciliacaoTransacaoCount = qConciliacaoTransacao.id.count();
		
		BooleanExpression existsQuery = JPAExpressions.selectOne()
			.from(qConciliacaoTransacaoTitulo)
			.where(qConciliacaoTransacaoTitulo.conciliacaoTransacao.id.eq(qConciliacaoTransacao.id))
			.having(qConciliacaoTransacaoTitulo.conciliacaoTransacao.id.count().gt(1))
			.groupBy(qConciliacaoTransacaoTitulo.conciliacaoTransacao.id)
			.exists();
		
		JPAQuery<Long> countQuery = query.select(conciliacaoTransacaoCount.as("conciliacaoTransacaoCount"))
			.from(qConciliacaoTransacao)
			.where(
					qConciliacaoTransacao.conciliacaoBancaria.id.eq(conciliacaoBancariaId)
					.and(existsQuery)
			);
		
		long count = countQuery.fetchOne();
		
		return count;
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public long countConciliacaoTransacaoComTitulosRepetidos(UUID conciliacaoBancariaId) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		QConciliacaoTransacaoEntity qConciliacaoTransacao = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity;
		
		List<Integer> resut = query
			.selectOne()
			.from(qConciliacaoTransacao)
			.where(qConciliacaoTransacao.conciliacaoBancaria.id.eq(conciliacaoBancariaId)
					.and(qConciliacaoTransacao.tituloConciliadoMultiple.isFalse()))
			.groupBy(qConciliacaoTransacao.tituloConciliadoId)
			.having(qConciliacaoTransacao.tituloConciliadoId.count().gt(1L))
			.fetch();
		
		long count = resut.size();
		
		return count;
	}
	
	@Transactional
	@Override
	public List<ConciliacaoTransacaoEntity> criarTransacoes(ConciliacaoBancariaEntity conciliacaoBancariaEntity, ConciliacaoOFXReader reader) {
		
		List<Transaction> transactions = reader.getTransactions();
		
		// transactions = transactions.stream().filter(it -> Math.abs(it.getAmount()) == 28.21).collect(Collectors.toList());
		
		// Desconsidera lançamentos futuros. Cuidar que se tiver lançamentos futuros do dia, vão ser computados.
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		transactions = transactions.stream().filter(it -> toLocalDate(it.getDatePosted()).isBefore(tomorrow)).collect(Collectors.toList());
		
		List<ConciliacaoTransacaoEntity> transacoes = new ArrayList<>(transactions.size());
		for (Transaction transaction : transactions) {
			ConciliacaoTransacaoEntity entity = new ConciliacaoTransacaoEntity();
			entity.setConciliacaoBancaria(conciliacaoBancariaEntity);
			
			entity.setTrnId(reader.getTransactionId(transaction));
			entity.setTrnData(toLocalDate(transaction.getDatePosted()));
			entity.setTrnHistorico(transaction.getMemo());
			entity.setTrnDocumento(reader.getTransactionDocument(transaction));
			
			TipoTransacao trnTipo = TipoTransacao.OUTROS;
			switch (transaction.getTransactionType()) {
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
			
			BigDecimal valor = BigDecimal.valueOf(transaction.getAmount());
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
		List<ConciliacaoTransacaoEntity> transacoesSalvas = new ArrayList<>(transacoes.size());
		transacoes.forEach(transacao -> {
			try {
				ConciliacaoTransacaoEntity t = conciliacaoTransacaoRepository.save(transacao);
				conciliacaoTransacaoRepository.flush();
				transacoesSalvas.add(t);
			} catch (Exception e) {
				log.error(MessageFormat.format("Erro ao salvar trandação id: {0} de conciliação bancária. Erro: {1}", transacao.getId(), e.getMessage()), e);
				
				transacao.setConciliadoComErro(true);
				transacao.setConciliadoMsg(e.getMessage());
				//transacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.ERRO);
				transacoesSalvas.add(transacao);
			}
		});
		
		return transacoesSalvas;
	}
	
	@Transactional
	@Override
	public ConciliacaoTransacaoEntity salvarTransacao(ConciliacaoTransacaoEntity transacao) {
		
			try {
				return salvarTransacaoEx(transacao);
			} catch (Exception e) {
				log.error(MessageFormat.format("Erro ao salvar trandação id: {0} de conciliação bancária. Erro: {1}", transacao.getId(), e.getMessage()), e);
				
				transacao.setConciliadoComErro(true);
				transacao.setConciliadoMsg(e.getMessage());
				//transacao.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.ERRO);
			}
		
		return transacao;
	}
	
	private ConciliacaoTransacaoEntity salvarTransacaoEx(ConciliacaoTransacaoEntity transacao) {
		ConciliacaoTransacaoEntity transacaoSalva = conciliacaoTransacaoRepository.save(transacao);
		conciliacaoTransacaoRepository.flush();
		return transacaoSalva;
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
