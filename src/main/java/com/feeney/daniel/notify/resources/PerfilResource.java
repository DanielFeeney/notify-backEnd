package com.feeney.daniel.notify.resources;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.services.PerfilService;

@RestController
@CrossOrigin
@RequestMapping("/perfil")
public class PerfilResource {
	
	@Autowired public PerfilService perfilService;
	
	@GetMapping("/sign-in")
	public ResponseEntity<?> signIn(){
		return ResponseEntity.status(HttpStatus.OK).body(perfilService.buscarTodosSignIn());
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@GetMapping()
	public ResponseEntity<?> listar(){
		return ResponseEntity.status(HttpStatus.OK).body(perfilService.buscarTodos());		
	}

}
