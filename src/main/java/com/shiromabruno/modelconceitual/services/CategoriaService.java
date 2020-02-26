package com.shiromabruno.modelconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.repositories.CategoriaRepository;
import com.shiromabruno.modelconceitual.services.exceptions.DataIntegrityExceptionYuji;
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
		
		public Categoria update(Categoria obj) {
			// comando abaixo garante que o ID eh nullo. Se nao fosse nulo, significa que seria updte
			// o  id eh IDENTITY
			// obj.setId(null); --> nao precisa pois se tiver esse comando, significa que eh INSERT
			// vai retornar o objeto Categoria 
			
			find(obj.getId());
			return repo.save(obj);
		}
		
		// se vc deletar uma categoria que ja existem PRODUTOS associados a ela, dará erro.
		// 2 solucoes. a primeira eh deletar os PRODUTOS tbm, a segunda eh ABORTAR a deleção dessa categoria = BAD REQUEST
		public void delete(Integer id) {
			// se o find nao achar, ja dispara a exceção
			find(id);
			
			try {
			repo.deleteById(id);
			}
			catch (DataIntegrityViolationException e) {
				throw new DataIntegrityExceptionYuji("Nao eh possivel Excluir Categoria que possui Produtos");
				
			}
		}
	}

