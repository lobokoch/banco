package br.com.kerubin.api.cadastros.banco.conciliacao.controller;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoBancariaAsyncExecution;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.ConciliacaoFileUploadResult;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.CountConciliacaoTransacaoComMaisDeUmTituloDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.model.CountDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelper;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoDTOConverter;

@RestController
@RequestMapping("cadastros/banco/entities/conciliacaoBancaria")
public class ConciliacaoController {
	
	@Inject
	private ConciliacaoService conciliacaoService;
	
	@Inject
	private ConciliacaoServiceHelper conciliacaoServiceHelper;
	
	@Autowired
	ConciliacaoTransacaoDTOConverter conciliacaoTransacaoDTOConverter;
	
	@PostMapping("/uploadArquivoConciliacao")
	public ResponseEntity<ConciliacaoFileUploadResult> uploadArquivoConciliacao(@RequestParam MultipartFile arquivoConciliacao) throws IOException {
		ConciliacaoBancariaAsyncExecution execAsync = conciliacaoService.processarArquivo(arquivoConciliacao.getInputStream());
		UUID conciliacaoId = execAsync.getConciliacaoBancaria().getId();
		
		ConciliacaoFileUploadResult result = ConciliacaoFileUploadResult.builder()
				.conciliacaoId(conciliacaoId)
				.result(true)
				.message("OK")
				.build();
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@GetMapping("/countConciliacaoTransacaoComMaisDeUmTitulo/{id}")
	public ResponseEntity<CountConciliacaoTransacaoComMaisDeUmTituloDTO> countConciliacaoTransacaoComMaisDeUmTitulo(@PathVariable java.util.UUID id) {
		long count = conciliacaoService.countConciliacaoTransacaoComMaisDeUmTitulo(id);
		
		CountConciliacaoTransacaoComMaisDeUmTituloDTO result = new CountConciliacaoTransacaoComMaisDeUmTituloDTO(count);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/countConciliacaoTransacaoComTitulosRepetidos/{id}")
	public ResponseEntity<CountDTO> countConciliacaoTransacaoComTitulosRepetidos(@PathVariable java.util.UUID id) {
		long count = conciliacaoServiceHelper.countConciliacaoTransacaoComTitulosRepetidos(id);
		
		CountDTO result = new CountDTO(count);
		return ResponseEntity.ok(result);
	}
	
	
	

}
