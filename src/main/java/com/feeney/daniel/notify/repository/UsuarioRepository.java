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

}
