/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.bandeiracartao;


import java.util.HashMap;
import java.util.Map;


public class BandeiraCartaoListFilter {

	private java.util.List<String> nomeBandeira;
	
	// Map field for developer customizing parameters.
	private Map<Object, Object> customParams = new HashMap<>();
	
	public java.util.List<String> getNomeBandeira() {
		return nomeBandeira;
	}
	
	public void setNomeBandeira(java.util.List<String> nomeBandeira) {
		this.nomeBandeira = nomeBandeira;
	}
	
	public Map<Object, Object> getCustomParams() {
		return customParams;
	}
	
	public void setCustomParams(Map<Object, Object> customParams) {
		this.customParams = customParams;
	}
	
}
