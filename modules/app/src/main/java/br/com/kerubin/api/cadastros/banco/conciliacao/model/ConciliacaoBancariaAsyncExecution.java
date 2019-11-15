package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConciliacaoBancariaAsyncExecution {
	
	private UUID id;
	private CompletableFuture<ConciliacaoBancariaEntity> future;
	

}
