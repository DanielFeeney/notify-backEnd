package com.feeney.daniel.notify.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feeney.daniel.notify.model.Permissao;

public class UsuarioDTO {

	private String cpf;
	private String senha;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dataNascimento;
	
	public UsuarioDTO() {
	}
	
	public UsuarioDTO(String cpf, String senha, Date dataNascimento) {

		this.cpf = cpf;
		this.senha = senha;
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
	
}
