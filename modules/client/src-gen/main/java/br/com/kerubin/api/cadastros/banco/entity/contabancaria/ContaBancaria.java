/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.entity.agenciabancaria.AgenciaBancariaLookupResult;
import br.com.kerubin.api.cadastros.banco.TipoContaBancaria;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoLookupResult;

public class ContaBancaria {

	private java.util.UUID id;
	
	@NotBlank(message="\"Nome do títular da conta\" é obrigatório.")
	@Size(max = 255, message = "\"Nome do títular da conta\" pode ter no máximo 255 caracteres.")
	private String nomeTitular;
	
	@NotNull(message="\"Agência bancária\" é obrigatório.")
	private AgenciaBancariaLookupResult agencia;
	
	@NotNull(message="\"Tipo da conta\" é obrigatório.")
	private TipoContaBancaria tipoContaBancaria;
	
	@NotBlank(message="\"Número da conta\" é obrigatório.")
	@Size(max = 30, message = "\"Número da conta\" pode ter no máximo 30 caracteres.")
	private String numeroConta;
	
	@Size(max = 10, message = "\"Dígito\" pode ter no máximo 10 caracteres.")
	private String digito;
	
	private java.math.BigDecimal saldo;
	
	@Size(max = 50, message = "\"Número do cartão\" pode ter no máximo 50 caracteres.")
	private String numeroCartao;
	
	@Size(max = 10, message = "\"Código de segurança\" pode ter no máximo 10 caracteres.")
	private String codigoSeguranca;
	
	private java.time.LocalDate dataValidade;
	
	@NotNull(message="\"Bandeira do cartão\" é obrigatório.")
	private BandeiraCartaoLookupResult bandeiraCartao;
	
	@NotNull(message="\"Conta ativa\" é obrigatório.")
	private Boolean ativo = true;
	
	@Size(max = 255, message = "\"Criado por\" pode ter no máximo 255 caracteres.")
	private String createdBy;
	
	private java.time.LocalDateTime createdDate;
	
	@Size(max = 255, message = "\"Alterado por\" pode ter no máximo 255 caracteres.")
	private String lastModifiedBy;
	
	private java.time.LocalDateTime lastModifiedDate;
	
	
	public ContaBancaria() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	
	public java.util.UUID getId() {
		return id;
	}
	
	public String getNomeTitular() {
		return nomeTitular;
	}
	
	public AgenciaBancariaLookupResult getAgencia() {
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
	
	public java.math.BigDecimal getSaldo() {
		return saldo;
	}
	
	public String getNumeroCartao() {
		return numeroCartao;
	}
	
	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}
	
	public java.time.LocalDate getDataValidade() {
		return dataValidade;
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
	
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	
	public void setAgencia(AgenciaBancariaLookupResult agencia) {
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
	
	public void setSaldo(java.math.BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
	
	public void setDataValidade(java.time.LocalDate dataValidade) {
		this.dataValidade = dataValidade;
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
		ContaBancaria other = (ContaBancaria) obj;
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
