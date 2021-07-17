package com.shiromabruno.modelconceitual.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	//pega a palavra secreta para misturar no token e garantir assinatura
	@Value("${jwt.secret}")
	private String secret;
	
	//tempo de validade do token
	@Value("${jwt.expiration}")
	private Long expiration;
	
	//para gerar token , usar metodo da dependencia do JJWT(io.jsonwebtoken) (arquivo POM.XML)
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				//a data sera o horario atual do sistema + data de Expiration
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				//tem vaaaaarios tipos de algoritmos. COlocar SignatureAlgorithm. e espaco para listar todos
				//precisa do getBytes pois exige que seja BYTES
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

}
