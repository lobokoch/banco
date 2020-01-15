/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.kerubin.api.cadastros.banco.common.PageResult;

		
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo.ConciliacaoTransacaoTituloAutoComplete;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("cadastros/banco/entities/conciliacaoTransacao")
public class ConciliacaoTransacaoController {
	
	@Autowired
	private ConciliacaoTransacaoService conciliacaoTransacaoService;
	
	@Autowired
	ConciliacaoTransacaoDTOConverter conciliacaoTransacaoDTOConverter;
	
	@Transactional
	@PostMapping
	public ResponseEntity<ConciliacaoTransacao> create(@Valid @RequestBody ConciliacaoTransacao conciliacaoTransacao) {
		ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.create(conciliacaoTransacaoDTOConverter.convertDtoToEntity(conciliacaoTransacao));
		return ResponseEntity.status(HttpStatus.CREATED).body(conciliacaoTransacaoDTOConverter.convertEntityToDto(conciliacaoTransacaoEntity));
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/{id}")
	public ResponseEntity<ConciliacaoTransacao> read(@PathVariable java.util.UUID id) {
		try {
			ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.read(id);
			return ResponseEntity.ok(conciliacaoTransacaoDTOConverter.convertEntityToDto(conciliacaoTransacaoEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<ConciliacaoTransacao> update(@PathVariable java.util.UUID id, @Valid @RequestBody ConciliacaoTransacao conciliacaoTransacao) {
		try {
			ConciliacaoTransacaoEntity conciliacaoTransacaoEntity = conciliacaoTransacaoService.update(id, conciliacaoTransacaoDTOConverter.convertDtoToEntity(conciliacaoTransacao));
			return ResponseEntity.ok(conciliacaoTransacaoDTOConverter.convertEntityToDto(conciliacaoTransacaoEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable java.util.UUID id) {
		conciliacaoTransacaoService.delete(id);
	}
	
	@Transactional(readOnly = true)
	@GetMapping
	public PageResult<ConciliacaoTransacao> list(ConciliacaoTransacaoListFilter conciliacaoTransacaoListFilter, Pageable pageable) {
		Page<ConciliacaoTransacaoEntity> page = conciliacaoTransacaoService.list(conciliacaoTransacaoListFilter, pageable);
		List<ConciliacaoTransacao> content = page.getContent().stream().map(pe -> conciliacaoTransacaoDTOConverter.convertEntityToDto(pe)).collect(Collectors.toList());
		PageResult<ConciliacaoTransacao> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		return pageResult;
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/autoComplete")
	public Collection<ConciliacaoTransacaoAutoComplete> autoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoTransacaoAutoComplete> result = conciliacaoTransacaoService.autoComplete(query);
		return result;
	}
	
	
	@GetMapping("/conciliacaoTransacaoTrnHistoricoAutoComplete")
	public Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> conciliacaoTransacaoTrnHistoricoAutoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> result = conciliacaoTransacaoService.conciliacaoTransacaoTrnHistoricoAutoComplete(query);
		return result;
	}
	
	@GetMapping("/conciliacaoTransacaoTrnDocumentoAutoComplete")
	public Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> conciliacaoTransacaoTrnDocumentoAutoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> result = conciliacaoTransacaoService.conciliacaoTransacaoTrnDocumentoAutoComplete(query);
		return result;
	}
	
	
	// Begin relationships autoComplete 
	
	@Transactional(readOnly = true)
	@GetMapping("/conciliacaoBancariaConciliacaoBancariaAutoComplete")
	public Collection<ConciliacaoBancariaAutoComplete> conciliacaoBancariaConciliacaoBancariaAutoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoBancariaAutoComplete> result = conciliacaoTransacaoService.conciliacaoBancariaConciliacaoBancariaAutoComplete(query);
		return result;
	}
	
	
	@Transactional(readOnly = true)
	@PostMapping(value = "/planoContaTituloPlanoContasAutoComplete", params = { "query" })
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(@RequestParam("query") String query, @RequestBody ConciliacaoTransacao conciliacaoTransacao) {
		Collection<PlanoContaAutoComplete> result = conciliacaoTransacaoService.planoContaTituloPlanoContasAutoComplete(query, conciliacaoTransacao);
		return result;
	}
	
	
	@Transactional(readOnly = true)
	@GetMapping("/conciliacaoTransacaoTituloConciliacaoTransacaoTitulosAutoComplete")
	public Collection<ConciliacaoTransacaoTituloAutoComplete> conciliacaoTransacaoTituloConciliacaoTransacaoTitulosAutoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoTransacaoTituloAutoComplete> result = conciliacaoTransacaoService.conciliacaoTransacaoTituloConciliacaoTransacaoTitulosAutoComplete(query);
		return result;
	}
	
	// End relationships autoComplete
	
				
	// findBy methods
	
	@Transactional(readOnly = true)
	@GetMapping("/findConciliacaoTransacaoByConciliacaoBancaria")
	public Collection<ConciliacaoTransacao> findConciliacaoTransacaoByConciliacaoBancaria(java.util.UUID id) {
		Collection<ConciliacaoTransacaoEntity> content = conciliacaoTransacaoService.findConciliacaoTransacaoByConciliacaoBancaria(id);
		List<ConciliacaoTransacao> result = content.stream().map(it -> conciliacaoTransacaoDTOConverter.convertEntityToDto(it)).collect(Collectors.toList());
		return result;
	}
	
}
