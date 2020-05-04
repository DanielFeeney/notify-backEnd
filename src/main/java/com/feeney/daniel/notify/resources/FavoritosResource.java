package com.feeney.daniel.notify.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.model.Favoritos;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.services.FavoritosService;
import com.feeney.daniel.notify.services.PublicacaoService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/favoritos")
public class FavoritosResource {
	
		@Autowired public FavoritosService favoritosService;
		
		@Autowired public PublicacaoService publicacaoService;
		
		@Autowired public UsuarioService usuarioService;
		
		@PreAuthorize("hasAnyRole('ROLE_FAVORITOS')")
		@GetMapping
		public ResponseEntity<?> buscarTodos() {
			 return ResponseEntity.status(HttpStatus.OK).body(favoritosService.buscarTodos());
		}
		
		@PreAuthorize("hasAnyRole('ROLE_FAVORITOS')")
		@GetMapping("/verificar/{idPublicacao}/{cpf}")
		public ResponseEntity<?> verificar(
				@PathVariable(name = "idPublicacao") Long idPublicacao,
				@PathVariable(name = "cpf") String cpf) {
			Optional<Favoritos> optFavoritos = favoritosService.buscarPorUsuarioEPublicacao(cpf, idPublicacao);
			if(optFavoritos.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
			return ResponseEntity.status(HttpStatus.OK).body(false);			
		}
	
		@PreAuthorize("hasAnyRole('ROLE_FAVORITOS')")
		@GetMapping("/usuarios/{cpf}") public ResponseEntity<?> buscarFavoritos(@PathVariable String cpf){
		Collection<Publicacao> colPublicacao = favoritosService.buscarPublicacoes(cpf);
		if(colPublicacao != null && !colPublicacao.isEmpty()) 
			return ResponseEntity.status(HttpStatus.OK).body(colPublicacao); 
		else 
			return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());  
		}
		
		@PreAuthorize("hasAnyRole('ROLE_FAVORITOS')")
		@PostMapping("/delete") public ResponseEntity<?> delete(
				@PathParam(value = "idPublicacao") String idPublicacao,
				@PathParam(value = "cpf") String cpf){
			try {
				Optional<Favoritos> optFavoritos = favoritosService.buscarPorUsuarioEPublicacao(cpf, Long.parseLong(idPublicacao));
				favoritosService.remover(optFavoritos.get().getId());
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK).body(false);
			}
			
		}
		
		@PreAuthorize("hasAnyRole('ROLE_FAVORITOS')")
		@PostMapping("/save") public ResponseEntity<?> salvar(
				@PathParam(value = "idPublicacao") String idPublicacao,
				@PathParam(value = "cpf") String cpf){
			try {
				Favoritos favoritos = new Favoritos();
				favoritos.setUsuario((usuarioService.buscarPeloCpf(cpf)).get());
				favoritos.setPublicacao((publicacaoService.buscar(Long.parseLong(idPublicacao))).get());
				favoritosService.salvar(favoritos);
				return ResponseEntity.status(HttpStatus.OK).body(true); 
			}
			catch(Exception e) {
				return ResponseEntity.status(HttpStatus.OK).body(false); 
			}
		}
	 
}
