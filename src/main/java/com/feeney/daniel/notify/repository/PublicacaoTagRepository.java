package com.feeney.daniel.notify.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.model.PublicacaoTag;

@Repository
public interface PublicacaoTagRepository extends JpaRepository<PublicacaoTag, Long> {

	@Query("Select pt from PublicacaoTag pt"
		+ " join pt.publicacao p"
		+ " join pt.tag t"
		+ " where p.id = ?1"
		+ " and t.id = ?2"
		+ " and t.ativo = true")
	Optional<PublicacaoTag> buscarPorIdPublicacaoIdTag(Long idPublicacao, Long idTag);
}
