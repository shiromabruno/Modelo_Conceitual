package com.shiromabruno.modelconceitual.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.domain.Pedido;
import com.shiromabruno.modelconceitual.dto.CategoriaDTO;
import com.shiromabruno.modelconceitual.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public List<Pedido> listar() {

	// public ResponseEntity<?> find(@PathVariable Integer id) {
	// Trocou de "?" para Pedido, pois se nao trazer nada, dá a exceção
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
// ? significa que pode trazer ou nao o obj		
		Pedido obj = service.find(id);

		return ResponseEntity.ok().body(obj);

		/**
		 * Pedido cat1 = new Pedido(1, "Informatica"); Pedido cat2 = new Pedido(2,
		 * "Escritorio"); List<Pedido> lista = new ArrayList<>(); lista.add(cat1);
		 * lista.add(cat2); return lista;
		 * 
		 **/
	}

	//Iremos fazer um POST sem classe DTO. Poderia usar DTO se quiser
	@RequestMapping(method = RequestMethod.POST)
	// esse @RequestBody --> o obj Pedido sera construido dos dados Json que
	// recebeu. 
	// esse @Valid --> ele valida as validacoes que estao na classe Pedido, se
	// nao passar, nao entra no metodo
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		// esse OBJ abaixo, mantem pois a operacao SAVE do Repository retorna objeto
		obj = service.insert(obj);
		// devemos retornar o URI pra indicar o ID como resposta
		// FromCurrentRequest pega o URI chamado e adiciona o /"{id}"
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
