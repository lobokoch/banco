package br.com.kerubin.api.cadastros.banco.conciliacao.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;

@RestController
@RequestMapping("cadastros/banco/entities/conciliacaoBancaria")
public class ConciliacaoController {
	
	@Inject
	private ConciliacaoService conciliacaoService;
	
	@PostMapping("/uploadArquivoConciliacao")
	public String uploadArquivoConciliacao(@RequestParam MultipartFile arquivoConciliacao) throws IOException {
		conciliacaoService.processarArquivo(arquivoConciliacao.getInputStream());
		return "OK";
	}

}
