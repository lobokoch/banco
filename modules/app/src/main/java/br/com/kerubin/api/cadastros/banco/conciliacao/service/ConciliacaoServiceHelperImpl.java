package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webcohesion.ofx4j.domain.data.common.Transaction;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoOFXReader;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoContext;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaRepository;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoRepository;
import br.com.kerubin.api.database.core.ServiceContext;
import br.com.kerubin.api.database.core.ServiceContextData;
import lombok.extern.slf4j.Slf4j;

import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

@Slf4j
@Service
public class ConciliacaoServiceHelperImpl implements ConciliacaoServiceHelper {
	
	@Inject
	private ConciliacaoBancariaRepository conciliacaoBancariaRepository;
	
	@Inject
	private ConciliacaoTransacaoRepository conciliacaoTransacaoRepository;
	
	@Transactional
	@Override
	public ConciliacaoContext criarTransacoes(ConciliacaoContext contexto, ConciliacaoOFXReader reader) {
		
		ServiceContext.applyServiceContextData(contexto.getServiceContextData());
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = contexto.getConciliacaoBancariaEntity();
		
		List<Transaction> transactions = reader.getTransactions();
		List<ConciliacaoTransacaoEntity> transacoes = new ArrayList<>(transactions.size());
		for (Transaction transacao : transactions) {
			ConciliacaoTransacaoEntity entity = new ConciliacaoTransacaoEntity();
			entity.setConciliacaoBancaria(conciliacaoBancariaEntity);
			
			entity.setTrnData(reader.toDate(transacao.getDatePosted()));
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
		
		contexto.setTransacoes(transacoes);
		
		return contexto;
	}
	
	@Transactional
	@Override
	public void salvarTransacoes(List<ConciliacaoTransacaoEntity> transacoesAlteradas) {
		try {
			conciliacaoTransacaoRepository.saveAll(transacoesAlteradas);
			conciliacaoTransacaoRepository.flush();
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

}
