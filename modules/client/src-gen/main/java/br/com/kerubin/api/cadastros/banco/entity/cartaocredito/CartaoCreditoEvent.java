/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.cartaocredito;

import br.com.kerubin.api.messaging.core.DomainEvent;

public class CartaoCreditoEvent implements DomainEvent {
	
	public static final String CARTAO_CREDITO_CREATED = "cartaoCreditoCreated";
	public static final String CARTAO_CREDITO_UPDATED = "cartaoCreditoUpdated";
	public static final String CARTAO_CREDITO_DELETED = "cartaoCreditoDeleted";
	private java.util.UUID id;
	
	private java.util.UUID banco;
	
	private String nomeTitular;
	
	private String numeroCartao;
	
	private java.time.LocalDate validade;
	
	private java.math.BigDecimal valorLimite;
	
	private java.util.UUID bandeiraCartao;
	
	private Boolean ativo;
	
	public CartaoCreditoEvent() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	public CartaoCreditoEvent(java.util.UUID id, java.util.UUID banco, String nomeTitular, String numeroCartao, java.time.LocalDate validade, java.math.BigDecimal valorLimite, java.util.UUID bandeiraCartao, Boolean ativo) {
		this.id = id;
		this.banco = banco;
		this.nomeTitular = nomeTitular;
		this.numeroCartao = numeroCartao;
		this.validade = validade;
		this.valorLimite = valorLimite;
		this.bandeiraCartao = bandeiraCartao;
		this.ativo = ativo;
	}
	
	public java.util.UUID getId() {
		return id;
	}
	
	public java.util.UUID getBanco() {
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
	
	public java.math.BigDecimal getValorLimite() {
		return valorLimite;
	}
	
	public java.util.UUID getBandeiraCartao() {
		return bandeiraCartao;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setBanco(java.util.UUID banco) {
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
	
	public void setValorLimite(java.math.BigDecimal valorLimite) {
		this.valorLimite = valorLimite;
	}
	
	public void setBandeiraCartao(java.util.UUID bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaoCreditoEvent other = (CartaoCreditoEvent) obj;
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
