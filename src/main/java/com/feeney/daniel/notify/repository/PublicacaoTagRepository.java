package com.feeney.daniel.notify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feeney.daniel.notify.model.PublicacaoTag;

@Repository
public interface PublicacaoTagRepository extends JpaRepository<PublicacaoTag, Long> {

}
