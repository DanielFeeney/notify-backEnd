package com.feeney.daniel.notify.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
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
import com.feeney.daniel.notify.model.Perfil;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.security.JWTUtil;
import com.feeney.daniel.notify.security.UserSS;
import com.feeney.daniel.notify.services.PerfilService;
import com.feeney.daniel.notify.services.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired public UsuarioService usuarioService;
	
	@Autowired public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired public PerfilService perfilService;
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@GetMapping()
	public ResponseEntity<?> buscarTodos() {
		 return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarioDTO());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@GetMapping("/{usuarioId}")
	public ResponseEntity<?> buscarUsuario(@PathVariable Long usuarioId) {
		Optional<Usuario> optUsuario = usuarioService.buscar(usuarioId);
		if(optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			return ResponseEntity.status(HttpStatus.OK).body(
					new UsuarioDTO(usuarioId, usuario.getCpf(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil().getId(), usuario.getDtNascimento()));
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@GetMapping("perfil/{cpf}")
	public ResponseEntity<?> buscarUsuarioPorCpf(@PathVariable String cpf) {
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if(optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			return ResponseEntity.status(HttpStatus.OK).body(
					new UsuarioDTO(usuario.getId(), usuario.getCpf(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil().getDescricao(), usuario.getDtNascimento()));
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	
	/* @PreAuthorize("hasAnyRole('ROLE_CREATE_USUARIO')") */
	@PostMapping public ResponseEntity<?> salvar(@RequestBody UsuarioDTO usuarioDTO){ 
		try {
			if(usuarioService.validarEmailECPF(usuarioDTO.getId(), usuarioDTO.getEmail(), usuarioDTO.getCpf())) {
				Optional<Perfil> optPerfil = perfilService.buscar(usuarioDTO.getPerfilId());
				if(!optPerfil.isPresent()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
				if(usuarioDTO.getId() != null) {
					Optional<Usuario> optUsuario = usuarioService.buscar(usuarioDTO.getId());
					if(optUsuario.isPresent()) {					
						Usuario usuario = optUsuario.get();					
						usuario.setUsuario(usuarioDTO);
						usuario.setPerfil(optPerfil.get());
						
						
						usuarioService.salvar(usuario);
						
						return ResponseEntity.status(HttpStatus.OK).build();
					}
					else {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
					}
				}
				else {
					Usuario usuario = new Usuario();
					usuario.setUsuario(usuarioDTO);
					usuario.setPerfil(optPerfil.get());
					usuario.setMsg(false);
					if(usuarioDTO.getSenha() == null || usuarioDTO.getSenha().isEmpty()) {
						usuario.setSenha(bCryptPasswordEncoder.encode("123456"));
					}
					else {
						usuario.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
					}
					
					usuarioService.salvar(usuario);
					return ResponseEntity.status(HttpStatus.OK).build();
				}
			}
			else {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}			
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ACESSO')")
	@PostMapping("/mudarSenha")
	public ResponseEntity<?> mudarSenha(
			@PathParam(value = "cpf") String cpf,
			@PathParam(value = "senha") String senha) {
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if (optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			usuario.setSenha(bCryptPasswordEncoder.encode(senha));
			usuarioService.salvar(usuario);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}
	
	/* @PreAuthorize("hasAnyRole('ROLE_ACESSO')") */
	@PostMapping("/resetarSenha")
	public ResponseEntity<?> resetarSenha(@RequestBody UsuarioDTO usuarioDTO) {		
		try {
			Optional<Usuario> optUsuario = usuarioService.buscarPelasInformacoesUsuarioResetarSenha(usuarioDTO.getCpf(), usuarioDTO.getDataNascimento(), usuarioDTO.getEmail());
			if (optUsuario.isPresent()) {
				Usuario usuario = optUsuario.get();
				usuario.setSenha(bCryptPasswordEncoder.encode("123456"));
				usuarioService.salvar(usuario);
				return ResponseEntity.ok().build();
			} else
				return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_DELETE_USUARIO')")
	@PostMapping("/delete")
	public ResponseEntity<?> deletar(@PathParam(value = "usuarioId") String usuarioId) {
		Optional<Usuario> optUsuario = usuarioService.buscar(Long.parseLong(usuarioId));
		if (optUsuario.isPresent()) {
			usuarioService.remover(Long.parseLong(usuarioId));
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}

}
