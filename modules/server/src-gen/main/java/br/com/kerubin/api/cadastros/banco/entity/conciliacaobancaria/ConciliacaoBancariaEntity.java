/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria;

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
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;

@Entity
@Table(name = "conciliacao_bancaria")
public class ConciliacaoBancariaEntity extends AuditingEntity {

	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Id
	@Column(name="id")
	private java.util.UUID id;
	
	@NotBlank(message="\"Banco\" é obrigatório.")
	@Size(max = 255, message = "\"Banco\" pode ter no máximo 255 caracteres.")
	@Column(name="banco_id")
	private String bancoId;
	
	@NotBlank(message="\"Agência\" é obrigatório.")
	@Size(max = 255, message = "\"Agência\" pode ter no máximo 255 caracteres.")
	@Column(name="agencia_id")
	private String agenciaId;
	
	@NotBlank(message="\"Conta\" é obrigatório.")
	@Size(max = 255, message = "\"Conta\" pode ter no máximo 255 caracteres.")
	@Column(name="conta_id")
	private String contaId;
	
	@NotNull(message="\"Data inicial\" é obrigatório.")
	@Column(name="data_ini")
	private java.time.LocalDate dataIni;
	
	@NotNull(message="\"Data final\" é obrigatório.")
	@Column(name="data_fim")
	private java.time.LocalDate dataFim;
	
	@NotNull(message="\"situacaoConciliacao\" é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name="situacao_conciliacao")
	private SituacaoConciliacao situacaoConciliacao;
	
	public java.util.UUID getId() {
		return id;
	}
	
	public String getBancoId() {
		return bancoId;
	}
	
	public String getAgenciaId() {
		return agenciaId;
	}
	
	public String getContaId() {
		return contaId;
	}
	
	public java.time.LocalDate getDataIni() {
		return dataIni;
	}
	
	public java.time.LocalDate getDataFim() {
		return dataFim;
	}
	
	public SituacaoConciliacao getSituacaoConciliacao() {
		return situacaoConciliacao;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setBancoId(String bancoId) {
		this.bancoId = bancoId != null ? bancoId.trim() : bancoId; // Chamadas REST fazem trim.
	}
	
	public void setAgenciaId(String agenciaId) {
		this.agenciaId = agenciaId != null ? agenciaId.trim() : agenciaId; // Chamadas REST fazem trim.
	}
	
	public void setContaId(String contaId) {
		this.contaId = contaId != null ? contaId.trim() : contaId; // Chamadas REST fazem trim.
	}
	
	public void setDataIni(java.time.LocalDate dataIni) {
		this.dataIni = dataIni;
	}
	
	public void setDataFim(java.time.LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	
	public void setSituacaoConciliacao(SituacaoConciliacao situacaoConciliacao) {
		this.situacaoConciliacao = situacaoConciliacao;
	}
	
	public void assign(ConciliacaoBancariaEntity source) {
		if (source != null) {
			this.setId(source.getId());
			this.setBancoId(source.getBancoId());
			this.setAgenciaId(source.getAgenciaId());
			this.setContaId(source.getContaId());
			this.setDataIni(source.getDataIni());
			this.setDataFim(source.getDataFim());
			this.setSituacaoConciliacao(source.getSituacaoConciliacao());
			this.setCreatedBy(source.getCreatedBy());
			this.setCreatedDate(source.getCreatedDate());
			this.setLastModifiedBy(source.getLastModifiedBy());
			this.setLastModifiedDate(source.getLastModifiedDate());
		}
	}
	
	public ConciliacaoBancariaEntity clone() {
		return clone(new java.util.HashMap<>());
	}
	
	public ConciliacaoBancariaEntity clone(java.util.Map<Object, Object> visited) {
		if (visited.containsKey(this)) {
			return (ConciliacaoBancariaEntity) visited.get(this);
		}
				
		ConciliacaoBancariaEntity theClone = new ConciliacaoBancariaEntity();
		visited.put(this, theClone);
		
		theClone.setId(this.getId());
		theClone.setBancoId(this.getBancoId());
		theClone.setAgenciaId(this.getAgenciaId());
		theClone.setContaId(this.getContaId());
		theClone.setDataIni(this.getDataIni());
		theClone.setDataFim(this.getDataFim());
		theClone.setSituacaoConciliacao(this.getSituacaoConciliacao());
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
		ConciliacaoBancariaEntity other = (ConciliacaoBancariaEntity) obj;
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
