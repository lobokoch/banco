package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface ConciliacaoTransacaoFindByIdIn {
	
	List<java.util.UUID> getIds();

}
