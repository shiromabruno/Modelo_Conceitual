package com.shiromabruno.modelconceitual.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	// esse JSON IGNORE vai ignorar o ITEM PEDIDO, pois so queremos saber os PRODUTOS
	@JsonIgnore
	@OneToMany(mappedBy="id.produto")
	// set a garantir q nao item repedito 
	// esse produto virou um "itempedido" associado a um pedido
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
    
	// o ignore eh necessario , senao vai serializar os pedidos associados ao produto. e tera referecia ciclica
	@JsonIgnore
	// buscar a lista de pedidos o qual este produto foi colocado.
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		// percorrer a lista de ITENS q existe na classe, pra cada Item de pedido X que existir na minha lista de
		// itens, vou adicionar o pedido associado a ele na minha lista
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
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
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
