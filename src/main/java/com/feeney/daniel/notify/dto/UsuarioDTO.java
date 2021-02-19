package com.feeney.daniel.notify.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feeney.daniel.notify.model.Permissao;

public class UsuarioDTO {

	private Long id;
	private String cpf;
	private String senha;
	private String nome;
	private String email;
	private Long perfilId;
	private String perfil;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dataNascimento;
	
	public UsuarioDTO() {
	}
	
	public UsuarioDTO(String cpf, String senha, Date dataNascimento) {

		this.cpf = cpf;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public UsuarioDTO(Long id, String cpf, String nome, String email, String perfil,
			Date dataNascimento) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
		this.dataNascimento = dataNascimento;
	}
	
	

	public UsuarioDTO(Long id, String cpf, String nome, String email, Long perfilId, Date dataNascimento) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.perfilId = perfilId;
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPerfilId() {
		return perfilId;
	}

	public void setPerfilId(Long perfilId) {
		this.perfilId = perfilId;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
}
