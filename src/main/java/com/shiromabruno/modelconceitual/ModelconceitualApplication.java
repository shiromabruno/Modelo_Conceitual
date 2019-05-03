package com.shiromabruno.modelconceitual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.repositories.CategoriaRepository;

@SpringBootApplication
//public class ModelconceitualApplication {
public class ModelconceitualApplication implements CommandLineRunner{
    
	@Autowired
	private CategoriaRepository categoriarepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ModelconceitualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2)); 
	    // criar uma lista automatica
	}

}
