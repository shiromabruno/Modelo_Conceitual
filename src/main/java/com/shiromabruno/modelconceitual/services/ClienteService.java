package com.shiromabruno.modelconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
//	public Cliente buscar(Integer id) {
//		Optional<Cliente> obj = repo.findById(id);  
//		return obj.orElse(null); 
//}		
		public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);  
	    // () lambda funcao q nao recebe nenhum argumento e retorna o object not found
		// recebe uma funcao que instancia uma excecao
		  return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
		  // esse .getName() eh um metodo que volta o caminho do pacote + nome da classe
		}
	}

