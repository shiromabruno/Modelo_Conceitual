package com.shiromabruno.modelconceitual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.domain.Produto;
import com.shiromabruno.modelconceitual.repositories.CategoriaRepository;
import com.shiromabruno.modelconceitual.repositories.ProdutoRepository;
import com.shiromabruno.modelconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
//	public Produto buscar(Integer id) {
//		Optional<Produto> obj = repo.findById(id);  
//		return obj.orElse(null); 
//}		
		public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);  
	    // () lambda funcao q nao recebe nenhum argumento e retorna o object not found
		// recebe uma funcao que instancia uma excecao
		  return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName())); 
		}
		
		//Dado o nome do produto, ver quais categorias ele pertence 
		public Page<Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			List<Categoria> categorias = categoriaRepository.findAllById(ids); // --> retorna busca todas as categorias correspondente aos IDS que estiverem nessa ids
			return repo.search(nome, categorias, pageRequest);
			
			//Poderia ser o de baixo tambem (esta implementado no REPOSITORY)
			//Verificar lista de Nomes Reservados para usar esse Find...(site docs.spring.io - jpa.query-methods)
			//return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		}
	}

