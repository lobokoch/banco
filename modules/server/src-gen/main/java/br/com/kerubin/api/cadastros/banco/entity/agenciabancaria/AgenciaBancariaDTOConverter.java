/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/


package br.com.kerubin.api.cadastros.banco.entity.agenciabancaria;

import javax.inject.Inject;
import org.springframework.stereotype.Component;
import br.com.kerubin.api.servicecore.mapper.ObjectMapper;

@Component
public class AgenciaBancariaDTOConverter {

	@Inject
	private ObjectMapper mapper;

	public AgenciaBancaria convertEntityToDto(AgenciaBancariaEntity entity) {
		AgenciaBancaria dto = null;
		if (entity != null) {
			dto = mapper.map(entity, AgenciaBancaria.class, true); // Do not permit passwords fields go outside.
		}
		return dto;
	}


	public AgenciaBancariaEntity convertDtoToEntity(AgenciaBancaria dto) {
		AgenciaBancariaEntity entity = null;
		if (dto != null) {
			entity = mapper.map(dto, AgenciaBancariaEntity.class);
		}
		return entity;
	}


}