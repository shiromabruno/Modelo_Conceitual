package com.shiromabruno.modelconceitual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shiromabruno.modelconceitual.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	//Fala que eh ReadOnly para ser mais rapido.
	//Evita locking em gerenciamento de transações em banco de dados
	@Transactional(readOnly=true)
	Cliente  findByEmail(String email); 
	
}
