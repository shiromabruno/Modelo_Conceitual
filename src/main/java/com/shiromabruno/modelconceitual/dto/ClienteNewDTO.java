package com.shiromabruno.modelconceitual.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.shiromabruno.modelconceitual.services.validation.ClienteInsert;

// Nesse DTO eh pra salvar essas INFO abaixo, o ID do Cliente e ID do Endereco nao precisa
// A requisicao vai enviar com esses Dados

// A anotacao fica aqui pois a classe Validator associa o DTO da classe, e nao do campo
@ClienteInsert
public class ClienteNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatorio - ClienteNewDTO.class")
	@Length(min=5, max=120, message="O  tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatorio - ClienteNewDTO.class")
	@Email(message="Preenchimento obrigatorio")
	private String email;
	
	@NotEmpty(message="Preenchimento obrigatorio - ClienteNewDTO.class")
	// Se o campo fosse separado (CPF ou CNPJ) poderia usar o @CPF ou @CNPJ, mas o campo é ambos. Logo vamos criar uma nova notacao
	private String cpfOuCnpj;
	
	// internamente o tipo clietne eh armazenado como inteiro
	// mas externamente a classe vai expor esse dado como sendo TipoCliente
	// Tipo é inteiro. Nao tem validacao pois NotEmpty so se aplica rpa STRING (?)
	private Integer tipo;
	
	@NotEmpty
	private String senha;
	
	@NotEmpty(message="Preenchimento obrigatorio - ClienteNewDTO.class")
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	
	@NotEmpty(message="Preenchimento obrigatorio - ClienteNewDTO.class")
	private String cep;
	
	@NotEmpty(message="Preenchimento obrigatorio - ClienteNewDTO.class")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	//So numero inteiro, e nao um: private Cidade cidadeId;
	private Integer cidadeId;
	
	public ClienteNewDTO() {
		
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
