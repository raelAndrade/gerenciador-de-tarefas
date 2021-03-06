package br.com.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.demo.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	@Query("SELECT t FROM Tarefa t WHERE t.usuario.email = :emailUsuario ORDER BY t.id DESC") // JPQL - Linguagem de consulta da API de Persistencia do Java
	Page<Tarefa> carregarTarefasPorUsuario(@Param("emailUsuario") String email, Pageable pageable);
	
	@Query("SELECT count(*) FROM Tarefa t WHERE t.usuario.email = :emailUsuario AND t.concluida is true")
	int quantidadeTarefasConcluida(@Param("emailUsuario") String email);
	
	@Query("SELECT count(*) FROM Tarefa t WHERE t.usuario.email = :emailUsuario AND t.concluida is false")
	int quantidadeTarefasNaoConcluida(@Param("emailUsuario") String email);
}
