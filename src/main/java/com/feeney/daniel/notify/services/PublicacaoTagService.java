package com.feeney.daniel.notify.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.PublicacaoTag;
import com.feeney.daniel.notify.repository.PublicacaoRepository;
import com.feeney.daniel.notify.repository.PublicacaoTagRepository;

@Service
public class PublicacaoTagService implements IObject<PublicacaoTag>{
	
	@Autowired PublicacaoTagRepository publicacaoTagRepository;

	@Override
	public List<PublicacaoTag> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PublicacaoTag> buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Optional<PublicacaoTag> buscarPorPublicacaoETag(Long idPublicacao, Long idTag) {
		return publicacaoTagRepository.buscarPorIdPublicacaoIdTag(idPublicacao, idTag);
	}

	@Override
	public PublicacaoTag salvar(PublicacaoTag t) {
		return publicacaoTagRepository.save(t);
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	public void remover(PublicacaoTag publicacaoTag) {
		publicacaoTagRepository.delete(publicacaoTag);		
	}

}
