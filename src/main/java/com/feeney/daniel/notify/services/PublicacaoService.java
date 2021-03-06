package com.feeney.daniel.notify.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.repository.PublicacaoRepository;

@Service
public class PublicacaoService implements IObject<Publicacao> {
	
	@Autowired
	private PublicacaoRepository publicacaoRepository;

	@Override
	public List<Publicacao> buscarTodos() {
		return publicacaoRepository.findAll();
	}
	
	public List<Publicacao> buscarTodosPelaPreferenciaDoUsuario(List<Long> filtros, int page, int linesPerPage) {
		PageRequest pageRequest = PageRequest.of(page,linesPerPage);
		if(!filtros.isEmpty()) {
			return publicacaoRepository.listPublicacaoPelaPreferenciaDoUsuario(filtros, pageRequest).getContent();
		}
		return publicacaoRepository.listPublicacao(pageRequest).getContent();
	}

	@Override
	public Optional<Publicacao> buscar(Long id) {
		return publicacaoRepository.findById(id);
	}

	@Override
	public Publicacao salvar(Publicacao p) {
		return publicacaoRepository.save(p);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		publicacaoRepository.delete(id);
		
	}

}
