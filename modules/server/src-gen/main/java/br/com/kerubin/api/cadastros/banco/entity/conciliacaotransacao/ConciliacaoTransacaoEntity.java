/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import br.com.kerubin.api.database.entity.AuditingEntity;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import br.com.kerubin.api.cadastros.banco.TipoTransacao;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloEntity;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "conciliacao_transacao")
public class ConciliacaoTransacaoEntity extends AuditingEntity {

	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Id
	@Column(name="id")
	private java.util.UUID id;
	
	@NotBlank(message="\"Identificador da transação\" é obrigatório.")
	@Size(max = 255, message = "\"Identificador da transação\" pode ter no máximo 255 caracteres.")
	@Column(name="trn_id")
	private String trnId;
	
	@NotNull(message="\"Data\" é obrigatório.")
	@Column(name="trn_data")
	private java.time.LocalDate trnData;
	
	@NotBlank(message="\"Histórico\" é obrigatório.")
	@Size(max = 255, message = "\"Histórico\" pode ter no máximo 255 caracteres.")
	@Column(name="trn_historico")
	private String trnHistorico;
	
	@NotBlank(message="\"Documento\" é obrigatório.")
	@Size(max = 255, message = "\"Documento\" pode ter no máximo 255 caracteres.")
	@Column(name="trn_documento")
	private String trnDocumento;
	
	@NotNull(message="\"trnTipo\" é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name="trn_tipo")
	private TipoTransacao trnTipo;
	
	@NotNull(message="\"Valor\" é obrigatório.")
	@Column(name="trn_valor")
	private java.math.BigDecimal trnValor;
	
	@NotNull(message="\"Conciliação bancária\" é obrigatório.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conciliacao_bancaria")
	private ConciliacaoBancariaEntity conciliacaoBancaria;
	
	@NotNull(message="\"situacaoConciliacaoTrn\" é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name="situacao_conciliacao_trn")
	private SituacaoConciliacaoTrn situacaoConciliacaoTrn;
	
	@Column(name="titulo_conciliado_id")
	private java.util.UUID tituloConciliadoId;
	
	@Size(max = 255, message = "\"Desc. tít. conc.\" pode ter no máximo 255 caracteres.")
	@Column(name="titulo_conciliado_desc")
	private String tituloConciliadoDesc;
	
	@Column(name="titulo_conciliado_valor")
	private java.math.BigDecimal tituloConciliadoValor;
	
	@Column(name="titulo_conciliado_data_ven")
	private java.time.LocalDate tituloConciliadoDataVen;
	
	@Column(name="titulo_conciliado_data_pag")
	private java.time.LocalDate tituloConciliadoDataPag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "titulo_plano_contas")
	private PlanoContaEntity tituloPlanoContas;
	
	@Column(name="data_conciliacao")
	private java.time.LocalDate dataConciliacao;
	
	@OneToMany(mappedBy = "conciliacaoTransacao", fetch = FetchType.EAGER, cascade = CascadeType.ALL , orphanRemoval = true)
	private List<ConciliacaoTransacaoTituloEntity> conciliacaoTransacaoTitulos = new ArrayList<>();
	
	@Column(name="conciliado_com_erro")
	private Boolean conciliadoComErro = false;
	
	@Size(max = 255, message = "\"Mensagem\" pode ter no máximo 255 caracteres.")
	@Column(name="conciliado_msg")
	private String conciliadoMsg;
	
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
	
	public ConciliacaoBancariaEntity getConciliacaoBancaria() {
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
	
	public PlanoContaEntity getTituloPlanoContas() {
		return tituloPlanoContas;
	}
	
	public java.time.LocalDate getDataConciliacao() {
		return dataConciliacao;
	}
	
	public java.util.List<ConciliacaoTransacaoTituloEntity> getConciliacaoTransacaoTitulos() {
		return conciliacaoTransacaoTitulos;
	}
	
	public Boolean getConciliadoComErro() {
		return conciliadoComErro;
	}
	
	public String getConciliadoMsg() {
		return conciliadoMsg;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setTrnId(String trnId) {
		this.trnId = trnId != null ? trnId.trim() : trnId; // Chamadas REST fazem trim.
	}
	
	public void setTrnData(java.time.LocalDate trnData) {
		this.trnData = trnData;
	}
	
	public void setTrnHistorico(String trnHistorico) {
		this.trnHistorico = trnHistorico != null ? trnHistorico.trim() : trnHistorico; // Chamadas REST fazem trim.
	}
	
	public void setTrnDocumento(String trnDocumento) {
		this.trnDocumento = trnDocumento != null ? trnDocumento.trim() : trnDocumento; // Chamadas REST fazem trim.
	}
	
	public void setTrnTipo(TipoTransacao trnTipo) {
		this.trnTipo = trnTipo;
	}
	
	public void setTrnValor(java.math.BigDecimal trnValor) {
		this.trnValor = trnValor;
	}
	
	public void setConciliacaoBancaria(ConciliacaoBancariaEntity conciliacaoBancaria) {
		this.conciliacaoBancaria = conciliacaoBancaria;
	}
	
	public void setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn situacaoConciliacaoTrn) {
		this.situacaoConciliacaoTrn = situacaoConciliacaoTrn;
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
	
	public void setConciliacaoTransacaoTitulos(java.util.List<ConciliacaoTransacaoTituloEntity> conciliacaoTransacaoTitulos) {
		// First remove existing items.
		if (this.conciliacaoTransacaoTitulos != null) {
			this.conciliacaoTransacaoTitulos.forEach(this::removeConciliacaoTransacaoTitulo);
		}
		
		if (conciliacaoTransacaoTitulos != null) {
			conciliacaoTransacaoTitulos.forEach(this::addConciliacaoTransacaoTitulo);
		}
	}
	
	public void addConciliacaoTransacaoTitulo(ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTitulo) {
		this.conciliacaoTransacaoTitulos.add(conciliacaoTransacaoTitulo);
		conciliacaoTransacaoTitulo.setConciliacaoTransacao(this);
	}
	
	public void removeConciliacaoTransacaoTitulo(ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTitulo) {
		this.conciliacaoTransacaoTitulos.remove(conciliacaoTransacaoTitulo);
		conciliacaoTransacaoTitulo.setConciliacaoTransacao(null);
	}
	
	public void setConciliadoComErro(Boolean conciliadoComErro) {
		this.conciliadoComErro = conciliadoComErro;
	}
	
	public void setConciliadoMsg(String conciliadoMsg) {
		this.conciliadoMsg = conciliadoMsg != null ? conciliadoMsg.trim() : conciliadoMsg; // Chamadas REST fazem trim.
	}
	
	public void assign(ConciliacaoTransacaoEntity source) {
		if (source != null) {
			this.setId(source.getId());
			this.setTrnId(source.getTrnId());
			this.setTrnData(source.getTrnData());
			this.setTrnHistorico(source.getTrnHistorico());
			this.setTrnDocumento(source.getTrnDocumento());
			this.setTrnTipo(source.getTrnTipo());
			this.setTrnValor(source.getTrnValor());
			this.setConciliacaoBancaria(source.getConciliacaoBancaria());
			this.setSituacaoConciliacaoTrn(source.getSituacaoConciliacaoTrn());
			this.setTituloConciliadoId(source.getTituloConciliadoId());
			this.setTituloConciliadoDesc(source.getTituloConciliadoDesc());
			this.setTituloConciliadoValor(source.getTituloConciliadoValor());
			this.setTituloConciliadoDataVen(source.getTituloConciliadoDataVen());
			this.setTituloConciliadoDataPag(source.getTituloConciliadoDataPag());
			this.setTituloPlanoContas(source.getTituloPlanoContas());
			this.setDataConciliacao(source.getDataConciliacao());
			this.setConciliacaoTransacaoTitulos(source.getConciliacaoTransacaoTitulos());
			this.setConciliadoComErro(source.getConciliadoComErro());
			this.setConciliadoMsg(source.getConciliadoMsg());
			this.setCreatedBy(source.getCreatedBy());
			this.setCreatedDate(source.getCreatedDate());
			this.setLastModifiedBy(source.getLastModifiedBy());
			this.setLastModifiedDate(source.getLastModifiedDate());
		}
	}
	
	public ConciliacaoTransacaoEntity clone() {
		return clone(new java.util.HashMap<>());
	}
	
	public ConciliacaoTransacaoEntity clone(java.util.Map<Object, Object> visited) {
		if (visited.containsKey(this)) {
			return (ConciliacaoTransacaoEntity) visited.get(this);
		}
				
		ConciliacaoTransacaoEntity theClone = new ConciliacaoTransacaoEntity();
		visited.put(this, theClone);
		
		theClone.setId(this.getId());
		theClone.setTrnId(this.getTrnId());
		theClone.setTrnData(this.getTrnData());
		theClone.setTrnHistorico(this.getTrnHistorico());
		theClone.setTrnDocumento(this.getTrnDocumento());
		theClone.setTrnTipo(this.getTrnTipo());
		theClone.setTrnValor(this.getTrnValor());
		theClone.setConciliacaoBancaria(this.getConciliacaoBancaria() != null ? this.getConciliacaoBancaria().clone(visited) : null);
		theClone.setSituacaoConciliacaoTrn(this.getSituacaoConciliacaoTrn());
		theClone.setTituloConciliadoId(this.getTituloConciliadoId());
		theClone.setTituloConciliadoDesc(this.getTituloConciliadoDesc());
		theClone.setTituloConciliadoValor(this.getTituloConciliadoValor());
		theClone.setTituloConciliadoDataVen(this.getTituloConciliadoDataVen());
		theClone.setTituloConciliadoDataPag(this.getTituloConciliadoDataPag());
		theClone.setTituloPlanoContas(this.getTituloPlanoContas() != null ? this.getTituloPlanoContas().clone(visited) : null);
		theClone.setDataConciliacao(this.getDataConciliacao());
		theClone.setConciliacaoTransacaoTitulos(this.getConciliacaoTransacaoTitulos() != null ? this.getConciliacaoTransacaoTitulos().stream().map(it -> it.clone(visited)).collect(java.util.stream.Collectors.toList()) : null);
		theClone.setConciliadoComErro(this.getConciliadoComErro());
		theClone.setConciliadoMsg(this.getConciliadoMsg());
		theClone.setCreatedBy(this.getCreatedBy());
		theClone.setCreatedDate(this.getCreatedDate());
		theClone.setLastModifiedBy(this.getLastModifiedBy());
		theClone.setLastModifiedDate(this.getLastModifiedDate());
		
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
		ConciliacaoTransacaoEntity other = (ConciliacaoTransacaoEntity) obj;
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
