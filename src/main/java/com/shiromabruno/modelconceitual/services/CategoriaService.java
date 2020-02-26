package com.shiromabruno.modelconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.repositories.CategoriaRepository;
import com.shiromabruno.modelconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
//	public Categoria buscar(Integer id) {
//		Optional<Categoria> obj = repo.findById(id);  
//		return obj.orElse(null); 
//}		
		public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);  
	    // () lambda funcao q nao recebe nenhum argumento e retorna o object not found
		// recebe uma funcao que instancia uma excecao
		  return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		}
		
		public Categoria insert(Categoria obj) {
			// comando abaixo garante que o ID eh nullo. Se nao fosse nulo, significa que seria updte
			// o  id eh IDENTITY
			obj.setId(null);
			// vai retornar o objeto Categoria 
			return repo.save(obj);
		}
	}

