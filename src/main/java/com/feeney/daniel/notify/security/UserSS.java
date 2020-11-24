package com.feeney.daniel.notify.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.feeney.daniel.notify.model.Permissao;

public class UserSS implements UserDetails {
	
	private Long id;
	private Date dtNascimento;
	private String cpf;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public UserSS() {
	}
	
	public UserSS(Long id, Date dtNascimento, String cpf, String senha,
			Set<Permissao> permissoes) {
		this.id = id;
		this.dtNascimento = dtNascimento;		
		this.cpf = cpf;
		this.senha = senha;
		this.authorities = permissoes.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}



	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
