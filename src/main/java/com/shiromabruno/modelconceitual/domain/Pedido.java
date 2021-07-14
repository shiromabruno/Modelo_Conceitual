package com.shiromabruno.modelconceitual.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable{
	//Serializable = indica que os OBJs desta classe pode ser convertido em sequencia de Bytes
	//para que OBJs possam ser gravados em arquivos, trafegar em redes... etc
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	// so vai serializar aqui. Em pagamento nao podera trazer o Pedido. sera q eh pq se fosse, iria trazer uma porrada de pagamento ?
	// muitos pedidos terao 1 Cliente
	// UPDATE ultima aula: JsonManagedReference e JsonBackReference estava dando problema na hora da requisicao
		// no envio de dados Json em requisicao.
		//Solucao: tirar JsonManagedReference. No JsonBackReference, tirar ele e colocar o JsonIgnore
	//@JsonManagedReference
	//isso embaixo eh necessario pra conversar com classe PEDIDO no PEDIDO_ID. Coisa do JPA
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	// so vai serializar aqui. Em Cliente nao podera trazer o Pedido. sera q eh pq se fosse, iria trazer uma porrada de pedido ?
	// muitos pedidos terao 1 Cliente
	// UPDATE ultima aula: JsonManagedReference e JsonBackReference estava dando problema na hora da requisicao
		// no envio de dados Json em requisicao.
		//Solucao: tirar JsonManagedReference. No JsonBackReference, tirar ele e colocar o JsonIgnore
//	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	// muitos pedidos terai 1 endereco
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	@OneToMany(mappedBy="id.pedido")
	// set a garantir q nao item repedito no mesmo pedido
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	public Pedido() {
		
	}

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
	//	this.pagamento = pagamento;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	// Mesmo sem ninguem chamar esse metodo, ele aparece no reotno no POSTMAN...
	// JSON deve reconhecer esse getValorTotal. Talvez tudo que tenha GET, ele traz no Response Entity
	public double getValorTotal() {
		double soma = 0.0;
		for(ItemPedido ip : itens) {
			soma = soma + ip.getSubValor();
		}
		return soma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//toString para transformar o objeto pedido em string e enviar por email. Usou o StringBuilder/StringBuffer mais performatico
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // formatar de 2000.0 para R$ 2.000,00
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); // formatar data Wed Jul 14 17:43:31 BRT 2021 para 14/07/2021 05:48:13
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido numero: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situacao Pagamento: ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\n");
		builder.append("\nDetalhes:\n");
		for(ItemPedido ip: getItens()) {
			builder.append(ip.toString()); // implementado ja na classe ItemPedido, vira na ordem correta
		}
		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}


	
}
