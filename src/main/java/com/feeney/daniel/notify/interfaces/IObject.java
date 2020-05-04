package com.feeney.daniel.notify.interfaces;

import java.util.List;
import java.util.Optional;

public interface IObject<T> {
	List<T> buscarTodos();
	
	Optional<T> buscar(Long id);
	
	T salvar(T t);
	
	void remover(Long id);
}
