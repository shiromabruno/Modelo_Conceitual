package com.shiromabruno.modelconceitual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiromabruno.modelconceitual.domain.ItemPedido;
import com.shiromabruno.modelconceitual.domain.PagamentoComBoleto;
import com.shiromabruno.modelconceitual.domain.Pedido;
import com.shiromabruno.modelconceitual.domain.enums.EstadoPagamento;
import com.shiromabruno.modelconceitual.repositories.ItemPedidoRepository;
import com.shiromabruno.modelconceitual.repositories.PagamentoRepository;
import com.shiromabruno.modelconceitual.repositories.PedidoRepository;
import com.shiromabruno.modelconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
//	public Pedido buscar(Integer id) {
//		Optional<Pedido> obj = repo.findById(id);  
//		return obj.orElse(null); 
//}		
		public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);  
	    // () lambda funcao q nao recebe nenhum argumento e retorna o object not found
		// recebe uma funcao que instancia uma excecao
		  return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		}
		
		// Colocou transactional para garantir que salva todas as informações na mesma transação
		@Transactional
		public Pedido insert(Pedido obj){
			//garantir que estou inserindo um novo pedido
			obj.setId(null);
			obj.setInstante(new Date());
			// Teoria: obj Pedido retorna um obj Pagamento que por sua vez seta seu atributo Estado de pagamento para PENDENTE
			// Porem, esse obj Pagamento nem foi instanciado...
			obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
			obj.getPagamento().setPedido(obj);
			if(obj.getPagamento() instanceof PagamentoComBoleto) {
				//cast garante que com certeza o pgto eh um tipo PagamentoComBoleto
				PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
				// esse service preenchera nesse PGTO a data de vencimento (calculado em instante +7 dias)
				boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
			}
			//salvar Pedido no banco
			obj = repo.save(obj);
			//salvar Pagamento
			pagamentoRepository.save(obj.getPagamento());
			//Salvar Itens de Pedido
			for(ItemPedido ip  : obj.getItens()) {  // obj.getItens() retorna --> Set<ItemPedido>
				ip.setDesconto(0.0);
				ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
				ip.setPedido(obj);
			}
			itemPedidoRepository.saveAll(obj.getItens());
			return obj;
		}
	}

