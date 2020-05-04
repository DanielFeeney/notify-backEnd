package com.feeney.daniel.notify.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
	
	public List<Publicacao> buscarTodosPelaPreferenciaDoUsuario(String cpf, int page, int linesPerPage) {
		PageRequest pageRequest = PageRequest.of(page,linesPerPage);
		return publicacaoRepository.listPublicacaoPelaPreferenciaDoUsuario(cpf, pageRequest).getContent();
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
	public void remover(Long id) {
		publicacaoRepository.deleteById(id);
		
	}

}
