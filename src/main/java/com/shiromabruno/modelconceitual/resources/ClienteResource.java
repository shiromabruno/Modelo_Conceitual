package com.shiromabruno.modelconceitual.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.dto.ClienteDTO;
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@ Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
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
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		// precisa converter o comando abaixo para ClienteDTO	
		
		        List <Cliente> list = service.findAll();
		        // stream, percorrer a lista
		        // map, uma operacao pra cada elemento da lista. Cada elemento da lista dei o nome de OBJ1
		        // e pra cada elemento da lista passa como argumento
		        // precisa retornar esse stream stream().map(obj -> new ClienteDTO(obj)) para o tipo lista
		        // e pra isso usa o Collectors.toList()
		        List <ClienteDTO> listDto = list.stream().map(obj1 -> new ClienteDTO(obj1)).collect(Collectors.toList());
		        // esse comando acima converteu uma lista para outra lista
				return ResponseEntity.ok().body(listDto);
			}
	
	// path variable significa: ... categoria/page/8/20/campoX/ascendente
	// sera por parametro: ?page=0&linesPerPage=20&orderBy=campoX...
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		// precisa converter o comando abaixo para ClienteDTO	
		
		        Page <Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		        // map, uma operacao pra cada elemento da lista. Cada elemento da lista dei o nome de OBJ1
		        // e pra cada elemento da lista passa como argumento
		        // precisa retornar o map(obj -> new ClienteDTO(obj)) para o tipo page
		        Page <ClienteDTO> listDto = list.map(obj1 -> new ClienteDTO(obj1));
		        // esse comando acima converteu uma lista para outra lista
				return ResponseEntity.ok().body(listDto);
			}
}
