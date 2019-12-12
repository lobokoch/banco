package br.com.kerubin.api.cadastros.banco.conciliacaotransacao.service;

import static br.com.kerubin.api.servicecore.util.CoreUtils.isNotEmpty;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.kerubin.api.cadastros.banco.SituacaoConciliacaoTrn;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoEntity;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoListFilter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoServiceImpl;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.QConciliacaoTransacaoEntity;

@Primary
@Service
public class CustomConciliacaoTransacaoServiceImpl extends ConciliacaoTransacaoServiceImpl {
	
	@Override
	public ConciliacaoTransacaoEntity update(UUID id, ConciliacaoTransacaoEntity conciliacaoTransacaoEntity) {
		
		// Se removeu todos os títulos, entende que deseja lançar no caixa.
		if (conciliacaoTransacaoEntity.getConciliacaoTransacaoTitulos() == null || conciliacaoTransacaoEntity.getConciliacaoTransacaoTitulos().isEmpty()) {
			// conciliacaoTransacaoEntity.setTituloConciliadoDesc(null); esse o usuário pode querer alterar
			conciliacaoTransacaoEntity.setTituloConciliadoId(null);
			conciliacaoTransacaoEntity.setSituacaoConciliacaoTrn(SituacaoConciliacaoTrn.CONCILIAR_CAIXA);
			conciliacaoTransacaoEntity.setDataConciliacao(null);
		}
		
		return super.update(id, conciliacaoTransacaoEntity);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ConciliacaoTransacaoEntity> list(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter,
			Pageable pageable) {
		
		String fieldId = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.id.getMetadata().getName();
		String fieldTrnValor = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnValor.getMetadata().getName();
		String fieldTrnTipo = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnTipo.getMetadata().getName();
		String fieldTrnData = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnData.getMetadata().getName();
		String fieldTrnDocumento = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.trnDocumento.getMetadata().getName();
		
		
		boolean conciliadoComMaisDeUmTitulo = false;
		boolean conciliacaoTransacaoComMesmoTitulo = false;
		if (isNotEmpty(conciliacaoTransacaoListFilter.getCustomParams())) {
			Map<Object, Object> customParams = conciliacaoTransacaoListFilter.getCustomParams();
			
			
			Object objConciliadoComMaisDeUmTitulo = customParams.get("conciliadoComMaisDeUmTitulo");
			if (isNotEmpty(objConciliadoComMaisDeUmTitulo)) {
				conciliadoComMaisDeUmTitulo = (boolean) objConciliadoComMaisDeUmTitulo;
			}
			
			Object objConciliacaoTransacaoComMesmoTitulo = customParams.get("conciliacaoTransacaoComMesmoTitulo");
			if (isNotEmpty(objConciliacaoTransacaoComMesmoTitulo)) {
				conciliacaoTransacaoComMesmoTitulo = (boolean) objConciliacaoTransacaoComMesmoTitulo;
			}
		}
		
		
		if (!conciliadoComMaisDeUmTitulo && !conciliacaoTransacaoComMesmoTitulo) {
			Iterator<Order> orders = pageable.getSort().iterator();
			if (pageable.getSort().isSorted() && orders.hasNext()) {
				Order order = orders.next();
				if (fieldId.equals(order.getProperty()) && !orders.hasNext()) {
					Sort sort = Sort.by(fieldTrnData).ascending()
							.and(Sort.by(fieldTrnTipo).descending())
							.and(Sort.by(fieldTrnValor).descending())
							.and(Sort.by(fieldTrnDocumento).ascending());
					
					pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
					
				}
			}
		}
		
		if (conciliadoComMaisDeUmTitulo) {
			
			Sort sort = Sort.by(fieldTrnValor).descending()
					.and(Sort.by(fieldTrnTipo).descending())
					.and(Sort.by(fieldTrnData).ascending())
					.and(Sort.by(fieldTrnDocumento).ascending());
					//.and(Sort.by(fieldTrnHistorico).ascending());
			
			if (pageable.getSort().isSorted()) {
				sort.and(pageable.getSort());
			}
			
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
	
		if (conciliacaoTransacaoComMesmoTitulo) {
			
			String tituloConciliadoId = QConciliacaoTransacaoEntity.conciliacaoTransacaoEntity.tituloConciliadoId.getMetadata().getName();
			
			Sort sort = Sort.by(tituloConciliadoId)
					.and(Sort.by(fieldTrnTipo).descending())
					.and(Sort.by(fieldTrnData).ascending())
					.and(Sort.by(fieldTrnDocumento).ascending());
			
			if (pageable.getSort().isSorted()) {
				sort = sort.and(pageable.getSort());
			}
			
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
			
		}
			
		
		return super.list(conciliacaoTransacaoListFilter, pageable);
	}
	
	

}
