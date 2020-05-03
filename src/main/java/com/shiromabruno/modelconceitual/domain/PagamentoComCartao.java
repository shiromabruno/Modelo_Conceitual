package com.shiromabruno.modelconceitual.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.shiromabruno.modelconceitual.domain.enums.EstadoPagamento;

@Entity
//A notacao abaixo indica o Type que sera passado na requisicao. Se vier com o nome abaixo, eh criado um objeto dessa classe
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	
	//Classe que extende de uma PAI, nao precisa colocar IMPLEMENTS SERIALIZEBLE
	private static final long serialVersionUID = 1L;
	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
		// TODO Auto-generated constructor stub
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
