/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;


import java.util.HashMap;
import java.util.Map;


public class ContaBancariaListFilter {

	private java.util.List<String> numeroConta;
	
	// Map field for developer customizing parameters.
	private Map<Object, Object> customParams = new HashMap<>();
	
	public java.util.List<String> getNumeroConta() {
		return numeroConta;
	}
	
	public void setNumeroConta(java.util.List<String> numeroConta) {
		this.numeroConta = numeroConta;
	}
	
	public Map<Object, Object> getCustomParams() {
		return customParams;
	}
	
	public void setCustomParams(Map<Object, Object> customParams) {
		this.customParams = customParams;
	}
	
}
