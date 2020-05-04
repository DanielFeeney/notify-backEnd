package com.feeney.daniel.notify.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Permissao;
import com.feeney.daniel.notify.repository.PermissaoRepository;

@Service
public class PermissaoService implements IObject<Permissao> {
	
	@Autowired public PermissaoRepository permissaoRepository;

	@Override
	public List<Permissao> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Permissao> buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permissao salvar(Permissao t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	public Set<Permissao> buscarPermissoesPorPerfil(Long idPerfil){
		return permissaoRepository.buscarPermissoesPorPerfil(idPerfil);
	}

}
