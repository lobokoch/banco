/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.agenciabancaria;

import br.com.kerubin.api.messaging.core.DomainEvent;

public class AgenciaBancariaEvent implements DomainEvent {
	
	public static final String AGENCIA_BANCARIA_CREATED = "agenciaBancariaCreated";
	public static final String AGENCIA_BANCARIA_UPDATED = "agenciaBancariaUpdated";
	public static final String AGENCIA_BANCARIA_DELETED = "agenciaBancariaDeleted";
	private java.util.UUID id;
	
	private java.util.UUID banco;
	
	private String numeroAgencia;
	
	private String digitoAgencia;
	
	public AgenciaBancariaEvent() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	public AgenciaBancariaEvent(java.util.UUID id, java.util.UUID banco, String numeroAgencia, String digitoAgencia) {
		this.id = id;
		this.banco = banco;
		this.numeroAgencia = numeroAgencia;
		this.digitoAgencia = digitoAgencia;
	}
	
	public java.util.UUID getId() {
		return id;
	}
	
	public java.util.UUID getBanco() {
		return banco;
	}
	
	public String getNumeroAgencia() {
		return numeroAgencia;
	}
	
	public String getDigitoAgencia() {
		return digitoAgencia;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setBanco(java.util.UUID banco) {
		this.banco = banco;
	}
	
	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}
	
	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgenciaBancariaEvent other = (AgenciaBancariaEvent) obj;
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
