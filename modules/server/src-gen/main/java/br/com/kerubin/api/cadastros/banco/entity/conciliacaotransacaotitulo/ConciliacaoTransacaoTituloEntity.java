/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaEntity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;

@Entity
@Table(name = "conciliacao_transacao_titulo")
public class ConciliacaoTransacaoTituloEntity  {

	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Id
	@Column(name="id")
	private java.util.UUID id;
	
	@NotNull(message="\"Transações\" é obrigatório.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conciliacao_transacao")
	private ConciliacaoTransacaoEntity conciliacaoTransacao;
	
	@NotNull(message="\"Id. título\" é obrigatório.")
	@Column(name="titulo_conciliado_id")
	private java.util.UUID tituloConciliadoId;
	
	@NotBlank(message="\"Descrição título\" é obrigatório.")
	@Size(max = 255, message = "\"Descrição título\" pode ter no máximo 255 caracteres.")
	@Column(name="titulo_conciliado_desc")
	private String tituloConciliadoDesc;
	
	@Column(name="titulo_conciliado_valor")
	private java.math.BigDecimal tituloConciliadoValor;
	
	@NotNull(message="\"Data vencimento\" é obrigatório.")
	@Column(name="titulo_conciliado_data_ven")
	private java.time.LocalDate tituloConciliadoDataVen;
	
	@Column(name="titulo_conciliado_data_pag")
	private java.time.LocalDate tituloConciliadoDataPag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "titulo_plano_contas")
	private PlanoContaEntity tituloPlanoContas;
	
	@Column(name="data_conciliacao")
	private java.time.LocalDate dataConciliacao;
	
	@NotNull(message="\"situacaoConciliacaoTrn\" é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name="situacao_conciliacao_trn")
	private SituacaoConciliacaoTrn situacaoConciliacaoTrn;
	
	public java.util.UUID getId() {
		return id;
	}
	
	public ConciliacaoTransacaoEntity getConciliacaoTransacao() {
		return conciliacaoTransacao;
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
	
	public PlanoContaEntity getTituloPlanoContas() {
		return tituloPlanoContas;
	}
	
	public java.time.LocalDate getDataConciliacao() {
		return dataConciliacao;
	}
	
	public SituacaoConciliacaoTrn getSituacaoConciliacaoTrn() {
		return situacaoConciliacaoTrn;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setConciliacaoTransacao(ConciliacaoTransacaoEntity conciliacaoTransacao) {
		this.conciliacaoTransacao = conciliacaoTransacao;
	}
	
	public void setTituloConciliadoId(java.util.UUID tituloConciliadoId) {
		this.tituloConciliadoId = tituloConciliadoId;
	}
	
	public void setTituloConciliadoDesc(String tituloConciliadoDesc) {
		this.tituloConciliadoDesc = tituloConciliadoDesc != null ? tituloConciliadoDesc.trim() : tituloConciliadoDesc; // Chamadas REST fazem trim.
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
	
	public void setTituloPlanoContas(PlanoContaEntity tituloPlanoContas) {
		this.tituloPlanoContas = tituloPlanoContas;
	}
	
	public void setDataConciliacao(java.time.LocalDate dataConciliacao) {
		this.dataConciliacao = dataConciliacao;
	}
	
	public void setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn situacaoConciliacaoTrn) {
		this.situacaoConciliacaoTrn = situacaoConciliacaoTrn;
	}
	
	public void assign(ConciliacaoTransacaoTituloEntity source) {
		if (source != null) {
			this.setId(source.getId());
			this.setConciliacaoTransacao(source.getConciliacaoTransacao());
			this.setTituloConciliadoId(source.getTituloConciliadoId());
			this.setTituloConciliadoDesc(source.getTituloConciliadoDesc());
			this.setTituloConciliadoValor(source.getTituloConciliadoValor());
			this.setTituloConciliadoDataVen(source.getTituloConciliadoDataVen());
			this.setTituloConciliadoDataPag(source.getTituloConciliadoDataPag());
			this.setTituloPlanoContas(source.getTituloPlanoContas());
			this.setDataConciliacao(source.getDataConciliacao());
			this.setSituacaoConciliacaoTrn(source.getSituacaoConciliacaoTrn());
		}
	}
	
	public ConciliacaoTransacaoTituloEntity clone() {
		return clone(new java.util.HashMap<>());
	}
	
	public ConciliacaoTransacaoTituloEntity clone(java.util.Map<Object, Object> visited) {
		if (visited.containsKey(this)) {
			return (ConciliacaoTransacaoTituloEntity) visited.get(this);
		}
				
		ConciliacaoTransacaoTituloEntity theClone = new ConciliacaoTransacaoTituloEntity();
		visited.put(this, theClone);
		
		theClone.setId(this.getId());
		theClone.setConciliacaoTransacao(this.getConciliacaoTransacao() != null ? this.getConciliacaoTransacao().clone(visited) : null);
		theClone.setTituloConciliadoId(this.getTituloConciliadoId());
		theClone.setTituloConciliadoDesc(this.getTituloConciliadoDesc());
		theClone.setTituloConciliadoValor(this.getTituloConciliadoValor());
		theClone.setTituloConciliadoDataVen(this.getTituloConciliadoDataVen());
		theClone.setTituloConciliadoDataPag(this.getTituloConciliadoDataPag());
		theClone.setTituloPlanoContas(this.getTituloPlanoContas() != null ? this.getTituloPlanoContas().clone(visited) : null);
		theClone.setDataConciliacao(this.getDataConciliacao());
		theClone.setSituacaoConciliacaoTrn(this.getSituacaoConciliacaoTrn());
		
		return theClone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConciliacaoTransacaoTituloEntity other = (ConciliacaoTransacaoTituloEntity) obj;
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
