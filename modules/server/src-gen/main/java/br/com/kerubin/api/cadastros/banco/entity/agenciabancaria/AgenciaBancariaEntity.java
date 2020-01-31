/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.agenciabancaria;

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

@Entity
@Table(name = "agencia_bancaria")
public class AgenciaBancariaEntity extends AuditingEntity {

	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Id
	@Column(name="id")
	private java.util.UUID id;
	
	@NotNull(message="\"Banco\" é obrigatório.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banco")
	private BancoEntity banco;
	
	@NotBlank(message="\"Número da agência\" é obrigatório.")
	@Size(max = 50, message = "\"Número da agência\" pode ter no máximo 50 caracteres.")
	@Column(name="numero_agencia")
	private String numeroAgencia;
	
	@NotBlank(message="\"Dígito\" é obrigatório.")
	@Size(max = 10, message = "\"Dígito\" pode ter no máximo 10 caracteres.")
	@Column(name="digito_agencia")
	private String digitoAgencia;
	
	@Size(max = 255, message = "\"Endereço/localização da agência\" pode ter no máximo 255 caracteres.")
	@Column(name="endereco")
	private String endereco;
	
	@Size(max = 255, message = "\"Nome do gerente\" pode ter no máximo 255 caracteres.")
	@Column(name="nome_gerente")
	private String nomeGerente;
	
	@Size(max = 50, message = "\"Telefone da agência\" pode ter no máximo 50 caracteres.")
	@Column(name="fone")
	private String fone;
	
	public java.util.UUID getId() {
		return id;
	}
	
	public BancoEntity getBanco() {
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
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setBanco(BancoEntity banco) {
		this.banco = banco;
	}
	
	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia != null ? numeroAgencia.trim() : numeroAgencia; // Chamadas REST fazem trim.
	}
	
	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia != null ? digitoAgencia.trim() : digitoAgencia; // Chamadas REST fazem trim.
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco != null ? endereco.trim() : endereco; // Chamadas REST fazem trim.
	}
	
	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente != null ? nomeGerente.trim() : nomeGerente; // Chamadas REST fazem trim.
	}
	
	public void setFone(String fone) {
		this.fone = fone != null ? fone.trim() : fone; // Chamadas REST fazem trim.
	}
	
	public void assign(AgenciaBancariaEntity source) {
		if (source != null) {
			this.setId(source.getId());
			this.setBanco(source.getBanco());
			this.setNumeroAgencia(source.getNumeroAgencia());
			this.setDigitoAgencia(source.getDigitoAgencia());
			this.setEndereco(source.getEndereco());
			this.setNomeGerente(source.getNomeGerente());
			this.setFone(source.getFone());
			this.setCreatedBy(source.getCreatedBy());
			this.setCreatedDate(source.getCreatedDate());
			this.setLastModifiedBy(source.getLastModifiedBy());
			this.setLastModifiedDate(source.getLastModifiedDate());
		}
	}
	
	public AgenciaBancariaEntity clone() {
		return clone(new java.util.HashMap<>());
	}
	
	public AgenciaBancariaEntity clone(java.util.Map<Object, Object> visited) {
		if (visited.containsKey(this)) {
			return (AgenciaBancariaEntity) visited.get(this);
		}
				
		AgenciaBancariaEntity theClone = new AgenciaBancariaEntity();
		visited.put(this, theClone);
		
		theClone.setId(this.getId());
		theClone.setBanco(this.getBanco() != null ? this.getBanco().clone(visited) : null);
		theClone.setNumeroAgencia(this.getNumeroAgencia());
		theClone.setDigitoAgencia(this.getDigitoAgencia());
		theClone.setEndereco(this.getEndereco());
		theClone.setNomeGerente(this.getNomeGerente());
		theClone.setFone(this.getFone());
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
		AgenciaBancariaEntity other = (AgenciaBancariaEntity) obj;
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