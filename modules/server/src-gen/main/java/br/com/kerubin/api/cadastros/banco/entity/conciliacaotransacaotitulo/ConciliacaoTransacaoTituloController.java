/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacaotitulo;

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

		
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoAutoComplete;
import br.com.kerubin.api.cadastros.banco.entity.planoconta.PlanoContaAutoComplete;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("cadastros/banco/entities/conciliacaoTransacaoTitulo")
public class ConciliacaoTransacaoTituloController {
	
	@Autowired
	private ConciliacaoTransacaoTituloService conciliacaoTransacaoTituloService;
	
	@Autowired
	ConciliacaoTransacaoTituloDTOConverter conciliacaoTransacaoTituloDTOConverter;
	
	@Transactional
	@PostMapping
	public ResponseEntity<ConciliacaoTransacaoTitulo> create(@Valid @RequestBody ConciliacaoTransacaoTitulo conciliacaoTransacaoTitulo) {
		ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity = conciliacaoTransacaoTituloService.create(conciliacaoTransacaoTituloDTOConverter.convertDtoToEntity(conciliacaoTransacaoTitulo));
		return ResponseEntity.status(HttpStatus.CREATED).body(conciliacaoTransacaoTituloDTOConverter.convertEntityToDto(conciliacaoTransacaoTituloEntity));
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/{id}")
	public ResponseEntity<ConciliacaoTransacaoTitulo> read(@PathVariable java.util.UUID id) {
		try {
			ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity = conciliacaoTransacaoTituloService.read(id);
			return ResponseEntity.ok(conciliacaoTransacaoTituloDTOConverter.convertEntityToDto(conciliacaoTransacaoTituloEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<ConciliacaoTransacaoTitulo> update(@PathVariable java.util.UUID id, @Valid @RequestBody ConciliacaoTransacaoTitulo conciliacaoTransacaoTitulo) {
		try {
			ConciliacaoTransacaoTituloEntity conciliacaoTransacaoTituloEntity = conciliacaoTransacaoTituloService.update(id, conciliacaoTransacaoTituloDTOConverter.convertDtoToEntity(conciliacaoTransacaoTitulo));
			return ResponseEntity.ok(conciliacaoTransacaoTituloDTOConverter.convertEntityToDto(conciliacaoTransacaoTituloEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable java.util.UUID id) {
		conciliacaoTransacaoTituloService.delete(id);
	}
	
	@Transactional(readOnly = true)
	@GetMapping
	public PageResult<ConciliacaoTransacaoTitulo> list(ConciliacaoTransacaoTituloListFilter conciliacaoTransacaoTituloListFilter, Pageable pageable) {
		Page<ConciliacaoTransacaoTituloEntity> page = conciliacaoTransacaoTituloService.list(conciliacaoTransacaoTituloListFilter, pageable);
		List<ConciliacaoTransacaoTitulo> content = page.getContent().stream().map(pe -> conciliacaoTransacaoTituloDTOConverter.convertEntityToDto(pe)).collect(Collectors.toList());
		PageResult<ConciliacaoTransacaoTitulo> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		return pageResult;
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/autoComplete")
	public Collection<ConciliacaoTransacaoTituloAutoComplete> autoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoTransacaoTituloAutoComplete> result = conciliacaoTransacaoTituloService.autoComplete(query);
		return result;
	}
	
	
	
	// Begin relationships autoComplete 
	
	@Transactional(readOnly = true)
	@GetMapping("/conciliacaoTransacaoConciliacaoTransacaoAutoComplete")
	public Collection<ConciliacaoTransacaoAutoComplete> conciliacaoTransacaoConciliacaoTransacaoAutoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoTransacaoAutoComplete> result = conciliacaoTransacaoTituloService.conciliacaoTransacaoConciliacaoTransacaoAutoComplete(query);
		return result;
	}
	
	
	@Transactional(readOnly = true)
	@GetMapping("/planoContaTituloPlanoContasAutoComplete")
	public Collection<PlanoContaAutoComplete> planoContaTituloPlanoContasAutoComplete(@RequestParam("query") String query) {
		Collection<PlanoContaAutoComplete> result = conciliacaoTransacaoTituloService.planoContaTituloPlanoContasAutoComplete(query);
		return result;
	}
	
	// End relationships autoComplete
	
}
