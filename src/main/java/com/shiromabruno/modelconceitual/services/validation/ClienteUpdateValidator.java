package com.shiromabruno.modelconceitual.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.dto.ClienteDTO;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.services.exceptions.FieldMessage;

//Classe que implementara as regras da Notacao @ClienteUpdate
//ConstraintValidator<ClienteUpdate, Aqui eh o Tipo de dados que vai aceitar essa notacao>
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	// Usado para buscar email do Cliente no repo
	@Autowired
	private ClienteRepository repo;

	// Usado para pegar URI (id do cliente que vira no PUT)
	@Autowired
	private HttpServletRequest request;

	// Aqui poderia colocar alguma programacao de inicializacao
	// Mas nao precisa
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		//Colecao de chaves pares-valor
		//Precisa desse MAP, pois numa requisicao, pode ter varios atributos. Esses atributos sao armazenados num MAP
		//Tipo JSON --> "nome" : "Ciclano"
		//Nesse caso do URI: a chave eh o ID e o valor eh 2
		//O comando pra pegar a URI eh esse HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
		//Necessario fazer um CAST pra converter --> (Map<String, String>), pois o request.get(... eh um OBJETO GENERICO
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		//Precisa do parse pra  para Itnteger (o resultado desse comando eh um STRING --> map.get("Id"))
		Integer uriId = Integer.parseInt(map.get("id"));
		
		// Instancia de lista vazia de FieldMessage
		List<FieldMessage> list = new ArrayList<>();

		// Chama o findByEmail do Repositorio
		Cliente aux = repo.findByEmail(objDto.getEmail());
		//se essa condicao for verdadeira, entao tentou atualizar o cliente contendo um email que ja era de outro cliente
		//Se colocar um email que ja existe, OK se for email do cara q esta atualizando.
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("Email", "Email ja existente - ClienteUpdateValidator.class"));
		}

		// FieldMessage eu criei, essa lista carrega objeto que eu criei
		// Esse for eh pra cada objeto que tiver na nossa lista, ele vai adicionar um
		// tipo de erro da caracteristica do framework
		// Coisa do framework --> transporta o meu erro personalizado, para erro do
		// framework
		// Ele vai ser mostrado no ResourceExceptionHandler, la na parte:
		// for(FieldError x : e.getBindingResult().getFieldErrors()) { ....
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		// se a lista estiver vazia, retorna VERDADEIRO
		// se a lista tiver algo, retorna FALSO
		return list.isEmpty();
	}
}