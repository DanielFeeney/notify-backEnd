package com.feeney.daniel.notify.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.websocket.server.PathParam;

import com.feeney.daniel.notify.dto.PublicacaoDTO;

@Entity
@Table(name = "publicacao")
public class Publicacao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String subTitulo;
	private String descricao;
	private String imagem;
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_publicacao", columnDefinition = "bigint")
	private Usuario usuarioPublicacao;
	
	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	public Publicacao() {
		this.ativo = true;
	}
	
	public Publicacao(PublicacaoDTO publicacaoDTO) {
		this.id = publicacaoDTO.getId();
		this.titulo = publicacaoDTO.getTitulo();
		this.subTitulo = publicacaoDTO.getSubTitulo();
		this.descricao = publicacaoDTO.getDescricao();
		this.dataCriacao = publicacaoDTO.getDataCriacao();
		this.ativo = true;
	}
	
	public Publicacao(
			Long idPublicacao,
			String titulo,
			String subTitulo,
			String descricao,
			String cpf) {
		
		this.id = idPublicacao;		
		this.titulo = titulo;
		this.subTitulo = subTitulo;
		this.descricao = descricao;
		this.dataCriacao = new Date();
		this.ativo = true;
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
	public Usuario getUsuarioPublicacao() {
		return usuarioPublicacao;
	}

	public void setUsuarioPublicacao(Usuario usuarioPublicacao) {
		this.usuarioPublicacao = usuarioPublicacao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publicacao other = (Publicacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
