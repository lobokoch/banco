package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.io.InputStream;
import java.util.UUID;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;

public interface ConciliacaoService {

	ConciliacaoBancariaAsyncExecution processarArquivo(InputStream stream);

	long countConciliacaoTransacaoComMaisDeUmTitulo(UUID id);

}
