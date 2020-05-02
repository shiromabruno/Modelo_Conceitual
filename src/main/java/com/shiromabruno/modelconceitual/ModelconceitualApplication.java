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
    
	/*
	 * JPQL eh linguagem de consulta da JPA (que por sua vez eh biblioteca padrao de mapeamento-objeto-relacional do Java
	 * Eh obrigatorio ALIAS, exemplo:
	 * 
	 * select * from Cliente --> retorna um resultset, um resultado em forma de tabela com dados da table cliente
	 * select obj from Cliente (classe do projeto, nao eh a Tabela) obj --> retorna um List<Cliente>
	 * JPQL trabalha em nivel de objeto e nao em nivel de tabela
	 * 
	 * Relacionamento (Produto x Categoria) (muitos pra 1)
	 * Produto tera a chave estrangeira Categoria
	 * 
	 * No modelo orientado a objeto (Produto x Categoria) (muitos pra 1)
	 * Nao tem id de categoria em produto, e sim composicao de objeto
	 * O produto tera um atributo de nome categoria que sera do tipo categoria
	 * E em categoria tera um atributo chamado produto que sera tipo de colecao de produtos
	 * 
	 * Exemplo: buscando Produto pelo Id
	 * 
	 * Select * from produto where categoria_id = 1
	 * Select obj from Produto obj where obj.categoria.id = 1
	 * 
	 * Exemplo 2: buscando Produto pelo Nome Informatica
	 * 
	 * Select Produto.*  --> todos os campos da tabela produto
	 * From Produto, Categoria
	 * Where Produto.Categoria_id = Categoria.ID and
	 * Categoria.Nome = 'Informatica'
	 * 
	 * ou
	 * 
	 * Select Produto.*
	 * From Produto
	 * Inner join Categoria cat
	 * on Produto.categoria_id = cat.id
	 * where
	 * cat.nome = 'Informatica'
	 * 
	 * Select obj from Produto obj Where obj.categoria.nome = 'Informatica'
	 * 
	 * ---------------------------------------------------------------------------------
	 * 
	 * Caso de uso: buscar os produtos de uma determinada categoria, onde o nome do produto comeca com uam string
	 * 
	 * Select distinct *       --> pegar produtos sem repetições
	 * inner join
	 * Produto_categoria cat1
	 * on produto.id = cat.produto_id
	 * innet join
	 * Categoria cat2
	 * on cat1.categoria_id = cat2.id
	 * where
	 * Produto.nome like ? and  --> esse ? sera usbtituido por uma string
	 * cat2.Id in (?, ?)        --> serao substituido por numeros inteiros
	 * 
	 * Select Distinct obj
	 * From Produto obj
	 * Inner join obj.categorias cat
	 * Where
	 * obj.nome like %:nome% and    --> sera substituindo por uma string
	 * cat in :categorias           --> sera substituido por um List<Categoria>
	 * 
	 * 
	 */
	
	
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
		Produto p4 = new Produto(null, "Mesa de escritorio", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Rocadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		//esta adicionando na lista de produtos da CAtegoria, os produtos computador, impressora e mouse
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		//esta adicionando na lista de categorias do Produto, as categorias Informatica e escritorio
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7)); 
		produtorepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		
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
