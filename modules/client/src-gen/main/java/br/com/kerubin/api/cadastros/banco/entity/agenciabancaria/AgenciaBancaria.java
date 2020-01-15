/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.agenciabancaria;

import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.entity.banco.BancoLookupResult;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AgenciaBancaria {

	private java.util.UUID id;
	
	@NotNull(message="\"Banco\" é obrigatório.")
	private BancoLookupResult banco;
	
	@NotBlank(message="\"Número da agência\" é obrigatório.")
	@Size(max = 50, message = "\"Número da agência\" pode ter no máximo 50 caracteres.")
	private String numeroAgencia;
	
	@NotBlank(message="\"Dígito\" é obrigatório.")
	@Size(max = 10, message = "\"Dígito\" pode ter no máximo 10 caracteres.")
	private String digitoAgencia;
	
	@Size(max = 255, message = "\"Endereço/localização da agência\" pode ter no máximo 255 caracteres.")
	private String endereco;
	
	@Size(max = 255, message = "\"Nome do gerente\" pode ter no máximo 255 caracteres.")
	private String nomeGerente;
	
	@Size(max = 50, message = "\"Telefone da agência\" pode ter no máximo 50 caracteres.")
	private String fone;
	
	@Size(max = 255, message = "\"Criado por\" pode ter no máximo 255 caracteres.")
	private String createdBy;
	
	private java.time.LocalDateTime createdDate;
	
	@Size(max = 255, message = "\"Alterado por\" pode ter no máximo 255 caracteres.")
	private String lastModifiedBy;
	
	private java.time.LocalDateTime lastModifiedDate;
	
	
	public AgenciaBancaria() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	
	public java.util.UUID getId() {
		return id;
	}
	
	public BancoLookupResult getBanco() {
		return banco;
	}
	
	public String getNumeroAgencia() {
		return numeroAgencia;
	}
	
	public String getDigitoAgencia() {
		return digitoAgencia;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public String getNomeGerente() {
		return nomeGerente;
	}
	
	public String getFone() {
		return fone;
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
	
	public void setBanco(BancoLookupResult banco) {
		this.banco = banco;
	}
	
	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}
	
	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}
	
	public void setFone(String fone) {
		this.fone = fone;
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
		AgenciaBancaria other = (AgenciaBancaria) obj;
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
