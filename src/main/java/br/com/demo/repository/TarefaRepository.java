package br.com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.demo.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	@Query("SELECT t FROM Tarefa t WHERE t.usuario.email = :emailUsuario ORDER BY t.dataExpiracao DESC") // JPQL - Linguagem de consulta da API de Persistencia do Java
	List<Tarefa> carregarTarefasPorUsuario(@Param("emailUsuario") String email);

}
