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
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.FiltrosService;
import com.feeney.daniel.notify.services.TagService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/filtros")
public class FiltrosResource {
	
		@Autowired public FiltrosService filtrosService;
		
		@Autowired public UsuarioService usuarioService;
		
		@Autowired public TagService tagService;
		
		@PreAuthorize("hasAnyRole('ROLE_FILTROS')")
		@GetMapping("/{cpf}")
		public ResponseEntity<?> buscarTodos(@PathVariable String cpf) {
			 return ResponseEntity.status(HttpStatus.OK).body(tagService.listarTagDTODoUsuario(cpf));
		}
		
		@PreAuthorize("hasAnyRole('ROLE_FILTROS')")
		@GetMapping("/validarEnviarMsg/{cpf}")
		public ResponseEntity<?> validarEnviarMsg(@PathVariable String cpf) {
			 Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
			 if(optUsuario.isPresent()) {
				 Boolean msg1 = optUsuario.get().getMsg();
				 boolean msg = optUsuario.get().getMsg() == true ? true : false;
				 return ResponseEntity.status(HttpStatus.OK).body(msg); 
			 }
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		@PreAuthorize("hasAnyRole('ROLE_FILTROS')")
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
