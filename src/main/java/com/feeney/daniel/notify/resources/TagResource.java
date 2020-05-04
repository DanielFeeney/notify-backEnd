package com.feeney.daniel.notify.resources;

import java.util.Collection;
import java.util.List;
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

import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.model.Filtros;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.services.FiltrosService;
import com.feeney.daniel.notify.services.TagService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagResource {
	
		@Autowired public TagService tagService;
		
		@Autowired public FiltrosService filtrosService;
		
		@Autowired public UsuarioService usuarioService;
		
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@GetMapping("/{cpf}")
		public ResponseEntity<?> buscarTodos(@PathVariable String cpf) {
			 return ResponseEntity.status(HttpStatus.OK).body(tagService.listarTagDTODoUsuario(cpf));
		}
		
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@GetMapping()
		public ResponseEntity<?> buscarTodos() {
			 return ResponseEntity.status(HttpStatus.OK).body(tagService.listarTagDTO());
		}
	
	/*
	 * @GetMapping("/{id}") public ResponseEntity<?> buscar(@PathVariable Long id){
	 * Optional<Tag> optTag = tagService.buscar(id); if(optTag.isPresent()) return
	 * ResponseEntity.status(HttpStatus.OK).body(optTag.get()); else return
	 * ResponseEntity.notFound().build(); }
	 */
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@PostMapping public ResponseEntity<?> salvar(@RequestBody List<TagDTO> colTags){ 
			try {
				for(TagDTO tagDTO : colTags) {
					Optional<Filtros> optFiltro = filtrosService.buscarPorUsuarioETag(tagDTO.getCpfUsuario(), tagDTO.getId());
					if(optFiltro.isPresent()) {
						if(!tagDTO.getSelecionado()) {
							filtrosService.remover(optFiltro.get().getId());
						}
					}
					else {
						if(tagDTO.getSelecionado()) {
							Filtros filtro = new Filtros();
							filtro.setUsuario((usuarioService.buscarPeloCpf(tagDTO.getCpfUsuario())).get());
							filtro.setTag((tagService.buscar(tagDTO.getId())).get());
							filtrosService.salvar(filtro);
						}
					}
				}
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
	 
}
