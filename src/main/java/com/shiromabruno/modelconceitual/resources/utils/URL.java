package com.shiromabruno.modelconceitual.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

// classe que ira pegar da requisicao @RequestParm categorias=1,2,3 e converter em lista de numeros Inteiros
public class URL {

	
	// Metodo para eliminar caracteres especiais de um string NOME
	//Exemplo: Bruno Shiroma --> Bruno%20Shiroma
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	
	// static pois irei chamar esse metodo sem instanciar
	// pegar a string e quebrar em numeros e jogar na List
	public static List<Integer> decodeIntList(String s){
		
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		// poderia ser assim:
		// stream, percorrer a lista
        // map, uma operacao pra cada elemento da lista. Cada elemento da lista dei o nome de OBJ1
        // e pra cada elemento da lista passa como argumento
        // precisa retornar esse stream stream().map(obj -> new ClienteDTO(obj)) para o tipo lista
        // e pra isso usa o Collectors.toList()
		// return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
}
