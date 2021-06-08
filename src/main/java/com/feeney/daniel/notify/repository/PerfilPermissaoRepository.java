package com.feeney.daniel.notify.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.Perfil;
import com.feeney.daniel.notify.model.PerfilPermissao;
import com.feeney.daniel.notify.model.Permissao;

@Repository
public interface PerfilPermissaoRepository extends JpaRepository<PerfilPermissao, Long> {

	@Query("Select p from Usuario u "
			+ "join u.perfil p "
			+ "where u.cpf = ?1 "
			+ "and u.ativo = true ")
		Perfil buscarPerfilPorCpf(String cpf);
	
}
