/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaLookupResult;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaLookupResult;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTitulo;

public class ConciliacaoTransacao {

	private java.util.UUID id;
	
	@NotBlank(message="\"Identificador da transação\" é obrigatório.")
	@Size(max = 255, message = "\"Identificador da transação\" pode ter no máximo 255 caracteres.")
	private String trnId;
	
	@NotNull(message="\"Data\" é obrigatório.")
	private java.time.LocalDate trnData;
	
	@NotBlank(message="\"Histórico\" é obrigatório.")
	@Size(max = 255, message = "\"Histórico\" pode ter no máximo 255 caracteres.")
	private String trnHistorico;
	
	@NotBlank(message="\"Documento\" é obrigatório.")
	@Size(max = 255, message = "\"Documento\" pode ter no máximo 255 caracteres.")
	private String trnDocumento;
	
	@NotNull(message="\"trnTipo\" é obrigatório.")
	private TipoTransacao trnTipo;
	
	@NotNull(message="\"Valor\" é obrigatório.")
	private java.math.BigDecimal trnValor;
	
	@NotNull(message="\"Conciliação bancária\" é obrigatório.")
	private ConciliacaoBancariaLookupResult conciliacaoBancaria;
	
	@NotNull(message="\"situacaoConciliacaoTrn\" é obrigatório.")
	private SituacaoConciliacaoTrn situacaoConciliacaoTrn;
	
	private java.util.UUID tituloConciliadoId;
	
	@Size(max = 255, message = "\"Desc. tít. conc.\" pode ter no máximo 255 caracteres.")
	private String tituloConciliadoDesc;
	
	private java.math.BigDecimal tituloConciliadoValor;
	
	private java.time.LocalDate tituloConciliadoDataVen;
	
	private java.time.LocalDate tituloConciliadoDataPag;
	
	private PlanoContaLookupResult tituloPlanoContas;
	
	private java.time.LocalDate dataConciliacao;
	
	private java.util.List<ConciliacaoTransacaoTitulo> conciliacaoTransacaoTitulos;
	
	private Boolean conciliadoComErro = false;
	
	@Size(max = 255, message = "\"Mensagem\" pode ter no máximo 255 caracteres.")
	private String conciliadoMsg;
	
	@Size(max = 255, message = "\"Criado por\" pode ter no máximo 255 caracteres.")
	private String createdBy;
	
	private java.time.LocalDateTime createdDate;
	
	@Size(max = 255, message = "\"Alterado por\" pode ter no máximo 255 caracteres.")
	private String lastModifiedBy;
	
	private java.time.LocalDateTime lastModifiedDate;
	
	
	public ConciliacaoTransacao() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	
	public java.util.UUID getId() {
		return id;
	}
	
	public String getTrnId() {
		return trnId;
	}
	
	public java.time.LocalDate getTrnData() {
		return trnData;
	}
	
	public String getTrnHistorico() {
		return trnHistorico;
	}
	
	public String getTrnDocumento() {
		return trnDocumento;
	}
	
	public TipoTransacao getTrnTipo() {
		return trnTipo;
	}
	
	public java.math.BigDecimal getTrnValor() {
		return trnValor;
	}
	
	public ConciliacaoBancariaLookupResult getConciliacaoBancaria() {
		return conciliacaoBancaria;
	}
	
	public SituacaoConciliacaoTrn getSituacaoConciliacaoTrn() {
		return situacaoConciliacaoTrn;
	}
	
	public java.util.UUID getTituloConciliadoId() {
		return tituloConciliadoId;
	}
	
	public String getTituloConciliadoDesc() {
		return tituloConciliadoDesc;
	}
	
	public java.math.BigDecimal getTituloConciliadoValor() {
		return tituloConciliadoValor;
	}
	
	public java.time.LocalDate getTituloConciliadoDataVen() {
		return tituloConciliadoDataVen;
	}
	
	public java.time.LocalDate getTituloConciliadoDataPag() {
		return tituloConciliadoDataPag;
	}
	
	public PlanoContaLookupResult getTituloPlanoContas() {
		return tituloPlanoContas;
	}
	
	public java.time.LocalDate getDataConciliacao() {
		return dataConciliacao;
	}
	
	public java.util.List<ConciliacaoTransacaoTitulo> getConciliacaoTransacaoTitulos() {
		return conciliacaoTransacaoTitulos;
	}
	
	public Boolean getConciliadoComErro() {
		return conciliadoComErro;
	}
	
	public String getConciliadoMsg() {
		return conciliadoMsg;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public java.time.LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public java.time.LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setTrnId(String trnId) {
		this.trnId = trnId;
	}
	
	public void setTrnData(java.time.LocalDate trnData) {
		this.trnData = trnData;
	}
	
	public void setTrnHistorico(String trnHistorico) {
		this.trnHistorico = trnHistorico;
	}
	
	public void setTrnDocumento(String trnDocumento) {
		this.trnDocumento = trnDocumento;
	}
	
	public void setTrnTipo(TipoTransacao trnTipo) {
		this.trnTipo = trnTipo;
	}
	
	public void setTrnValor(java.math.BigDecimal trnValor) {
		this.trnValor = trnValor;
	}
	
	public void setConciliacaoBancaria(ConciliacaoBancariaLookupResult conciliacaoBancaria) {
		this.conciliacaoBancaria = conciliacaoBancaria;
	}
	
	public void setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn situacaoConciliacaoTrn) {
		this.situacaoConciliacaoTrn = situacaoConciliacaoTrn;
	}
	
	public void setTituloConciliadoId(java.util.UUID tituloConciliadoId) {
		this.tituloConciliadoId = tituloConciliadoId;
	}
	
	public void setTituloConciliadoDesc(String tituloConciliadoDesc) {
		this.tituloConciliadoDesc = tituloConciliadoDesc;
	}
	
	public void setTituloConciliadoValor(java.math.BigDecimal tituloConciliadoValor) {
		this.tituloConciliadoValor = tituloConciliadoValor;
	}
	
	public void setTituloConciliadoDataVen(java.time.LocalDate tituloConciliadoDataVen) {
		this.tituloConciliadoDataVen = tituloConciliadoDataVen;
	}
	
	public void setTituloConciliadoDataPag(java.time.LocalDate tituloConciliadoDataPag) {
		this.tituloConciliadoDataPag = tituloConciliadoDataPag;
	}
	
	public void setTituloPlanoContas(PlanoContaLookupResult tituloPlanoContas) {
		this.tituloPlanoContas = tituloPlanoContas;
	}
	
	public void setDataConciliacao(java.time.LocalDate dataConciliacao) {
		this.dataConciliacao = dataConciliacao;
	}
	
	public void setConciliacaoTransacaoTitulos(java.util.List<ConciliacaoTransacaoTitulo> conciliacaoTransacaoTitulos) {
		this.conciliacaoTransacaoTitulos = conciliacaoTransacaoTitulos;
	}
	
	public void setConciliadoComErro(Boolean conciliadoComErro) {
		this.conciliadoComErro = conciliadoComErro;
	}
	
	public void setConciliadoMsg(String conciliadoMsg) {
		this.conciliadoMsg = conciliadoMsg;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setCreatedDate(java.time.LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public void setLastModifiedDate(java.time.LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConciliacaoTransacao other = (ConciliacaoTransacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return 31;
	}
	
	/* 
	@Override
	public String toString() {
		// Enabling toString for JPA entities will implicitly trigger lazy loading on all fields.
	}
	*/

}
