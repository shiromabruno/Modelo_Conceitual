package com.shiromabruno.modelconceitual.domain.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"), // Precisa estar como ROLE_ --> exigente do SPRING SECURITY !
	CLIENTE(2, "ROLE_CLIENTE"); // Precisa estar como ROLE_ --> exigente do SPRING SECURITY !
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
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
	public static Perfil toEnum(Integer cod) {
		if (cod ==null) {
			return null;
		}
		// busca de todo objeto X nos valores possiveis do TipoCliente
		// se buscar com '1', entao o metodo retorna objeto PESSOAFISICA
		for (Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
}
