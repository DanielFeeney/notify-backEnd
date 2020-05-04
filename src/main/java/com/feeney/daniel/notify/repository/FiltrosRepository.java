package com.feeney.daniel.notify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.Filtros;


@Repository
public interface FiltrosRepository extends JpaRepository<Filtros, Long> {

	@Query("Select f from Filtros f"
		+ " join f.tag t "
		+ " join f.usuario u "
		+ " where t.id = ?2"
		+ " and u.cpf = ?1")
	Optional<Filtros> buscarFiltroPorUsuarioETag(String cpf, Long idTag);
}
