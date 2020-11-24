package com.feeney.daniel.notify.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.feeney.daniel.notify.dto.PublicacaoDTO;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.Tag;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

	@Query("Select distinct p from PublicacaoTag pt"
		+ " join pt.publicacao p"
		+ " join pt.tag t"
		+ " where p.ativo = true"
		+ " and t.id in"
		+ " (select tag.id from Filtros f"
		+ " join f.tag tag"
		+ " join f.usuario usuario"
		+ " where usuario.cpf = ?1"
		+ " and tag.ativo = true)"
		+ " order by p.dataCriacao desc")
	Page<Publicacao> listPublicacaoPelaPreferenciaDoUsuario(String cpf, Pageable pageable);

	
	@Modifying
	@Query("Update Publicacao "
			+ " Set ativo = false "
			+ " where id = ?1")
	void delete(Long id);
	
}
