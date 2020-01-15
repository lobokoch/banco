/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.cartaocredito;

import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.entity.banco.BancoLookupResult;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoLookupResult;

public class CartaoCredito {

	private java.util.UUID id;
	
	@NotNull(message="\"Banco\" é obrigatório.")
	private BancoLookupResult banco;
	
	@NotBlank(message="\"Nome do títular do cartão\" é obrigatório.")
	@Size(max = 255, message = "\"Nome do títular do cartão\" pode ter no máximo 255 caracteres.")
	private String nomeTitular;
	
	@NotBlank(message="\"Número do cartão\" é obrigatório.")
	@Size(max = 50, message = "\"Número do cartão\" pode ter no máximo 50 caracteres.")
	private String numeroCartao;
	
	private java.time.LocalDate validade;
	
	@Size(max = 10, message = "\"Código de segurança\" pode ter no máximo 10 caracteres.")
	private String codigoSeguranca;
	
	private java.math.BigDecimal valorLimite;
	
	@NotNull(message="\"Bandeira do cartão\" é obrigatório.")
	private BandeiraCartaoLookupResult bandeiraCartao;
	
	@NotNull(message="\"Cartão ativo\" é obrigatório.")
	private Boolean ativo = true;
	
	@Size(max = 255, message = "\"Criado por\" pode ter no máximo 255 caracteres.")
	private String createdBy;
	
	private java.time.LocalDateTime createdDate;
	
	@Size(max = 255, message = "\"Alterado por\" pode ter no máximo 255 caracteres.")
	private String lastModifiedBy;
	
	private java.time.LocalDateTime lastModifiedDate;
	
	
	public CartaoCredito() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	
	public java.util.UUID getId() {
		return id;
	}
	
	public BancoLookupResult getBanco() {
		return banco;
	}
	
	public String getNomeTitular() {
		return nomeTitular;
	}
	
	public String getNumeroCartao() {
		return numeroCartao;
	}
	
	public java.time.LocalDate getValidade() {
		return validade;
	}
	
	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}
	
	public java.math.BigDecimal getValorLimite() {
		return valorLimite;
	}
	
	public BandeiraCartaoLookupResult getBandeiraCartao() {
		return bandeiraCartao;
	}
	
	public Boolean getAtivo() {
		return ativo;
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
	
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
	public void setValidade(java.time.LocalDate validade) {
		this.validade = validade;
	}
	
	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
	
	public void setValorLimite(java.math.BigDecimal valorLimite) {
		this.valorLimite = valorLimite;
	}
	
	public void setBandeiraCartao(BandeiraCartaoLookupResult bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
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
		CartaoCredito other = (CartaoCredito) obj;
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
