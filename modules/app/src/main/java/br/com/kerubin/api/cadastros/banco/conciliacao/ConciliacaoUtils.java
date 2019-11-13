package br.com.kerubin.api.cadastros.banco.conciliacao;

import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT_ACCOUNT_TYPE;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_USER;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoTransacaoDTO;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaLookupResult;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.database.core.ServiceContextData;

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
					.trnData(it.getTrnData())
					.trnHistorico(it.getTrnHistorico())
					.trnDocumento(it.getTrnDocumento())
					.trnTipo(it.getTrnTipo())
					.trnValor(it.getTrnValor())
					//.conciliacaoBancaria(conciliacaoBancaria)
					.situacaoConciliacaoTrn(it.getSituacaoConciliacaoTrn())
					.tituloConciliadoId(it.getTituloConciliadoId())
					.tituloConciliadoDesc(it.getTituloConciliadoDesc())
					.dataConciliacao(it.getDataConciliacao())
					.conciliadoComErro(it.getConciliadoComErro())
					.conciliadoMsg(it.getConciliadoMsg())
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

}
