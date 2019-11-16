package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import java.util.concurrent.CompletableFuture;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConciliacaoBancariaAsyncExecution {
	
	private ConciliacaoBancariaEntity conciliacaoBancaria;
	private CompletableFuture<ConciliacaoBancariaEntity> future;
	

}
