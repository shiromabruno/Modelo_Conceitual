package com.shiromabruno.modelconceitual.dto;

import java.io.Serializable;

import com.shiromabruno.modelconceitual.domain.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1;

	private Integer id;
	private String nome;
	private double preco;

	public ProdutoDTO() {

	}

	public ProdutoDTO(Produto obj) {
        id = obj.getId();
        nome = obj.getNome();
        preco = obj.getPreco();
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}
