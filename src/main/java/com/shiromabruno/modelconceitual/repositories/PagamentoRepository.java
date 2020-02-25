package com.shiromabruno.modelconceitual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiromabruno.modelconceitual.domain.Pagamento;

// nao precisa criar Repostitory das outras classes pagamentos !
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

	
}
