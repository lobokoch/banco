package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ParametrosReprocessarDTO {
	
	private List<UUID> ids;

}
