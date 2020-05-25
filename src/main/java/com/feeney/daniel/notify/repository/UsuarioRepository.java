package com.feeney.daniel.notify.repository;

import java.util.Date;
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
		+ " and u.senha = ?3")
	Optional<Usuario> buscarPorCpfDataNascimentoESenha(String cpf, Date dataNascimento, String senha);
	
	
	Optional<Usuario> findByCpf(String cpf);
	
	@Query(" Select Count(u.id) from Usuario u "
			+ " join u.perfil p "
			+ " where u.cpf = ?1 "
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
			+ " where u.cpf = ?1 "
			+ " and pu.id = ?2 "
			+ " )"
			+ ") ")
		Integer verificarDelecaoPublicacao(String cpf, Long idPublicacao);
}
