package com.shiromabruno.modelconceitual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.domain.Cidade;
import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.domain.Endereco;
import com.shiromabruno.modelconceitual.domain.Estado;
import com.shiromabruno.modelconceitual.domain.Produto;
import com.shiromabruno.modelconceitual.domain.enums.TipoCliente;
import com.shiromabruno.modelconceitual.repositories.CategoriaRepository;
import com.shiromabruno.modelconceitual.repositories.CidadeRepository;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.repositories.EnderecoRepository;
import com.shiromabruno.modelconceitual.repositories.EstadoRepository;
import com.shiromabruno.modelconceitual.repositories.ProdutoRepository;

@SpringBootApplication
//public class ModelconceitualApplication {
public class ModelconceitualApplication implements CommandLineRunner{
    
	@Autowired
	private CategoriaRepository categoriarepository;
	@Autowired
	private ProdutoRepository produtorepository;
	@Autowired
	private EstadoRepository estadorepository;
	@Autowired
	private CidadeRepository cidaderepository;
	@Autowired
	private ClienteRepository clienterepository;
	@Autowired
	private EnderecoRepository enderecorepository;
	
	public static void main(String[] args) {	
		SpringApplication.run(ModelconceitualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// id vc deixa NULL pois eh o banco quem cria
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//esta adicionando na lista de produtos da CAtegoria, os produtos computador, impressora e mouse
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//esta adicionando na lista de categorias do Produto, as categorias Informatica e escritorio
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2)); 
		produtorepository.saveAll(Arrays.asList(p1, p2, p3));
		
		
		// id vc deixa NULL pois eh o banco quem cria
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
				
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
				
		// esta adicionando na lista de cidades do estado de MG, a cidade de uberlandia
		// o interessante eh que vc seta a lista de cidades com GET...
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadorepository.saveAll(Arrays.asList(est1, est2));
		cidaderepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678901", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27334421","927334421"));
		
		Endereco e1 = new Endereco(null, "Rua flores", "300", "Apt 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		clienterepository.saveAll(Arrays.asList(cli1));
		enderecorepository.saveAll(Arrays.asList(e1,e2));
	   
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
	}

}
