package com.shiromabruno.modelconceitual.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiromabruno.modelconceitual.domain.Produto;
import com.shiromabruno.modelconceitual.dto.ProdutoDTO;
import com.shiromabruno.modelconceitual.resources.utils.URL;
import com.shiromabruno.modelconceitual.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// public ResponseEntity<?> find(@PathVariable Integer id) {
	// Trocou de "?" para Produto, pois se nao trazer nada, dá a exceção
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
// ? significa que pode trazer ou nao o obj		
		Produto obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}

	// path variable significa: ... produtos/computador/1,2,3,4
	// porem, RequestParm significa: ... ?mome=computador&categorias=1,3,4
	//@RequestParm --> aparece na URL esse campo igual a alguma coisa (nome=computador)
	//Eh tudo STRING os campos de uma URL (nome e categorias) 
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		// Metodo para eliminar caracteres especiais de um string NOME
		//Exemplo: Bruno Shiroma --> Bruno%20Shiroma
		String nomeDecoded = URL.decodeParam(nome);
		
		//Lista Integer de ids sem o , (1,2,3...)
		List<Integer> ids = URL.decodeIntList(categorias);

		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		// map, uma operacao pra cada elemento da lista. Cada elemento da lista dei o
		// nome de OBJ1
		// e pra cada elemento da lista passa como argumento
		// precisa retornar o map(obj -> new CategoriaDTO(obj)) para o tipo page
		Page<ProdutoDTO> listDto = list.map(obj1 -> new ProdutoDTO(obj1));
		// esse comando acima converteu uma lista para outra lista
		return ResponseEntity.ok().body(listDto);
	}
}
