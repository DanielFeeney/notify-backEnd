package com.feeney.daniel.notify.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	
	@Query("Select p from Permissao p "
		+ "where p.id in ("
		+ "select p2.id from PerfilPermissao pp "
		+ "join pp.perfil per "
		+ "join pp.permissao p2 "
		+ "where per.id = ?1)")
	Set<Permissao> buscarPermissoesPorPerfil(Long idPerfil);

}
