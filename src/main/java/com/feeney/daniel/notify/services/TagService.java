package com.feeney.daniel.notify.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.repository.TagRepository;

@Service
public class TagService implements IObject<Tag> {
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	public List<Tag> buscarTodos() {
		return tagRepository.findAll();
	}

	@Override
	public Optional<Tag> buscar(Long id) {
		return tagRepository.findById(id);
	}

	@Override
	public Tag salvar(Tag p) {
		return tagRepository.save(p);
	}

	@Override
	public void remover(Long id) {
		tagRepository.deleteById(id);
		
	}
	
	public Collection<TagDTO> listarTagDTO(){
		return tagRepository.colTagDTO();
	}
	
	public Collection<TagDTO> listarTagDTODoUsuario(String cpf){
		return tagRepository.colTagDTODeUsuario(cpf);
	}
	
	public Collection<Tag> listarTagDePublicacao(Long publicacaoId){
		return tagRepository.listTagDePublicacao(publicacaoId);
	}
	
	public Collection<TagDTO> listarTodosTagDTOPublicacao(Long publicacaoId){
		return tagRepository.listTagDTODePublicacao(publicacaoId);
	}

	public Collection<TagDTO> listarTodosTagDTOETodosTagDTODePublicacao(Long idPublicacao) {
		return tagRepository.listarTodosTagDTOETodosTagDTODePublicacao(idPublicacao);
	}

}
