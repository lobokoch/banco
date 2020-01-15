/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;

import br.com.kerubin.api.messaging.core.DomainEvent;
import br.com.kerubin.api.cadastros.banco.TipoContaBancaria;

public class ContaBancariaEvent implements DomainEvent {
	
	public static final String CONTA_BANCARIA_CREATED = "contaBancariaCreated";
	public static final String CONTA_BANCARIA_UPDATED = "contaBancariaUpdated";
	public static final String CONTA_BANCARIA_DELETED = "contaBancariaDeleted";
	private java.util.UUID id;
	
	private String nomeTitular;
	
	private java.util.UUID agencia;
	
	private TipoContaBancaria tipoContaBancaria;
	
	private String numeroConta;
	
	private String digito;
	
	private java.time.LocalDate dataValidade;
	
	private java.util.UUID bandeiraCartao;
	
	private Boolean ativo;
	
	public ContaBancariaEvent() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	public ContaBancariaEvent(java.util.UUID id, String nomeTitular, java.util.UUID agencia, TipoContaBancaria tipoContaBancaria, String numeroConta, String digito, java.time.LocalDate dataValidade, java.util.UUID bandeiraCartao, Boolean ativo) {
		this.id = id;
		this.nomeTitular = nomeTitular;
		this.agencia = agencia;
		this.tipoContaBancaria = tipoContaBancaria;
		this.numeroConta = numeroConta;
		this.digito = digito;
		this.dataValidade = dataValidade;
		this.bandeiraCartao = bandeiraCartao;
		this.ativo = ativo;
	}
	
	public java.util.UUID getId() {
		return id;
	}
	
	public String getNomeTitular() {
		return nomeTitular;
	}
	
	public java.util.UUID getAgencia() {
		return agencia;
	}
	
	public TipoContaBancaria getTipoContaBancaria() {
		return tipoContaBancaria;
	}
	
	public String getNumeroConta() {
		return numeroConta;
	}
	
	public String getDigito() {
		return digito;
	}
	
	public java.time.LocalDate getDataValidade() {
		return dataValidade;
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
	
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	
	public void setAgencia(java.util.UUID agencia) {
		this.agencia = agencia;
	}
	
	public void setTipoContaBancaria(TipoContaBancaria tipoContaBancaria) {
		this.tipoContaBancaria = tipoContaBancaria;
	}
	
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	
	public void setDigito(String digito) {
		this.digito = digito;
	}
	
	public void setDataValidade(java.time.LocalDate dataValidade) {
		this.dataValidade = dataValidade;
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
		ContaBancariaEvent other = (ContaBancariaEvent) obj;
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
