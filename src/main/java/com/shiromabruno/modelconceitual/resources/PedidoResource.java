package com.shiromabruno.modelconceitual.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shiromabruno.modelconceitual.domain.Pedido;
import com.shiromabruno.modelconceitual.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public List<Pedido> listar() {
	
	//public ResponseEntity<?> find(@PathVariable Integer id) {
	// Trocou de "?" para Pedido, pois se nao trazer nada, dá a exceção
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
// ? significa que pode trazer ou nao o obj		
		Pedido obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
				
/**			
	    Pedido cat1 = new Pedido(1, "Informatica");
	    Pedido cat2 = new Pedido(2, "Escritorio");
	    List<Pedido> lista = new ArrayList<>();
	    lista.add(cat1);
	    lista.add(cat2);
	   	return lista;
	   	
**/
	}
}
