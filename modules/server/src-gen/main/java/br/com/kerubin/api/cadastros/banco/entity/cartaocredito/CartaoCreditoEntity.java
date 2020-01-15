/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.cartaocredito;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import br.com.kerubin.api.database.entity.AuditingEntity;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.NotNull;
import br.com.kerubin.api.cadastros.banco.entity.banco.BancoEntity;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.com.kerubin.api.cadastros.banco.entity.bandeiracartao.BandeiraCartaoEntity;

@Entity
@Table(name = "cartao_credito")
public class CartaoCreditoEntity extends AuditingEntity {

	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Id
	@Column(name="id")
	private java.util.UUID id;
	
	@NotNull(message="\"Banco\" é obrigatório.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banco")
	private BancoEntity banco;
	
	@NotBlank(message="\"Nome do títular do cartão\" é obrigatório.")
	@Size(max = 255, message = "\"Nome do títular do cartão\" pode ter no máximo 255 caracteres.")
	@Column(name="nome_titular")
	private String nomeTitular;
	
	@NotBlank(message="\"Número do cartão\" é obrigatório.")
	@Size(max = 50, message = "\"Número do cartão\" pode ter no máximo 50 caracteres.")
	@Column(name="numero_cartao")
	private String numeroCartao;
	
	@Column(name="validade")
	private java.time.LocalDate validade;
	
	@Size(max = 10, message = "\"Código de segurança\" pode ter no máximo 10 caracteres.")
	@Column(name="codigo_seguranca")
	private String codigoSeguranca;
	
	@Column(name="valor_limite")
	private java.math.BigDecimal valorLimite;
	
	@NotNull(message="\"Bandeira do cartão\" é obrigatório.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bandeira_cartao")
	private BandeiraCartaoEntity bandeiraCartao;
	
	@NotNull(message="\"Cartão ativo\" é obrigatório.")
	@Column(name="ativo")
	private Boolean ativo = true;
	
	public java.util.UUID getId() {
		return id;
	}
	
	public BancoEntity getBanco() {
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
	
	public BandeiraCartaoEntity getBandeiraCartao() {
		return bandeiraCartao;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setBanco(BancoEntity banco) {
		this.banco = banco;
	}
	
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular != null ? nomeTitular.trim() : nomeTitular; // Chamadas REST fazem trim.
	}
	
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao != null ? numeroCartao.trim() : numeroCartao; // Chamadas REST fazem trim.
	}
	
	public void setValidade(java.time.LocalDate validade) {
		this.validade = validade;
	}
	
	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca != null ? codigoSeguranca.trim() : codigoSeguranca; // Chamadas REST fazem trim.
	}
	
	public void setValorLimite(java.math.BigDecimal valorLimite) {
		this.valorLimite = valorLimite;
	}
	
	public void setBandeiraCartao(BandeiraCartaoEntity bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public void assign(CartaoCreditoEntity source) {
		if (source != null) {
			this.setId(source.getId());
			this.setBanco(source.getBanco());
			this.setNomeTitular(source.getNomeTitular());
			this.setNumeroCartao(source.getNumeroCartao());
			this.setValidade(source.getValidade());
			this.setCodigoSeguranca(source.getCodigoSeguranca());
			this.setValorLimite(source.getValorLimite());
			this.setBandeiraCartao(source.getBandeiraCartao());
			this.setAtivo(source.getAtivo());
			this.setCreatedBy(source.getCreatedBy());
			this.setCreatedDate(source.getCreatedDate());
			this.setLastModifiedBy(source.getLastModifiedBy());
			this.setLastModifiedDate(source.getLastModifiedDate());
		}
	}
	
	public CartaoCreditoEntity clone() {
		return clone(new java.util.HashMap<>());
	}
	
	public CartaoCreditoEntity clone(java.util.Map<Object, Object> visited) {
		if (visited.containsKey(this)) {
			return (CartaoCreditoEntity) visited.get(this);
		}
				
		CartaoCreditoEntity theClone = new CartaoCreditoEntity();
		visited.put(this, theClone);
		
		theClone.setId(this.getId());
		theClone.setBanco(this.getBanco() != null ? this.getBanco().clone(visited) : null);
		theClone.setNomeTitular(this.getNomeTitular());
		theClone.setNumeroCartao(this.getNumeroCartao());
		theClone.setValidade(this.getValidade());
		theClone.setCodigoSeguranca(this.getCodigoSeguranca());
		theClone.setValorLimite(this.getValorLimite());
		theClone.setBandeiraCartao(this.getBandeiraCartao() != null ? this.getBandeiraCartao().clone(visited) : null);
		theClone.setAtivo(this.getAtivo());
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
		CartaoCreditoEntity other = (CartaoCreditoEntity) obj;
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
