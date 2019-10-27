package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.util.List;

import br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoOFXReader;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoContext;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.database.core.ServiceContextData;

public interface ConciliacaoServiceHelper {
	
	ConciliacaoBancariaEntity createConciliacaoBancaria(ConciliacaoOFXReader reader);
	ConciliacaoBancariaEntity salvarConciliacao(ConciliacaoBancariaEntity conciliacaoBancariaEntity);

	ConciliacaoContext criarTransacoes(ConciliacaoContext contexto, ConciliacaoOFXReader reader);
	void salvarTransacoes(List<ConciliacaoTransacaoEntity> transacoesAlteradas, ServiceContextData contexto);


}
