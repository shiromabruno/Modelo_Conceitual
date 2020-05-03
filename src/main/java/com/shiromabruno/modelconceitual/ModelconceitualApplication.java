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
	
	public static void main(String[] args) {	
		SpringApplication.run(ModelconceitualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		

	}

}
