package com.feeney.daniel.notify.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.repository.UsuarioRepository;
import com.feeney.daniel.notify.security.UserSS;

@Service
public class UsuarioService implements IObject<Usuario> {
	
	@Autowired public UsuarioRepository usuarioRepository;
	
	@Autowired public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> buscar(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void remover(Long id) {
		 usuarioRepository.deleteById(id);
		
	}
	
	public Optional<Usuario> buscarPelasInformacoesUsuario(String cpf, Date dataNascimento, String senha){
		return usuarioRepository.buscarPorCpfDataNascimentoESenha(cpf, dataNascimento, bCryptPasswordEncoder.encode(senha));
	}
	
	public Optional<Usuario> buscarPeloCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf);
	}
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}

}
