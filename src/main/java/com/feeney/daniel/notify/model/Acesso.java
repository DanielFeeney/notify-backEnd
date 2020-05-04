package com.feeney.daniel.notify.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Inheritance
@Entity
public abstract class Acesso {
	
	@Id
	@GeneratedValue
	protected Long id;
	
	protected Boolean concedido;

	public Acesso(Boolean concedido) {
		this.concedido = true;
	}
	
	

}
