package br.com.kerubin.api.cadastros.banco.conciliacao.controller;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoFileUploadResult;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoDTOConverter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoService;

@RestController
@RequestMapping("cadastros/banco/entities/conciliacaoBancaria")
public class ConciliacaoController {
	
	@Inject
	private ConciliacaoService conciliacaoService;
	
	@Autowired
	private ConciliacaoTransacaoService conciliacaoTransacaoService;
	
	@Autowired
	ConciliacaoTransacaoDTOConverter conciliacaoTransacaoDTOConverter;
	
	@PostMapping("/uploadArquivoConciliacao")
	public ResponseEntity<ConciliacaoFileUploadResult> uploadArquivoConciliacao(@RequestParam MultipartFile arquivoConciliacao) throws IOException {
		UUID conciliacaoId = conciliacaoService.processarArquivo(arquivoConciliacao.getInputStream());
		ConciliacaoFileUploadResult result = ConciliacaoFileUploadResult.builder()
				.conciliacaoId(conciliacaoId)
				.result(true)
				.message("OK")
				.build();
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	
	

}
