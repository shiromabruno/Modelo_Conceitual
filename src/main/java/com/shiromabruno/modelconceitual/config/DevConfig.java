package com.shiromabruno.modelconceitual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.shiromabruno.modelconceitual.services.DBService;
import com.shiromabruno.modelconceitual.services.EmailService;
import com.shiromabruno.modelconceitual.services.SmtpEmailService;

//Configuracoes especificias do Profile Testee
@Configuration
//Especifica que todos os Bean que tiverem nessa classe so serao ativos se no application.properties tiver ativo o "test"
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbservice;
	
	//pega o valor do arquivo DEV de property. Se nao for CREATE, nao executa o SERVICE pra incluir todos os dados nas TABELAS
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	// instanciar o BD no profile de test
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}
		dbservice.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService metodoQualquerCoisa() {
		return new SmtpEmailService();
	}
	
}
