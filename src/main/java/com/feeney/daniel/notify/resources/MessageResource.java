package com.feeney.daniel.notify.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.MessageService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageResource {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void register(@RequestParam("token") String token, @RequestParam("cpf") String cpf) {
		System.out.println("register: " + token);
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if (optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			usuario.setMsg(true);
			usuario.setFcmToken(token);
			usuarioService.salvar(usuario);
		}
	}

	@PostMapping("/unregister")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unregister(@RequestParam("token") String token, @RequestParam("cpf") String cpf) {
		System.out.println("unregister: " + token);
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if (optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			usuario.setMsg(false);
			usuarioService.salvar(usuario);
		}
	}
	
	@PostMapping("/atualizaToken")
	public ResponseEntity<?> atualizaToken(@RequestParam("token") String token, @RequestParam("cpf") String cpf) {
		System.out.println("atualizando: " + token);
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if (optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			usuario.setFcmToken(token);
			usuarioService.salvar(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuario.getMsg());
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
	}

}
