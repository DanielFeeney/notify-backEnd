package com.feeney.daniel.notify.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.Favoritos;
import com.feeney.daniel.notify.model.Publicacao;


@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {
	
	@Query("Select p from Favoritos f "
			+ "join f.publicacao p "
			+ "join f.usuario u "
			+ "where u.cpf = ?1 "
			+ "order by p.id")
	Collection<Publicacao> getPublicacaoByIdUsuario(String cpf);
	
	@Query("Select f from Favoritos f "
			+ "join f.publicacao p "
			+ "join f.usuario u "
			+ "where u.cpf = ?1 "
			+ "and p.id = ?2")
	Optional<Favoritos> getFavoritoByIdUsuarioAndIdPublicacao(String cpf, Long idPublicacao);

}
