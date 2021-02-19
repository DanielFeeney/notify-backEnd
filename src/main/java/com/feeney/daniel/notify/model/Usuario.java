package com.feeney.daniel.notify.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feeney.daniel.notify.dto.UsuarioDTO;

@Entity
@Table(name = "usuario")
@SQLDelete(sql="UPDATE usuario SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Usuario{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cpf;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "dt_nascimento")
	private Date dtNascimento;

	private String senha;
	
	@ManyToOne
	@JoinColumn(name =  "id_perfil", columnDefinition = "bigint")
	private Perfil perfil;
	private String nome;
	private String foto;
	private String email;
	private Boolean msg;
	
	@Column(name = "fcm_token")
	private String fcmToken;
	
	private Boolean ativo;
	
	

	public Usuario() {
	}
	
	
	
	public Usuario setUsuario(UsuarioDTO usuarioDTO) {
		id = usuarioDTO.getId();
		nome = usuarioDTO.getNome();
		email = usuarioDTO.getEmail();
		cpf = usuarioDTO.getCpf();
		dtNascimento = set3horas(usuarioDTO.getDataNascimento());		
		ativo = true;		
		return this;
	}



	private Date set3horas(Date dataNascimento) {
		Calendar data = Calendar.getInstance();
		data.setTime(dataNascimento);
		data.set(Calendar.HOUR, data.get(Calendar.HOUR) + 3);
		return data.getTime();
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Boolean getMsg() {
		return msg;
	}
	public void setMsg(Boolean msg) {
		this.msg = msg;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
