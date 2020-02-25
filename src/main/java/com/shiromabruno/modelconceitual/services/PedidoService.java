package com.shiromabruno.modelconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.Pedido;
import com.shiromabruno.modelconceitual.repositories.PedidoRepository;
import com.shiromabruno.modelconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
//	public Pedido buscar(Integer id) {
//		Optional<Pedido> obj = repo.findById(id);  
//		return obj.orElse(null); 
//}		
		public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);  
	    // () lambda funcao q nao recebe nenhum argumento e retorna o object not found
		// recebe uma funcao que instancia uma excecao
		  return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		}
	}

