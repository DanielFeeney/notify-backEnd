package com.feeney.daniel.notify.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.dto.UsuarioDTO;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired public UsuarioService usuarioService;
	
	@PreAuthorize("hasAnyRole('ROLE_ACESSO')")
	@PostMapping
	public ResponseEntity<?> acessoUsuario(@RequestBody UsuarioDTO usuarioDTO){
		Optional<Usuario> optUsuario =  usuarioService.buscarPelasInformacoesUsuario(usuarioDTO.getCpf(), usuarioDTO.getDataNascimento(), usuarioDTO.getSenha());
		if(optUsuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optUsuario.get().getId());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
}
