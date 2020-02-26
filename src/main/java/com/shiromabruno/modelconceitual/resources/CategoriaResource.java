package com.shiromabruno.modelconceitual.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public List<Categoria> listar() {
//	public ResponseEntity<?> find(@PathVariable Integer id) {
	
	// Trocou de "?" para Categoria, pois se nao trazer nada, dá a exceção
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
// ? significa que pode trazer ou nao o obj		
		Categoria obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
				
/**			
	    Categoria cat1 = new Categoria(1, "Informatica");
	    Categoria cat2 = new Categoria(2, "Escritorio");
	    List<Categoria> lista = new ArrayList<>();
	    lista.add(cat1);
	    lista.add(cat2);
	   	return lista;
	   	
**/
	@RequestMapping(method=RequestMethod.POST)	
	// esse @RequestBody --> o obj categoria sera construido dos dados Json que recebeu. Converte em obj Java Categoria
	public ResponseEntity<Void>	insert(@RequestBody Categoria obj){
	   // esse OBJ abaixo, mantem pois a operacao SAVE do Repository retorna objeto
		obj = service.insert(obj);
		// devemos retornar o URI pra indicar o ID como resposta
		//FromCurrentRequest pega o URI chamado e adiciona o /"{id}"
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		// retornarei um conteudo vazio ?
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
