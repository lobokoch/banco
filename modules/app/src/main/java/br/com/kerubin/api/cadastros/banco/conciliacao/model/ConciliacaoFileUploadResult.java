package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoFileUploadResult {
	
	private UUID conciliacaoId;
	private boolean result;
	private String message;

}
