package com.feeney.daniel.notify.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Favoritos;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.repository.FavoritosRepository;

@Service
public class FavoritosService implements IObject<Favoritos> {
	
	@Autowired
	private FavoritosRepository favoritosRepository;

	@Override
	public List<Favoritos> buscarTodos() {
		return favoritosRepository.findAll();
	}

	@Override
	public Optional<Favoritos> buscar(Long id) {
		return favoritosRepository.findById(id);
	}

	@Override
	public Favoritos salvar(Favoritos p) {
		return favoritosRepository.save(p);
	}

	@Override
	public void remover(Long id) {
		favoritosRepository.deleteById(id);
		
	}
	
	public Collection<Publicacao> buscarPublicacoes(String cpf){
		return favoritosRepository.getPublicacaoByIdUsuario(cpf);
	}
	
	public Optional<Favoritos> buscarPorUsuarioEPublicacao(String cpf, Long idPublicacao) {
		return favoritosRepository.getFavoritoByIdUsuarioAndIdPublicacao(cpf, idPublicacao);
	}

}
