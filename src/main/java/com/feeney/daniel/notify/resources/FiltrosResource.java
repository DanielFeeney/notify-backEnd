package com.feeney.daniel.notify.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.model.Filtros;
import com.feeney.daniel.notify.services.FiltrosService;

@RestController
@CrossOrigin
@RequestMapping("/filtros")
public class FiltrosResource {
	
		@Autowired public FiltrosService filtrosService;
		
		@PreAuthorize("hasAnyRole('ROLE_FILTROS')")
		@GetMapping
		public ResponseEntity<?> buscarTodos() {
			 return ResponseEntity.status(HttpStatus.OK).body(filtrosService.buscarTodos());
		}

		@PreAuthorize("hasAnyRole('ROLE_FILTROS')")
		@GetMapping("/{id}") public ResponseEntity<?> buscar(@PathVariable Long id){
		Optional<Filtros> optFiltros = filtrosService.buscar(id);
		if(optFiltros.isPresent()) 
			return ResponseEntity.status(HttpStatus.OK).body(optFiltros.get()); 
		else 
			return ResponseEntity.notFound().build(); 
		}
		
		@PreAuthorize("hasAnyRole('ROLE_FILTROS')")
		@PostMapping public ResponseEntity<?> salvar(@RequestBody Filtros filtros){ 
			return ResponseEntity.status(HttpStatus.OK).body(filtrosService.salvar(filtros)); 
		}
	 
}
