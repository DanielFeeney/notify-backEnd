package com.feeney.daniel.notify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "publicacao_tag")
public class PublicacaoTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_publicacao", columnDefinition = "bigint")
	private Publicacao publicacao;
	
	@ManyToOne
	@JoinColumn(name = "id_tag", columnDefinition = "bigint")
	private Tag tag;	
	
	public PublicacaoTag() {
		super();
	}
	
	public PublicacaoTag(Publicacao publicacao, Tag tag) {
		this.publicacao = publicacao;
		this.tag = tag;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
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
		PublicacaoTag other = (PublicacaoTag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
