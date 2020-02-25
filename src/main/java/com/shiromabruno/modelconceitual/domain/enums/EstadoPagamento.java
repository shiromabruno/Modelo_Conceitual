package com.shiromabruno.modelconceitual.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	//static essa operacao tem q ser possivel mesmo sem instanciar objeto
	public static EstadoPagamento toEnum(Integer cod) {
		if (cod ==null) {
			return null;
		}
		// busca de todo objeto X nos valores possiveis do EstadoPagamento
		// se buscar com '1', entao o metodo retorna objeto PENDENTE
		for (EstadoPagamento x : EstadoPagamento.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
}


