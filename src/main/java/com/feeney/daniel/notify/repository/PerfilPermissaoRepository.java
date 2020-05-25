package com.feeney.daniel.notify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.PerfilPermissao;

@Repository
public interface PerfilPermissaoRepository extends JpaRepository<PerfilPermissao, Long> {

	
}
