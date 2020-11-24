package com.feeney.daniel.notify.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Filtros;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.repository.FiltrosRepository;

@Service
public class FiltrosService implements IObject<Filtros> {
	
	@Autowired
	private FiltrosRepository filtrosRepository;

	@Override
	public List<Filtros> buscarTodos() {
		return filtrosRepository.findAll();
	}

	@Override
	public Optional<Filtros> buscar(Long id) {
		return filtrosRepository.findById(id);
	}

	@Override
	public Filtros salvar(Filtros p) {
		return filtrosRepository.save(p);
	}

	@Override
	public void remover(Long id) {
		filtrosRepository.deleteById(id);
		
	}
	
	public Optional<Filtros> buscarPorUsuarioETag(String cpf, Long idTag) {
		return filtrosRepository.buscarFiltroPorUsuarioETag(cpf, idTag);
	}
	
	public Collection<String> buscarTokenDeUsuarioPorTag(Collection<Tag> listTag){
		return filtrosRepository.buscarTokensDeUsuariosPorTag(listTag);
	}

}
