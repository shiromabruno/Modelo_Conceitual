package com.shiromabruno.modelconceitual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//dei autowired da INTERFACE, mas o Spring consegue vasculhar e colocar a implementacao dessa Interface (UserDetailsServiceImpl - pacote services)
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env; // captura PROFILES para validar se estamos rodando como TEST/DEV/PROD...
	
	//rotas que estao liberados, sem TOKEN
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**", // ainda precisa implementar o ENVIRONMENT LA EM CIMA
	};
	
	//caminhos somente para GET/RECUPERAR , sem chances para DELETA/ INSERIR/UPDATE
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); //desabilita acesso para poder entrar no H2
		}
		
		//O metodo .cors() acha o CorsConfigurationSource [se definido na classe] para aplicar. Se nao tiver, usa por padrao o CorsFilter
		http.cors().and().csrf().disable(); // desabilitar a protecao a ataques CSRF, pois nosso sistema eh stateless, entao nao armazena AUTENTICACAO EM SESSAO
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //autoriza todas as rotas do vetor para comandos GET APENAS!
			.antMatchers(PUBLIC_MATCHERS).permitAll() //autoriza rotas para H2
			.anyRequest().authenticated(); //para todo o resto, precisa do token. Nesse caso aparecera o 403
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // garante q nao armazena sessao de USUARIO
	}
	
	//tem um segundo metodo configure pois
	//como vamos usar a identificacao do framework, temos q sobrescrever esse metodo por 2 razoes:
	//quem eh o UserDetaisService que estamos usando e quem eh o algoritmo de codificacao da senha (BCrypt)
	//metodo para buscar o usuario(email) e validar para autenticacao
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean // definindo CorsConfigurationSource que permite acesso aos meus endpoints FROM multiplas fontes ("/**") com as configs basicas
	CorsConfigurationSource corsConfigurationSource() { // as regras abaixo aplicados via http.cors() no configure(HttpSecurity http)
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues()); //eh necessario colocar explicitamente essa liberacao pra funcioanr. Por padrao nao eh liberado CROSS-ORIGIN
		return source;
	}
	
	@Bean //usado para cript e decript senha
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
