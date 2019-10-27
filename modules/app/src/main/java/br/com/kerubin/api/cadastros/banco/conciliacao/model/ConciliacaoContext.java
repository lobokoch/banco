package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import java.util.List;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.database.core.ServiceContextData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConciliacaoContext {
	
	private ServiceContextData serviceContextData;
	private ConciliacaoBancariaEntity conciliacaoBancariaEntity;
	private List<ConciliacaoTransacaoEntity> transacoes;

}
