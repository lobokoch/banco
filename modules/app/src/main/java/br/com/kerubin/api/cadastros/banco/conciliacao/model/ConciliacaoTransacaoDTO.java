package br.com.kerubin.api.cadastros.banco.conciliacao.model;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaLookupResult;
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
public class ConciliacaoTransacaoDTO {
	
	private java.util.UUID id;
	
	private java.time.LocalDate trnData;
	
	private String trnHistorico;
	
	private String trnDocumento;
	
	private TipoTransacao trnTipo;
	
	private java.math.BigDecimal trnValor;
	
	private ConciliacaoBancariaLookupResult conciliacaoBancaria;
	
	private SituacaoConciliacaoTrn situacaoConciliacaoTrn;
	
	private java.util.UUID tituloConciliadoId;
	
	private String tituloConciliadoDesc;
	
	private java.time.LocalDate dataConciliacao;
	
	private Boolean conciliadoComErro;
	
	private String conciliadoMsg;
	
	private String createdBy;
	
	private java.time.LocalDateTime createdDate;
	
	private String lastModifiedBy;
	
	private java.time.LocalDateTime lastModifiedDate;

}
