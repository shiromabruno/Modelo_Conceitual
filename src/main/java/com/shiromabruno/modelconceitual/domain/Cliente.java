package com.shiromabruno.modelconceitual.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shiromabruno.modelconceitual.domain.enums.Perfil;
import com.shiromabruno.modelconceitual.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	//Serializable = indica que os OBJs desta classe pode ser convertido em sequencia de Bytes
	//para que OBJs possam ser gravados em arquivos, trafegar em redes... etc
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//Faz o baco de dados garantir que nao vai ter repeticao nesse campo
	//indica que eh uma chave candidata
    //Porem desse nao jeito nao da pra tratar a excecao e voltar msg personalizada. Dessa forma a excecao eh do banco de dados
	//Nesse cenario, viria um DataIntegrityViolationException
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	//private TipoCliente tipo;

	
	// internamente o tipo clietne eh armazenado como inteiro
	// mas externamente a classe vai expor esse dado como sendo TipoCliente
	// aqui so vai aparecer o primeiro (CLIENTE). Ele pode ter mais um (ADMIN) mas no banco de dados CLIENTE so aparece 1.
	// No banco de dados PERFIS, aparecera os 2 perfis
	private Integer tipo;
	
	@JsonIgnore
	private String senha;
	
	// esse comando eh pra fazer referencia ciclica somente aqui, e nao nos enderecos, senao cliente tem enderecos
	// e esses enderecos tem clientes, que por sua vez tem enderecos, que por sua vez tem clientes...
	// UPDATE ultima aula: JsonManagedReference e JsonBackReference estava dando problema na hora da requisicao
		// no envio de dados Json em requisicao.
		//Solucao: tirar JsonManagedReference. No JsonBackReference, tirar ele e colocar o JsonIgnore
	//@JsonManagedReference
	//esse "cliente" eh o nome do campo que foi mapeado la em endereço
    // o comando cascade=CascadeType.ALL --> quando deletar um CLIENTE, vai deletar tambem TODOS os Enderecos associados a esse CLIENTE
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	//Set eh conjunto q nao aceita repetição
	// Entidade fraca
	@ElementCollection
	@CollectionTable(name="Telefone") //armazena os telefones de TODOS. Exemplo: Codigo_cliente 1 tem Telefone 123123123
	private Set<String> telefones = new HashSet<>();
	
	//Garante que for buscar os CLientes no Banco dedados, vai buscar os PERFIS tambem. EAGER garanta que ambos sejam buscados juntos
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS") //armazena os perfis de TODOS. Exemplo: Codigo_cliente 1 tem Perfil 1
	private Set<Integer> perfis = new HashSet<>(); //so vai gravar o codigo 1 (ROLE_ADMIN) e 2 (ROLE_CLIENTE). So grava INTEGER
	
	// o Pedido do cliente nao va ser Serializado
	// sera q eh pq se fosse, iria trazer uma porrada de pedido ?
	// UPDATE ultima aula: JsonManagedReference e JsonBackReference estava dando problema na hora da requisicao
		// no envio de dados Json em requisicao.
		//Solucao: tirar JsonManagedReference. No JsonBackReference, tirar ele e colocar o JsonIgnore
	//@JsonBackReference
	@JsonIgnore	
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() { //regra de negocio nossa onde: toda pessoa que esta nessa entidade eh CLIENTE. Algumas delas serao ADMIN
		addPerfil(Perfil.CLIENTE);
	}

	// gerando construtor com tudo que NAO EH COLECAO. Nao precisa criar cliente e dps ficar dando SET cada atributo
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE); //regra de negocio nossa onde: toda pessoa que esta nessa entidade eh CLIENTE. Algumas delas serao ADMIN
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
	//return tipo;
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<Perfil> getPerfis(){ //precisa converter o Numerico para o Perfil equivalente (resgata o INTEGER da tabela)
		//pra cada X [instancia de Perfil] fazer um mapeamento trocando Integer pelo ROLE_bla. Em seguida transforma em SET (Set<Integer>)
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());   //private Set<Integer> perfis = new HashSet<>();
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod()); //armazena so Integer, entao captura o Integer e grava (add)
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
