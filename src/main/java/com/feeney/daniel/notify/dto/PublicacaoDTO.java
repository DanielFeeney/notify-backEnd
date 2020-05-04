package com.feeney.daniel.notify.dto;

import java.util.Collection;
import java.util.Date;

import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.Tag;

public class PublicacaoDTO {

	private Long id;
	private String titulo;
	private String subTitulo;
	private String descricao;
	private String imagem;
	private Date dataCriacao;
	private String cpfUsuario;
	private Collection<Tag> colTag;
	
	
	
	public PublicacaoDTO(Publicacao publicacao) {
		this.id = publicacao.getId();
		this.titulo = publicacao.getTitulo();
		this.subTitulo = publicacao.getSubTitulo();
		this.descricao = publicacao.getDescricao();
		this.dataCriacao = publicacao.getDataCriacao();
	}
	
	public PublicacaoDTO() {
		super();
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSubTitulo() {
		return subTitulo;
	}
	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Collection<Tag> getColTag() {
		return colTag;
	}
	public void setColTag(Collection<Tag> colTag) {
		this.colTag = colTag;
	}
	public String getCpfUsuario() {
		return cpfUsuario;
	}
	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}
	
	
	
}
