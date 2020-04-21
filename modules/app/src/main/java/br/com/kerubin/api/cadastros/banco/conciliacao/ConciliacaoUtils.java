package br.com.kerubin.api.cadastros.banco.conciliacao;

import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT_ACCOUNT_TYPE;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_USER;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoTituloDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.PlanoContaDTO;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaLookupResult;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaEntity;
import br.com.kerubin.api.database.core.ServiceContextData;
import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

public class ConciliacaoUtils {
	
	public static HttpHeaders buildHttpHeaders(ServiceContextData serviceContextData) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_TENANT, serviceContextData.getTenant());
        headers.set(HEADER_USER, serviceContextData.getUsername());
        headers.set(HEADER_TENANT_ACCOUNT_TYPE, serviceContextData.getTenantAccountType());
        
        return headers;
	}
	
	public static ConciliacaoBancariaDTO toDTO(ConciliacaoBancariaEntity conciliacaoBancariaEntity, List<ConciliacaoTransacaoEntity> transacoes) {
		
		final ConciliacaoBancariaLookupResult conciliacaoBancaria = new ConciliacaoBancariaLookupResult();
		conciliacaoBancaria.setId(conciliacaoBancariaEntity.getId());
		conciliacaoBancaria.setBancoId(conciliacaoBancariaEntity.getBancoId());
		
		List<ConciliacaoTransacaoDTO> transacoesDTO = transacoes.stream().map(it -> {
			
			ConciliacaoTransacaoDTO dto = ConciliacaoTransacaoDTO.builder()
					.id(it.getId())
					.trnId(it.getTrnId())
					.trnData(it.getTrnData())
					.trnHistorico(it.getTrnHistorico())
					.trnDocumento(it.getTrnDocumento())
					.trnTipo(it.getTrnTipo())
					.trnValor(it.getTrnValor())
					.conciliadoComErro(it.getConciliadoComErro())
					.conciliadoMsg(it.getConciliadoMsg())
					
					////
                    .tituloConciliadoId(it.getTituloConciliadoId())
                    .tituloConciliadoDesc(it.getTituloConciliadoDesc())
                    .tituloConciliadoMultiple(it.getTituloConciliadoMultiple())
                    .tituloConciliadoValor(it.getTituloConciliadoValor())
                    .tituloConciliadoDataVen(it.getTituloConciliadoDataVen())
                    .tituloConciliadoDataPag(it.getTituloConciliadoDataPag())
                    .tituloPlanoContas(toDTO(it.getTituloPlanoContas()))
                    .dataConciliacao(it.getDataConciliacao())
                    .situacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn())
					////
					.conciliacaoTransacaoTitulosDTO(toDTO(it)) //
					.build();
			
			return dto;
			
		}).collect(Collectors.toList());
		
		ConciliacaoBancariaDTO conciliacaoBancariaDTO = ConciliacaoBancariaDTO.builder()
				.id(conciliacaoBancariaEntity.getId())
				.bancoId(conciliacaoBancariaEntity.getBancoId())
				.agenciaId(conciliacaoBancariaEntity.getAgenciaId())
				.contaId(conciliacaoBancariaEntity.getContaId())
				.dataIni(conciliacaoBancariaEntity.getDataIni())
				.dataFim(conciliacaoBancariaEntity.getDataFim())
				.situacaoConciliacao(conciliacaoBancariaEntity.getSituacaoConciliacao())
				.transacoes(transacoesDTO)
				.build();
		
		return conciliacaoBancariaDTO;
	}

	private static PlanoContaDTO toDTO(PlanoContaEntity tituloPlanoContas) {
		if (tituloPlanoContas == null) {
			return null;
		}
		
		PlanoContaDTO dto = PlanoContaDTO.builder()
				.id(tituloPlanoContas.getId()) // Lazy vs eager Hibernate problem.
				//.codigo(tituloPlanoContas.getCodigo())
				//.descricao(tituloPlanoContas.getDescricao())
				.build();
		
		return dto;
	}

	private static List<ConciliacaoTransacaoTituloDTO> toDTO(ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		if (isNotEmpty(conciliacaoTransacaoEntity.getConciliacaoTransacaoTitulos())) {
			
			List<ConciliacaoTransacaoTituloDTO> titulos = conciliacaoTransacaoEntity.getConciliacaoTransacaoTitulos().stream().map(transacaoEntity -> {
				ConciliacaoTransacaoTituloDTO transacaoDTO = ConciliacaoTransacaoTituloDTO.builder()
						.id(transacaoEntity.getId())
						.tituloConciliadoId(transacaoEntity.getTituloConciliadoId())
						.tituloConciliadoDesc(transacaoEntity.getTituloConciliadoDesc())
						.tituloConciliadoValor(transacaoEntity.getTituloConciliadoValor())
						.tituloConciliadoDataVen(transacaoEntity.getTituloConciliadoDataVen())
						.tituloConciliadoDataPag(transacaoEntity.getTituloConciliadoDataPag())
						.tituloConciliadoMultiple(transacaoEntity.getTituloConciliadoMultiple())
						.dataConciliacao(transacaoEntity.getDataConciliacao())
						.situacaoConciliacaoTrn(transacaoEntity.getSituacaoConciliacaoTrn())
						.tituloPlanoContas(toDTO(transacaoEntity.getTituloPlanoContas()))
						.build();
				
				return transacaoDTO;
				
			}).collect(Collectors.toList());
			
			return titulos;
		}
		
		return null;
	}
	
	public static PlanoContaEntity toEntity(PlanoContaDTO tituloPlanoContas) {
		if (isEmpty(tituloPlanoContas)) {
			return null;
		}
		
		PlanoContaEntity entity = new PlanoContaEntity();
		entity.setId(tituloPlanoContas.getId());
		return entity;
	}

}
