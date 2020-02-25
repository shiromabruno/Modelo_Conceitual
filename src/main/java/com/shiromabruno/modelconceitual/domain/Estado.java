package com.shiromabruno.modelconceitual.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Estado implements Serializable{
	//Serializable = indica que os OBJs desta classe pode ser convertido em sequencia de Bytes
	//para que OBJs possam ser gravados em arquivos, trafegar em redes... etc
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	
	// esse comando eh pra fazer referencia ciclica QUE PÀRA AQUI, ou seja, nao ira chamad cidade, que por sua vez
	// chamaria estado, que por sua vez chamaria cidade... e etc num loop
	// UPDATE ultima aula: JsonManagedReference e JsonBackReference estava dando problema na hora da requisicao
		// no envio de dados Json em requisicao.
		//Solucao: tirar JsonManagedReference. No JsonBackReference, tirar ele e colocar o JsonIgnore
//	@JsonBackReference
	@JsonIgnore
	//aqui seriam os relacionamentos / coleções
	//Estado possui uma lista de cidades
	// quem foi o atributo que mapeou do lado de la ?? na classe cidade o nome do atributo foi "estado"
	//JPA ORM
	@OneToMany(mappedBy="estado")
	private List<Cidade> cidades = new ArrayList<>();
	
	public Estado() {
		
	}

	//criando o construtor sem A COLEÇÂO (a LISTA de cidades)
	public Estado(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	
	// [hashCode e o Equals] comparar por conteudo um estado com o outro
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
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
