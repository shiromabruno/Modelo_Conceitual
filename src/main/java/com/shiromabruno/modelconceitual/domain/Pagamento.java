package com.shiromabruno.modelconceitual.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shiromabruno.modelconceitual.domain.enums.EstadoPagamento;


// tem o modo tabela unica ou o JOINED
// tabela unica = cada instancia eh a fusao das 2 classes (exemplo pgt com cartao + pgt com boleto, ai se for
// pgt com cartao, os campos de pgt com boleto fica NULL, e vise versa
// no JOINED = tem q fazer JOIN e no caso havera 2 tabelas (ou seria 3 ?)
//Abstract pra garantir que o PAGAMENTO nao seja instanciado, e sim suas classes herdeiras

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Pagamento implements Serializable{
	//Serializable = indica que os OBJs desta classe pode ser convertido em sequencia de Bytes
	//para que OBJs possam ser gravados em arquivos, trafegar em redes... etc
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer estado;
	
	// back reference com Pedido. Entao essa classe pagamento nao vai serializar (trazer) os pedidos associados
	// aqui 1 pedido tem 1 pagamento. O que unirá os dois sera o PEDIDO ID
	// para garantir que o ID do pagamento seja o mesmo do ID do pedido, deve usar @MapsId
	// UPDATE ultima aula: JsonManagedReference e JsonBackReference estava dando problema na hora da requisicao
		// no envio de dados Json em requisicao.
		//Solucao: tirar JsonManagedReference. No JsonBackReference, tirar ele e colocar o JsonIgnore
	//@JsonBackReference
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	private Pedido pedido;
	
	public Pagamento() {
		
	}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = (estado == null) ? null : estado.getCod();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
