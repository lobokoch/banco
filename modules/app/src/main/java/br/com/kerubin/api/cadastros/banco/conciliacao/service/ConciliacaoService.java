package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.io.InputStream;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;

public interface ConciliacaoService {

	ConciliacaoBancariaAsyncExecution processarArquivo(InputStream stream);

}
