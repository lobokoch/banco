package br.com.kerubin.api.cadastros.banco.conciliacao.service;

import java.io.InputStream;
import java.util.UUID;

public interface ConciliacaoService {

	UUID processarArquivo(InputStream stream);

}
