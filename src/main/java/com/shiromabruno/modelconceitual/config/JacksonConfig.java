package com.shiromabruno.modelconceitual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiromabruno.modelconceitual.domain.PagamentoComBoleto;
import com.shiromabruno.modelconceitual.domain.PagamentoComCartao;

@Configuration
//Classe que vai ter metodo ou info, que estara disponivel no meu sistema e sera configurado no inicio da execucao da aplicacao
//Configura o Jackson para que o Json possa entender os @Types e aceitar a criacao dessas classes e objetos
//@Bean estarao nos metodos que conterao as informacoes de configs
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
	
	/*
	 * Field javaMailSender in com.pierre.cursomc.services.AbstractEmailService required a bean of type 'org.springframework.mail.javamail.JavaMailSender' that could not be found.
		The injection point has the following annotations:
		- @org.springframework.beans.factory.annotation.Autowired(required=true)
	 */
	@Bean
	public JavaMailSender jMS (){
		return new JavaMailSenderImpl();
	}
	
}