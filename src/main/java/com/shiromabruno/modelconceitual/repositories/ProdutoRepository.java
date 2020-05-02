package com.shiromabruno.modelconceitual.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shiromabruno.modelconceitual.domain.Categoria;
import com.shiromabruno.modelconceitual.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	//Dado o nome do produto, ver quais categorias ele pertence 
	//como eh uma consulta personalizada (nao eh de palavra reservada como findByEmail(String email) la do ClientRepository), precisa implementar
	//esse @Param pega a nomenclatura que esta no parametro e associa com o que esta no @Query
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	//Eh possivel construir dessa forma, ao inves do de cima
	////Verificar lista de Nomes Reservados para usar esse Find... (site docs.spring.io - jpa.query-methods)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
