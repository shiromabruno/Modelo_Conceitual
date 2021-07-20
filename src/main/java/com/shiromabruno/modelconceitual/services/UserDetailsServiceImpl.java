package com.shiromabruno.modelconceitual.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.Cliente;
import com.shiromabruno.modelconceitual.repositories.ClienteRepository;
import com.shiromabruno.modelconceitual.security.UserSS;

@Service
//UserDetailsService eh uma interface do Spring Security que permite a busca pelo nome do usuario
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repo;

	//metodo que recebe o usuario (String username) e volta o UserDetails (classe UserSS no pacote Security)
	//buscar um cliente por email (o ClienteRepository ja tem o metodo findByEmail)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email); // excecao do spring security
		}
		
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
