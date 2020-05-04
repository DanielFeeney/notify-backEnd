package com.feeney.daniel.notify.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	@Query("Select new com.feeney.daniel.notify.dto.TagDTO(t.id,t.descricao,"
			+ "case when ("
				+ "select count(f.id) from Filtros f "
				+ "join f.usuario u "
				+ "join f.tag t2 "
				+ "where t2.id = t.id "
				+ "AND u.cpf = ?1) = 1 "
				+ "THEN TRUE "
				+ "ELSE FALSE "
				+ "END ) "
			+ " from Tag t")
	Collection<TagDTO> colTagDTODeUsuario(String cpf);

	
	@Query("Select t from Tag t "
			+ "where t.id in "
			+ "(Select t2.id from PublicacaoTag pt "
			+ "join pt.publicacao p "
			+ "join pt.tag t2 "
			+ "where p.id = ?1)")
	Collection<Tag> listTagDePublicacao(Long publicacaoId);
	
	@Query("Select new com.feeney.daniel.notify.dto.TagDTO(t.id, t.descricao, false)"
			+ " from Tag t")
	Collection<TagDTO> colTagDTO();
}
