package com.feeney.daniel.notify.dto;

public class TagDTO {
	private Long id;
	private String descricao;
	private Boolean selecionado;
	private String cpfUsuario;
	
	public TagDTO(Long id, String descricao, Boolean selecionado) {
		this.id = id;
		this.descricao = descricao;
		this.selecionado = selecionado;
	}
	
	public TagDTO(Long id, String descricao, String cpfUsuario, Boolean selecionado) {
		this.id = id;
		this.descricao = descricao;
		this.cpfUsuario = cpfUsuario;
		this.selecionado = selecionado;
	}	
	
	public TagDTO() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Boolean getSelecionado() {
		return selecionado;
	}
	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	
	
	
}
