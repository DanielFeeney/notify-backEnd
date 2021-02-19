package com.feeney.daniel.notify.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.security.JWTUtil;
import com.feeney.daniel.notify.security.UserSS;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthResource {
	
	@Autowired public UsuarioService usuarioService;
	
	@Autowired public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<?> refreshToken(HttpServletResponse response){
		try {
			UserSS user = UsuarioService.authenticated();
			String token = jwtUtil.generateToken(user.getUsername());
			response.addHeader("Authorization", "Bearer " + token);
			response.addHeader("access-control-expose-headers", "Authorization");
			return ResponseEntity.noContent().build();
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	/*
	 * @PostMapping public ResponseEntity<?> salvarUsuario(@RequestBody Usuario
	 * usuario){ try {
	 * usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
	 * usuarioService.salvar(usuario); return
	 * ResponseEntity.status(HttpStatus.OK).build(); } catch(Exception e) { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); }
	 * 
	 * }
	 */

}
