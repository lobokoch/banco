package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.util.List;

import br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoOFXReader;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;

public interface ConciliacaoServiceHelper {
	
	ConciliacaoBancariaEntity createConciliacaoBancaria(ConciliacaoOFXReader reader);
	ConciliacaoBancariaEntity salvarConciliacao(ConciliacaoBancariaEntity conciliacaoBancariaEntity);

	List<ConciliacaoTransacaoEntity> criarTransacoes(ConciliacaoBancariaEntity conciliacaoBancariaEntity, ConciliacaoOFXReader reader);
	List<ConciliacaoTransacaoEntity> salvarTransacoes(List<ConciliacaoTransacaoEntity> transacoesAlteradas);
	List<ConciliacaoTransacaoEntity> buscarTransacoesAConciliar(ConciliacaoBancariaEntity conciliacaoBancariaEntity);


}
