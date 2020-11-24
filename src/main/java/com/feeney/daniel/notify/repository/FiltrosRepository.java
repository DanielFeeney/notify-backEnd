package com.feeney.daniel.notify.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.Filtros;
import com.feeney.daniel.notify.model.Tag;


@Repository
public interface FiltrosRepository extends JpaRepository<Filtros, Long> {

	@Query("Select f from Filtros f"
		+ " join f.tag t "
		+ " join f.usuario u "
		+ " where t.id = ?2"
		+ " and u.cpf = ?1"
		+ " and t.ativo = true")
	Optional<Filtros> buscarFiltroPorUsuarioETag(String cpf, Long idTag);
	
	@Query("Select distinct u.fcmToken from Filtros f"
		+ " join f.tag t "
		+ " join f.usuario u "
		+ " where t in ?1"
		+ " and u.msg = true")
	Collection<String> buscarTokensDeUsuariosPorTag(Collection<Tag> listTag);
}
