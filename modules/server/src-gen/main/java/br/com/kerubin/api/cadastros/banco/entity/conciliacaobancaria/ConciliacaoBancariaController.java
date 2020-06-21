/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria;

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

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("cadastros/banco/entities/conciliacaoBancaria")
public class ConciliacaoBancariaController {
	
	@Autowired
	private ConciliacaoBancariaService conciliacaoBancariaService;
	
	@Autowired
	ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter;
	
	@Autowired
	private ConciliacaoBancariaRuleFunctions conciliacaoBancariaRuleFunctions;
	
	@Transactional
	@PostMapping
	public ResponseEntity<ConciliacaoBancaria> create(@Valid @RequestBody ConciliacaoBancaria conciliacaoBancaria) {
		ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.create(conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
		return ResponseEntity.status(HttpStatus.CREATED).body(conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity));
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/{id}")
	public ResponseEntity<ConciliacaoBancaria> read(@PathVariable java.util.UUID id) {
		try {
			ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.read(id);
			return ResponseEntity.ok(conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<ConciliacaoBancaria> update(@PathVariable java.util.UUID id, @Valid @RequestBody ConciliacaoBancaria conciliacaoBancaria) {
		try {
			ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaService.update(id, conciliacaoBancariaDTOConverter.convertDtoToEntity(conciliacaoBancaria));
			return ResponseEntity.ok(conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable java.util.UUID id) {
		conciliacaoBancariaService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping("/deleteInBulk")
	public void deleteInBulk(@RequestBody java.util.List<java.util.UUID> idList) {
		conciliacaoBancariaService.deleteInBulk(idList);
	}
	
	@Transactional(readOnly = true)
	@GetMapping
	public PageResult<ConciliacaoBancaria> list(ConciliacaoBancariaListFilter conciliacaoBancariaListFilter, Pageable pageable) {
		Page<ConciliacaoBancariaEntity> page = conciliacaoBancariaService.list(conciliacaoBancariaListFilter, pageable);
		List<ConciliacaoBancaria> content = page.getContent().stream().map(pe -> conciliacaoBancariaDTOConverter.convertEntityToDto(pe)).collect(Collectors.toList());
		PageResult<ConciliacaoBancaria> pageResult = new PageResult<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
		return pageResult;
	}
	
	@Transactional(readOnly = true)
	@GetMapping("/autoComplete")
	public Collection<ConciliacaoBancariaAutoComplete> autoComplete(@RequestParam("query") String query) {
		Collection<ConciliacaoBancariaAutoComplete> result = conciliacaoBancariaService.autoComplete(query);
		return result;
	}
	
	
	
	@Transactional
	@PutMapping("/conciliacaoBancariaRuleFunctionAplicarConciliacaoBancaria/{id}")
	public ResponseEntity<ConciliacaoBancaria> conciliacaoBancariaRuleFunctionAplicarConciliacaoBancaria(@PathVariable java.util.UUID id, @Valid @RequestBody ConciliacaoBancaria conciliacaoBancaria) {
		try {
			ConciliacaoBancariaEntity conciliacaoBancariaEntity = conciliacaoBancariaRuleFunctions.aplicarConciliacaoBancaria(id, conciliacaoBancaria);
			return ResponseEntity.ok(conciliacaoBancariaDTOConverter.convertEntityToDto(conciliacaoBancariaEntity));
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
