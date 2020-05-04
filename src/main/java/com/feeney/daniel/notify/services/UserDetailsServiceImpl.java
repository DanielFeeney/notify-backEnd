package com.feeney.daniel.notify.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.model.Permissao;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	public PermissaoService permissaoService;

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if(!optUsuario.isPresent()) {
			throw new UsernameNotFoundException(cpf);
		}
		Usuario u = optUsuario.get();
		Set<Permissao> permissoes = permissaoService.buscarPermissoesPorPerfil(u.getPerfil().getId());
		return new UserSS(u.getId(),u.getDtNascimento(),u.getCpf(),u.getSenha(), permissoes);
	}
	

}
