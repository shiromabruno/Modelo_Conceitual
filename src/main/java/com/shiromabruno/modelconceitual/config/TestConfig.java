package com.shiromabruno.modelconceitual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.shiromabruno.modelconceitual.services.DBService;

//Configuracoes especificias do Profile Teste
@Configuration
//Especifica que todos os Bean que tiverem nessa classe so serao ativos se no application.properties tiver ativo o "test"
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbservice;

	// instanciar o BD no profile de test
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbservice.instantiateTestDatabase();
		return true;
	}
}
