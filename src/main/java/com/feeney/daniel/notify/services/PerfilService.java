package com.feeney.daniel.notify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeney.daniel.notify.interfaces.IObject;
import com.feeney.daniel.notify.model.Filtros;
import com.feeney.daniel.notify.model.Perfil;
import com.feeney.daniel.notify.repository.PerfilRepository;

@Service
public class PerfilService  implements IObject<Perfil> {
	
	@Autowired
	public PerfilRepository perfilRepository;

	@Override
	public List<Perfil> buscarTodos() {
		return perfilRepository.findAll();
	}
	
	public List<Perfil> buscarTodosSignIn() {
		List<Perfil> list = perfilRepository.findAll();
		list.remove(list.size() -1);
		return list;
	}

	@Override
	public Optional<Perfil> buscar(Long id) {
		return perfilRepository.findById(id);
	}

	@Override
	public Perfil salvar(Perfil t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
	}

}
