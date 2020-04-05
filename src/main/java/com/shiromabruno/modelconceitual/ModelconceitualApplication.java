package com.shiromabruno.modelconceitual;

import java.text.SimpleDateFormat;
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
import com.shiromabruno.modelconceitual.domain.ItemPedido;
import com.shiromabruno.modelconceitual.domain.Pagamento;
import com.shiromabruno.modelconceitual.domain.PagamentoComBoleto;
import com.shiromabruno.modelconceitual.domain.PagamentoComCartao;
import com.shiromabruno.modelconceitual.domain.Pedido;
import com.shiromabruno.modelconceitual.domain.Produto;
import com.shiromabruno.modelconceitual.domain.enums.EstadoPagamento;
import com.shiromabruno.modelconceitual.domain.enums.TipoCliente;
import com.shiromabruno.modelconceitual.repositories.CategoriaRepository;
import com.shiromabruno.modelconceitual.repositories.CidadeRepository;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.repositories.EnderecoRepository;
import com.shiromabruno.modelconceitual.repositories.EstadoRepository;
import com.shiromabruno.modelconceitual.repositories.ItemPedidoRepository;
import com.shiromabruno.modelconceitual.repositories.PagamentoRepository;
import com.shiromabruno.modelconceitual.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidorepository;
	@Autowired
	private PagamentoRepository pagamentorepository;
	@Autowired
	private ItemPedidoRepository itempedidorepository;
	
	public static void main(String[] args) {	
		SpringApplication.run(ModelconceitualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// id vc deixa NULL pois eh o banco quem cria
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoracao");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
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
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7)); 
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("30/09/2017 00:47"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
				
		pedidorepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentorepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itempedidorepository.saveAll(Arrays.asList(ip1,ip2,ip3));

	}

}
