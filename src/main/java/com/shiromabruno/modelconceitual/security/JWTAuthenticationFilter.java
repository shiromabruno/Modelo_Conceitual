package com.shiromabruno.modelconceitual.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiromabruno.modelconceitual.dto.CredenciaisDTO;

//filtro de autenticacao para o POST de login do user.
// com o UsernamePasswordAuthenticationFilter, o Spring sabe que ele tera que interceptar a requisicao de LOGIN /login
// http://localhost:8080/login ---> /login padrao do spring security para Autenticar. Se OK, gera o TOKEN(No Header Response), senao eh 401 ou 403.
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	//classe do Spring security
	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	//o construtor era instanciar os dois objetos acima
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			//pegar os dados da requisicao (body) e converter para CredenciaisDTO.class
			CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);

			//instancia o UsernamePasswordAuthenticationToken MAS NAO EH TOKEN DO JWT, EH SPRING SECURITY
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getSenha(), new ArrayList<>());
			
			//authenticationManager.authenticate --> ele que valida se esse USER E SENHA SAO VALIDOS. O framework faz isso com base nos modulos 
			//UserDetailsServices UserDetails. E o resultado dessa validacao ele retorna numa variavel Authentication auth, sendo valido ou nao, ele q informa pro spring security se deu certo ou nao
			//se errar a senha da um BadCredencial
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	//se a autenticacao for OK, entao precisa gerar um TOKEN e colocar na resposta da requisicao
	//No parametro ja recebe o objeto AUTH produzido no attemptAuthentication
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		//auth.getPrincipal() --> retorna ousuario do sprng security e faz um cast para o UserSS que implementei
		//pega o email do username e chama o generateToken passando o email.
		//o TOKEN eh gerado e colocado no Header da response. Ficara no Authorization
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		res.addHeader("Authorization", "Bearer " + token);
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}
}
