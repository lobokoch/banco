/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/


package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import javax.inject.Inject;
import org.springframework.stereotype.Component;
import br.com.kerubin.api.servicecore.mapper.ObjectMapper;

@Component
public class ConciliacaoTransacaoDTOConverter {

	@Inject
	private ObjectMapper mapper;

	public ConciliacaoTransacao convertEntityToDto(ConciliacaoTransacaoEntity entity) {
		ConciliacaoTransacao dto = null;
		if (entity != null) {
			dto = mapper.map(entity, ConciliacaoTransacao.class, true); // Do not permit passwords fields go outside.
		}
		return dto;
	}


	public ConciliacaoTransacaoEntity convertDtoToEntity(ConciliacaoTransacao dto) {
		ConciliacaoTransacaoEntity entity = null;
		if (dto != null) {
			entity = mapper.map(dto, ConciliacaoTransacaoEntity.class);
		}
		return entity;
	}


}