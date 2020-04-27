package com.shiromabruno.modelconceitual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiromabruno.modelconceitual.domain.Cidade;
import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.domain.Endereco;
import com.shiromabruno.modelconceitual.domain.enums.TipoCliente;
import com.shiromabruno.modelconceitual.dto.ClienteDTO;
import com.shiromabruno.modelconceitual.dto.ClienteNewDTO;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.repositories.EnderecoRepository;
import com.shiromabruno.modelconceitual.services.exceptions.DataIntegrityExceptionYuji;
import com.shiromabruno.modelconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
//	public Cliente buscar(Integer id) {
//		Optional<Cliente> obj = repo.findById(id);  
//		return obj.orElse(null); 
//}		
		public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);  
	    // () lambda funcao q nao recebe nenhum argumento e retorna o object not found
		// recebe uma funcao que instancia uma excecao
		  return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
		  // esse .getName() eh um metodo que volta o caminho do pacote + nome da classe
		}
		
		// Colocou transactional para garantir que salva o cliente e seus enderecos na mesma transacao no banco de dados
		// Objetivo eh salvar no REPOSITORY Cliente e REPOSITORY Endereco, tudo junto
		@Transactional
		public Cliente insert(Cliente obj) {
			// comando abaixo garante que o ID eh nullo. Se nao fosse nulo, significa que seria updte
			// o  id eh IDENTITY
			obj.setId(null);
			obj = repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
		}
		
		public Cliente update(Cliente obj) {
			// comando abaixo garante que o ID eh nullo. Se nao fosse nulo, significa que seria updte
			// o  id eh IDENTITY
			// obj.setId(null); --> nao precisa pois se tiver esse comando, significa que eh INSERT
			// vai retornar o objeto Cliente 
			
			//Problema: fizemos put cliente atualizando nome e email (cpf e TipoCliente ja tinha)
			//Ai atualizou registro todo e o cpf e TipoCliente ficou como null (fromDTO metodo la embaixo)
			//Para resolver:
			//instanciar um cliente a partir do banco de dados (newObj)
			//UpdateData --> atualiza os dados desse novo objeto q vc criou com base no objeto que veio no argumento
			//vai chamar UpdateData e fazer update nesse novo objeto com os dados atualizados
			Cliente newObj = find(obj.getId());
			updateData(newObj, obj);
			return repo.save(newObj);
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
				throw new DataIntegrityExceptionYuji("Nao eh possivel Excluir Cliente porque ha entidades relacionadas (Pedido e/ou Endereco)");
				
			}
		}
		
		// a contagem de pagina comeca com 0, linha por pagina, ordenar por o que e ascendente ou descendente
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return repo.findAll(pageRequest);
		}
		
		public List <Cliente> findAll() {
			return repo.findAll();
			
		}
		
		//PUT
		//Criando um objeto Cliente a partir de um ClienteDto
		public Cliente fromDTO(ClienteDTO objDto) {
            // CPF/CNPJ e TipoCliente serao NULL abaixo, pois agora nao iremos alterar CPF/CNPJ e TipoCliente
			return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
			
			// comando abaixo eh temporario, pois implantaremos depois o fromDTO. 
			//throw new UnsupportedOperationException();
		}
		
		//POST
		//Criando um objeto Cliente a partir de um ClienteNewDto
		public Cliente fromDTO(ClienteNewDTO objDto) {
		// o objDto.getTipo() retorna um INTEGER, mas o Cliente aceita um TipoCliente, entao precisa do ToEnum()
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null); // aqui presume que a cidade ja esteja criada ?
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);	
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if(objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
		
		}
		
		// Atualizar newObj com os novos dados que vieram no obj. No caso é o nome e email
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}
		
	}

