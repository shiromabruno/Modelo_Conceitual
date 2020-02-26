package com.shiromabruno.modelconceitual.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public List<Cliente> listar() {
	//	public ResponseEntity<?> find(@PathVariable Integer id) {
	// Trocou de "?" para Cliente, pois se nao trazer nada, dá a exceção
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
// ? significa que pode trazer ou nao o obj		
		Cliente obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
				
/**			
	    cliente cat1 = new cliente(1, "Informatica");
	    cliente cat2 = new cliente(2, "Escritorio");
	    List<cliente> lista = new ArrayList<>();
	    lista.add(cat1);
	    lista.add(cat2);
	   	return lista;
	   	
**/
	}
}
