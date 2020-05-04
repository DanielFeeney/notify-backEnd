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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.dto.PublicacaoDTO;
import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.PublicacaoTag;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.PublicacaoService;
import com.feeney.daniel.notify.services.PublicacaoTagService;
import com.feeney.daniel.notify.services.TagService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/publicacao")
public class PublicacaoResource {
	
		@Autowired public PublicacaoService publicacaoService;
		@Autowired public UsuarioService usuarioService;
		@Autowired public PublicacaoTagService publicacaoTagService;
		
		@Autowired public TagService tagService;
		
		@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
		@GetMapping("/preferencias/{cpf}")
		public ResponseEntity<?> buscarTodos(@PathVariable String cpf,
				@RequestParam(value = "page", defaultValue = "0")Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "10")Integer linesPerPage) {
			List<Publicacao> list = publicacaoService.buscarTodosPelaPreferenciaDoUsuario(cpf, page, linesPerPage);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}

		@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
		@GetMapping("/{idPublicacao}") public ResponseEntity<?> buscar(@PathVariable Long idPublicacao){
		Optional<Publicacao> optPublicacao = publicacaoService.buscar(idPublicacao);
		if(optPublicacao.isPresent()) {
			PublicacaoDTO publicacaoDTO = new PublicacaoDTO(optPublicacao.get());
			publicacaoDTO.setColTag(tagService.listarTagDePublicacao(idPublicacao));
			return ResponseEntity.status(HttpStatus.OK).body(publicacaoDTO); 
		}			
		else 
			return ResponseEntity.notFound().build(); 
		}
		
		@PreAuthorize("hasAnyRole('ROLE_POST_PUBLICACAO')")
		@PostMapping public ResponseEntity<?> salvar(@RequestBody PublicacaoDTO publicacaoDTO){
			Publicacao publicacao = new Publicacao(publicacaoDTO);
			Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(publicacaoDTO.getCpfUsuario());
			if(optUsuario.isPresent()) {
				publicacao.setUsuarioPublicacao(optUsuario.get());
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build(); 
			}
			publicacaoService.salvar(publicacao);
			
			for(Tag tag : publicacaoDTO.getColTag()) {
				PublicacaoTag publicacaoTag = new PublicacaoTag(publicacao, tag);
				publicacaoTagService.salvar(publicacaoTag);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(publicacao); 
		}
	 
}
