package com.shiromabruno.modelconceitual.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.shiromabruno.modelconceitual.domain.Cliente;

public class ClienteDTO implements Serializable{
	
	private static final long serialVersionUID = 1;
	
	//essas 3 coisas ele pode mudar
	private Integer id;
	@NotEmpty(message = "Preenchimento obrigatorio - ClienteDTO.class")
	@Length(min=5, max =120, message="Tamanho do nome de 5 ate 120 - ClienteDTO.class")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatorio - ClienteDTO.class")
	@Email(message = "Email invalido - ClienteDTO.class")
	private String email;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente obj) {
		//new ClienteDTO(obj.getId(), obj.getNome(), obj.getEmail());
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	

}
