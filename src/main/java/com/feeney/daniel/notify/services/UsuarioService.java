package com.feeney.daniel.notify.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.dto.UsuarioDTO;
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
	
	public List<UsuarioDTO> listarUsuarioDTO() {
		return usuarioRepository.listaUsuarioDTO();
	}
	
	public Integer verificarCriacao(String cpf) {
		return usuarioRepository.verificarCriacaoPublicacao(cpf);
	}
	
	public Integer verificarEdicao(String cpf, Long idPublicacao) {
		return usuarioRepository.verificarEdicaoPublicacao(cpf, idPublicacao);
	}
	
	public Integer verificarDelecao(String cpf, Long idPublicacao) {
		return usuarioRepository.verificarDelecaoPublicacao(cpf,idPublicacao);
	}
	
	public Optional<Usuario> buscarPelasInformacoesUsuario(String cpf, Date dataNascimento, String senha){
		return usuarioRepository.buscarPorCpfDataNascimentoESenha(cpf, set3horas(dataNascimento), bCryptPasswordEncoder.encode(senha));
	}
	
	public Optional<Usuario> buscarPelasInformacoesUsuarioResetarSenha(String cpf, Date dataNascimento, String email){
		Date data = set3horas(dataNascimento);
		return usuarioRepository.buscarPorCpfDataNascimentoEEmail(cpf, data, email);
	}
	
	public Optional<Usuario> buscarPeloCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf);
	}
	
	public Optional<Usuario> buscarPeloToken(String fcmToken) {
		return usuarioRepository.findByFcmToken(fcmToken);
	}
	
	public static UserSS authenticated() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth instanceof AnonymousAuthenticationToken) {
				System.out.println("Erro");
			}
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
	
	public Boolean validarEmailECPF(Long id, String email, String cpf) {
		if(id == null) {
			return usuarioRepository.ValidadorDeEmailECpfSemId(cpf, email).isEmpty();
		}
		else {
			return usuarioRepository.ValidadorDeEmailECpfComId(cpf, email, id).isEmpty();
		}
	} 
	
	private Date set3horas(Date dataNascimento) {
		Calendar data = Calendar.getInstance();
		data.setTime(dataNascimento);
		data.set(Calendar.HOUR, data.get(Calendar.HOUR) + 3);
		return data.getTime();
	}

}
