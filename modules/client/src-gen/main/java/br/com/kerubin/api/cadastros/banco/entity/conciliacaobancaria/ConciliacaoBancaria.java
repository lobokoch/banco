/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.SituacaoConciliacao;

public class ConciliacaoBancaria {

	private java.util.UUID id;
	
	@NotBlank(message="\"Banco\" é obrigatório.")
	@Size(max = 255, message = "\"Banco\" pode ter no máximo 255 caracteres.")
	private String bancoId;
	
	@NotBlank(message="\"Agência\" é obrigatório.")
	@Size(max = 255, message = "\"Agência\" pode ter no máximo 255 caracteres.")
	private String agenciaId;
	
	@NotBlank(message="\"Conta\" é obrigatório.")
	@Size(max = 255, message = "\"Conta\" pode ter no máximo 255 caracteres.")
	private String contaId;
	
	@NotNull(message="\"Data inicial\" é obrigatório.")
	private java.time.LocalDate dataIni;
	
	@NotNull(message="\"Data final\" é obrigatório.")
	private java.time.LocalDate dataFim;
	
	@NotNull(message="\"situacaoConciliacao\" é obrigatório.")
	private SituacaoConciliacao situacaoConciliacao;
	
	@Size(max = 255, message = "\"Criado por\" pode ter no máximo 255 caracteres.")
	private String createdBy;
	
	private java.time.LocalDateTime createdDate;
	
	@Size(max = 255, message = "\"Alterado por\" pode ter no máximo 255 caracteres.")
	private String lastModifiedBy;
	
	private java.time.LocalDateTime lastModifiedDate;
	
	
	public ConciliacaoBancaria() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	
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
	
	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}
	
	public void setAgenciaId(String agenciaId) {
		this.agenciaId = agenciaId;
	}
	
	public void setContaId(String contaId) {
		this.contaId = contaId;
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
		ConciliacaoBancaria other = (ConciliacaoBancaria) obj;
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