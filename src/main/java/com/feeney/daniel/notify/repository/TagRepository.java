package com.feeney.daniel.notify.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
				+ "AND u.cpf = ?1 "
				+ "and u.ativo = true "
				+ "AND t2.ativo = true) = 1 "
				+ "THEN TRUE "
				+ "ELSE FALSE "
				+ "END ) "
			+ " from Tag t"
			+ " where t.ativo = true")
	Collection<TagDTO> colTagDTODeUsuario(String cpf);

	
	@Query("Select t from Tag t "
			+ "where t.id in "
			+ "(Select t2.id from PublicacaoTag pt "
			+ "join pt.publicacao p "
			+ "join pt.tag t2 "
			+ "where p.id = ?1)"
			+ " and t.ativo = true")
	Collection<Tag> listTagDePublicacao(Long publicacaoId);
	
	@Query("Select new com.feeney.daniel.notify.dto.TagDTO(t.id, t.descricao, true) from Tag t "
			+ "where t.id in "
			+ "(Select t2.id from PublicacaoTag pt "
			+ "join pt.publicacao p "
			+ "join pt.tag t2 "
			+ "where p.id = ?1)"
			+ " and t.ativo = true")
	Collection<TagDTO> listTagDTODePublicacao(Long publicacaoId);
	
	@Query("Select new com.feeney.daniel.notify.dto.TagDTO(t.id, t.descricao, "
			+ "case when "
			+ " (Select COUNT(t2.id) from Tag t2 "
			+ " where t2.id in "
			+ " (Select t3.id from PublicacaoTag pt " 
			+ " join pt.publicacao p " 
			+ " join pt.tag t3 " 
			+ " where p.id = ?1"
			+ " ) "
			+ " AND t2.id = t.id"
			+ " ) > 0 "
			+ " THEN true "
			+ " ELSE false "
			+ " END"
			+ ") from Tag t "
			+ " where t.ativo = true")
	Collection<TagDTO> listarTodosTagDTOETodosTagDTODePublicacao(Long publicacaoId);
	
	@Query("Select new com.feeney.daniel.notify.dto.TagDTO(t.id, t.descricao, false)"
			+ " from Tag t"
			+ " where t.ativo = true")
	Collection<TagDTO> colTagDTO();
	
	
	@Modifying
	@Query("Update Tag "
			+ " Set ativo = false "
			+ " where id = ?1")
	void delete(Long id);
}
