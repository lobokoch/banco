/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;

public class ConciliacaoTransacaoListFilter {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.time.LocalDate trnDataFrom;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.time.LocalDate trnDataTo;
	
	private java.util.List<String> trnHistorico;
	
	private java.util.List<String> trnDocumento;
	
	private TipoTransacao trnTipo;
	
	private java.math.BigDecimal trnValorFrom;
	
	private java.math.BigDecimal trnValorTo;
	
	private java.util.UUID conciliacaoBancariaId;
	
	private SituacaoConciliacaoTrn situacaoConciliacaoTrn;
	
	private Boolean conciliadoComErroIsNotNull;
	
	// Map field for developer customizing parameters.
	private Map<Object, Object> customParams = new HashMap<>();
	
	public java.time.LocalDate getTrnDataFrom() {
		return trnDataFrom;
	}
	
	public void setTrnDataFrom(java.time.LocalDate trnDataFrom) {
		this.trnDataFrom = trnDataFrom;
	}
	
	public java.time.LocalDate getTrnDataTo() {
		return trnDataTo;
	}
	
	public void setTrnDataTo(java.time.LocalDate trnDataTo) {
		this.trnDataTo = trnDataTo;
	}
	
	public java.util.List<String> getTrnHistorico() {
		return trnHistorico;
	}
	
	public void setTrnHistorico(java.util.List<String> trnHistorico) {
		this.trnHistorico = trnHistorico;
	}
	
	public java.util.List<String> getTrnDocumento() {
		return trnDocumento;
	}
	
	public void setTrnDocumento(java.util.List<String> trnDocumento) {
		this.trnDocumento = trnDocumento;
	}
	
	public TipoTransacao getTrnTipo() {
		return trnTipo;
	}
			
	public void setTrnTipo(TipoTransacao trnTipo) {
		this.trnTipo = trnTipo;
	}
	
	public java.math.BigDecimal getTrnValorFrom() {
		return trnValorFrom;
	}
	
	public void setTrnValorFrom(java.math.BigDecimal trnValorFrom) {
		this.trnValorFrom = trnValorFrom;
	}
	
	public java.math.BigDecimal getTrnValorTo() {
		return trnValorTo;
	}
	
	public void setTrnValorTo(java.math.BigDecimal trnValorTo) {
		this.trnValorTo = trnValorTo;
	}
	
	public java.util.UUID getConciliacaoBancariaId() {
		return conciliacaoBancariaId;
	}
			
	public void setConciliacaoBancariaId(java.util.UUID conciliacaoBancariaId) {
		this.conciliacaoBancariaId = conciliacaoBancariaId;
	}
	
	public SituacaoConciliacaoTrn getSituacaoConciliacaoTrn() {
		return situacaoConciliacaoTrn;
	}
			
	public void setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn situacaoConciliacaoTrn) {
		this.situacaoConciliacaoTrn = situacaoConciliacaoTrn;
	}
	
	public Boolean isConciliadoComErroIsNotNull() {
		return conciliadoComErroIsNotNull != null && conciliadoComErroIsNotNull;
	}
	
	public Boolean getConciliadoComErroIsNotNull() {
		return conciliadoComErroIsNotNull;
	}
			
	public void setConciliadoComErroIsNotNull(Boolean conciliadoComErroIsNotNull) {
		this.conciliadoComErroIsNotNull = conciliadoComErroIsNotNull;
	}
	
	public Map<Object, Object> getCustomParams() {
		return customParams;
	}
	
	public void setCustomParams(Map<Object, Object> customParams) {
		this.customParams = customParams;
	}
	
}
