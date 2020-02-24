package com.shiromabruno.modelconceitual.domain.enums;

public enum TipoCliente {
	
	//atribui cod 1 para PESSOAFISICA, aparece (onde?) "Pessoa Fisica"
	PESSOAFISICA(1, "Pessoa Fisica"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
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
	public static TipoCliente toEnum(Integer cod) {
		if (cod ==null) {
			return null;
		}
		// busca de todo objeto X nos valores possiveis do TipoCliente
		// se buscar com '1', entao o metodo retorna objeto PESSOAFISICA
		for (TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
}
