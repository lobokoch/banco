package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;

public interface ConciliacaoService {

	ConciliacaoBancariaAsyncExecution processarArquivo(InputStream stream);

	long countConciliacaoTransacaoComMaisDeUmTitulo(UUID id);

	void reprocessar(List<UUID> id);
	
	void reprocessarConciliacaoTransacaoAsync(List<ConciliacaoTransacaoEntity> transacoes);

}
