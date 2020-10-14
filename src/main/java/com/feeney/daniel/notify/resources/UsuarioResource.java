package com.feeney.daniel.notify.resources;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.dto.UsuarioDTO;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.PublicacaoService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired public UsuarioService usuarioService;
	
	@Autowired public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PreAuthorize("hasAnyRole('ROLE_ACESSO')")
	@PostMapping("/criacao")
	public ResponseEntity<?> criacao(
			@PathParam(value = "cpf") String cpf){
		try {
			if(usuarioService.verificarCriacao(cpf) > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ACESSO')")
	@PostMapping("/edicao") public ResponseEntity<?> edicao(
			@PathParam(value = "idPublicacao") String idPublicacao,
			@PathParam(value = "cpf") String cpf){
		try {
			if(usuarioService.verificarEdicao(cpf, Long.parseLong(idPublicacao)) > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ACESSO')")
	@PostMapping("/delecao")
	public ResponseEntity<?> delecao(
			@PathParam(value = "idPublicacao") String idPublicacao,
			@PathParam(value = "cpf") String cpf){
		try {
			if(usuarioService.verificarDelecao(cpf, Long.parseLong(idPublicacao)) > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
}
