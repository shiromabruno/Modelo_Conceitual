package com.shiromabruno.modelconceitual.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.domain.enums.TipoCliente;
import com.shiromabruno.modelconceitual.dto.ClienteNewDTO;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.services.exceptions.FieldMessage;
import com.shiromabruno.modelconceitual.services.validation.utils.BR;

//Classe que implementara as regras da Notacao @ClienteInsert
//ConstraintValidator<ClienteInsert, Aqui eh o Tipo de dados que vai aceitar essa notacao>
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	
	@Autowired
	private ClienteRepository repo;
	
	//Aqui poderia colocar alguma programacao de inicializacao
	// Mas nao precisa
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		//Instancia de lista vazia de FieldMessage
		List<FieldMessage> list = new ArrayList<>();
		
		// Tem esse getCod() pois o objDto.getTipo() retorna INTEIRO
	    if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()) ) {
	    	list.add(new FieldMessage("cpfOuCnpj", "CPF invalido - ClienteInsertValidator.class"));
	    }
	    
	    // Tem esse getCod() pois o objDto.getTipo() retorna INTEIRO
	    if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()) ) {
	    	list.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido - ClienteInsertValidator.class"));
	    }
	    
	    Cliente aux = repo.findByEmail(objDto.getEmail());
	    if (aux != null) {
	    	list.add(new FieldMessage("Email", "Email ja existente - ClienteInsertValidator.class"));
	    }

		//FieldMessage eu criei, essa lista carrega objeto que eu criei
		//Esse for eh pra cada objeto que tiver na nossa lista, ele vai adicionar um tipo de erro da caracteristica do framework
		//Coisa do framework --> transporta o meu erro personalizado, para erro do framework
		//Ele vai ser mostrado no ResourceExceptionHandler, la na parte:
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