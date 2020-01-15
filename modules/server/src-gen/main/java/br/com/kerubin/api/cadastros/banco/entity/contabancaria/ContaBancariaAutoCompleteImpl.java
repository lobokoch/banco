/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContaBancariaAutoCompleteImpl implements ContaBancariaAutoComplete {

	private java.util.UUID id;
	
	private String nomeTitular;
	
	private String numeroConta;
	
	private String digito;
	
	public ContaBancariaAutoCompleteImpl() {
		// Contructor for reflexion, injection, Jackson, QueryDSL, etc proposal.
	}

}
