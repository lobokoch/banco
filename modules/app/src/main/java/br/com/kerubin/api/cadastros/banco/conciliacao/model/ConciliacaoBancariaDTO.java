package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import java.util.List;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoBancariaDTO {
	
	private java.util.UUID id;
	
	private String bancoId;
	
	private String agenciaId;
	
	private String contaId;
	
	private java.time.LocalDate dataIni;
	
	private java.time.LocalDate dataFim;
	
	private SituacaoConciliacao situacaoConciliacao;
	
	private List<ConciliacaoTransacaoDTO> transacoes;
	

}
