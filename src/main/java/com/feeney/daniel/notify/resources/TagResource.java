package com.feeney.daniel.notify.resources;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

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
import com.feeney.daniel.notify.model.PerfilPermissao;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.services.FiltrosService;
import com.feeney.daniel.notify.services.PermissaoService;
import com.feeney.daniel.notify.services.TagService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagResource {
	
		@Autowired public TagService tagService;
		
		@Autowired public FiltrosService filtrosService;
		
		@Autowired public UsuarioService usuarioService;
		
		@Autowired public PermissaoService permissaoService;
		
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@GetMapping()
		public ResponseEntity<?> buscarTodos() {
			 return ResponseEntity.status(HttpStatus.OK).body(tagService.listarTagDTO());
		}
		
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@GetMapping("publicacao/{idPublicacao}")
		public ResponseEntity<?> buscarTodosETodosDePublicacao(@PathVariable Long idPublicacao) {
			Collection<TagDTO> colTagDTO = tagService.listarTodosTagDTOETodosTagDTODePublicacao(idPublicacao);
			 return ResponseEntity.status(HttpStatus.OK).body(colTagDTO);
		}
		
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@GetMapping("/{tagId}")
		public ResponseEntity<?> buscarTag(@PathVariable Long tagId) {
			Optional<Tag> optTag = tagService.buscar(tagId);
			if(optTag.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(optTag.get());
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(new Tag());
			}
		}
	
	/*
	 * @GetMapping("/{id}") public ResponseEntity<?> buscar(@PathVariable Long id){
	 * Optional<Tag> optTag = tagService.buscar(id); if(optTag.isPresent()) return
	 * ResponseEntity.status(HttpStatus.OK).body(optTag.get()); else return
	 * ResponseEntity.notFound().build(); }
	 */
		@PreAuthorize("hasAnyRole('ROLE_CREATE_TAG')")
		@PostMapping public ResponseEntity<?> salvar(@RequestBody TagDTO tagDTO){ 
			try {
				if(tagDTO.getId() != null) {
					Optional<Tag> optTag = tagService.buscar(tagDTO.getId());
					if(optTag.isPresent()) {					
						Tag tag = optTag.get();					
						tag.setDescricao(tagDTO.getDescricao());
						
						tagService.salvar(tag);
						
						return ResponseEntity.status(HttpStatus.OK).build();
					}
					else {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
					}
				}
				else {
					Tag tag = new Tag(tagDTO.getDescricao(), true);
					tagService.salvar(tag);
					return ResponseEntity.status(HttpStatus.OK).build();
				}
				
				
			}
			catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		
		@PreAuthorize("hasAnyRole('ROLE_DELETE_TAG')")
		@PostMapping("/delete")
		public ResponseEntity<?> deletar(@PathParam(value = "tagId") String tagId) {
			Optional<Tag> optTag = tagService.buscar(Long.parseLong(tagId));
			if (optTag.isPresent()) {
				tagService.remover(Long.parseLong(tagId));
				return ResponseEntity.ok().build();
			} else
				return ResponseEntity.notFound().build();
		}
		
		
		@PreAuthorize("hasAnyRole('ROLE_TAG')")
		@GetMapping("permissao/{cpf}")
		public ResponseEntity<?> buscarTodosETodosDePublicacao(@PathVariable String cpf) {
			List<PerfilPermissao> listPerfilPermissao = permissaoService.buscarPerfilPermissaoPorCpf(cpf);
			if(listPerfilPermissao == null || listPerfilPermissao.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(false);
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
		}
	 
}
