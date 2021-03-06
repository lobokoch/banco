/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.bandeiracartao;

import br.com.kerubin.api.messaging.core.DomainEvent;

public class BandeiraCartaoEvent implements DomainEvent {
	
	public static final String BANDEIRA_CARTAO_CREATED = "bandeiraCartaoCreated";
	public static final String BANDEIRA_CARTAO_UPDATED = "bandeiraCartaoUpdated";
	public static final String BANDEIRA_CARTAO_DELETED = "bandeiraCartaoDeleted";
	private java.util.UUID id;
	
	private String nomeBandeira;
	
	public BandeiraCartaoEvent() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}
	
	public BandeiraCartaoEvent(java.util.UUID id, String nomeBandeira) {
		this.id = id;
		this.nomeBandeira = nomeBandeira;
	}
	
	public java.util.UUID getId() {
		return id;
	}
	
	public String getNomeBandeira() {
		return nomeBandeira;
	}
	
	public void setId(java.util.UUID id) {
		this.id = id;
	}
	
	public void setNomeBandeira(String nomeBandeira) {
		this.nomeBandeira = nomeBandeira;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BandeiraCartaoEvent other = (BandeiraCartaoEvent) obj;
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
