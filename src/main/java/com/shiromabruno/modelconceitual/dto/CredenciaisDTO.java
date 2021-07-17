package com.shiromabruno.modelconceitual.dto;

import java.io.Serializable;

//classe para receber a requisicao com email e senha
public class CredenciaisDTO implements Serializable {
	
	private static final long serialVersionUID = 1;
	
	private String email;
	private String senha;
	
	public CredenciaisDTO() {
		
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
