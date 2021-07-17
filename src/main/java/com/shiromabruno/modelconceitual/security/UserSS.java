package com.shiromabruno.modelconceitual.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shiromabruno.modelconceitual.domain.enums.Perfil;

//classe usuario que atende o Spring Security
public class UserSS implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS() {
		
	}
	
	//trocamos de ao inves de passar por parametro o Collection<? extends GrantedAuthority>, vai passar um Set<Perfis>
	//tera que o conjunto de perfis para o conjunto Collection<? extends GrantedAuthority>
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	//retorna um colecao do tipo GrantedAuthority. Que sao os perfis do usuario
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	//A conta NAO esta expirada?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//A conta NAO esta bloqueada?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//A credencial nao esta expirada?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//Esta habilitado?
	@Override
	public boolean isEnabled() {
		return true;
	}

}
