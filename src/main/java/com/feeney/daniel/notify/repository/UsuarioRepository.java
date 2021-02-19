package com.feeney.daniel.notify.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.dto.UsuarioDTO;
import com.feeney.daniel.notify.model.Permissao;
import com.feeney.daniel.notify.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("Select u from Usuario u"
		+ " where u.cpf = ?1"
		+ " and u.dtNascimento = ?2"
		+ " and u.senha = ?3"
		+ " and u.ativo = true")
	Optional<Usuario> buscarPorCpfDataNascimentoESenha(String cpf, Date dataNascimento, String senha);
	
	@Query("Select u from Usuario u"
			+ " where u.cpf = ?1"
			+ " and u.dtNascimento = ?2"
			+ " and u.email = ?3"
			+ " and u.ativo = true")
		Optional<Usuario> buscarPorCpfDataNascimentoEEmail(String cpf, Date dataNascimento, String email);
	
	@Query("Select u from Usuario u"
			+ " where (u.cpf = ?1"
			+ " or u.email = ?2)"
			+ " and u.id <> ?3 "
			+ " and u.ativo = true")
	List<Usuario> ValidadorDeEmailECpfComId(String cpf, String email, Long id);
	
	@Query("Select u from Usuario u"
			+ " where (u.cpf = ?1"
			+ " or u.email = ?2)"
			+ " and u.ativo = true")
	List<Usuario> ValidadorDeEmailECpfSemId(String cpf, String email);
	
	
	Optional<Usuario> findByCpf(String cpf);
	
	Optional<Usuario> findByFcmToken(String fcmToken);
	
	@Query(" Select Count(u.id) from Usuario u "
			+ " join u.perfil p "
			+ " where u.cpf = ?1 "
			+ " and u.ativo = true "
			+ " and p.id in ( "
			+ " Select p2.id from PerfilPermissao pp "
			+ " join pp.perfil p2 "
			+ " join pp.permissao per "
			+ " where per.descricao = 'ROLE_POST_PUBLICACAO' "
			+ " and p2.id = p.id) ")
		Integer verificarCriacaoPublicacao(String cpf);
	
	@Query(" Select Count(u.id) from Usuario u "
			+ " join u.perfil p "
			+ " where u.cpf = ?1 "
			+ " and u.ativo = true "
			+ " and "
			+ "	("
			+ "	p.id in ( "
			+ " Select p2.id from PerfilPermissao pp "
			+ " join pp.perfil p2 "
			+ " join pp.permissao per "
			+ " where per.descricao = 'ROLE_EDIT_PUBLICACAO' "
			+ " and p2.id = p.id) or "
			+ " u.id in ("
			+ " Select u2.id from Publicacao pu "
			+ " join pu.usuarioPublicacao u2 "
			+ " where u.cpf = ?1 "
			+ " and pu.id = ?2 "
			+ " )"
			+ ") ")
		Integer verificarEdicaoPublicacao(String cpf, Long idPublicacao);
	
	@Query(" Select Count(u.id) from Usuario u "
			+ " join u.perfil p "
			+ " where u.cpf = ?1 "
			+ " and u.ativo = true "
			+ " and "
			+ "	(p.id in ( "
			+ " Select p2.id from PerfilPermissao pp "
			+ " join pp.perfil p2 "
			+ " join pp.permissao per "
			+ " where per.descricao = 'ROLE_DELETE_PUBLICACAO' "
			+ " and p2.id = p.id) or "
			+ " u.id in ("
			+ " Select u2.id from Publicacao pu "
			+ " join pu.usuarioPublicacao u2 "
			+ " where u2.cpf = ?1 "
			+ " and u2.ativo = true "
			+ " and pu.id = ?2 "
			+ " )"
			+ ") ")
		Integer verificarDelecaoPublicacao(String cpf, Long idPublicacao);
	
	@Query(" Select new com.feeney.daniel.notify.dto.UsuarioDTO("
			+ "u.id, u.cpf, u.nome, u.email, p.descricao," + 
			" u.dtNascimento"
			+ ") from Usuario u "
			+ "join u.perfil p "
			+ " where u.ativo = true")
	List<UsuarioDTO> listaUsuarioDTO();
}
