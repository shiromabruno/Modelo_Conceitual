package com.shiromabruno.modelconceitual.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable{
	//Serializable = indica que os OBJs desta classe pode ser convertido em sequencia de Bytes
	//para que OBJs possam ser gravados em arquivos, trafegar em redes... etc
	
	private static final long serialVersionUID = 1L;
	//Significa que a minha classe eh a versao 1
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private double preco;
	
	
	//Aqui o Json vai omitir a lista de categoria pra cada produto, pois ja foi buscado a lista categoria 
	//com os produtos associados na classe Categoria
	@JsonBackReference
	@ManyToMany
	@JoinTable(name="PRODUTO_CATEGORIA",
	joinColumns = @JoinColumn(name = "produto_id"),
	inverseJoinColumns = @JoinColumn (name = "categoria_id")
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
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

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}