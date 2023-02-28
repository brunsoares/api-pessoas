package io.github.brunsoares.pessoas.repositories;

import io.github.brunsoares.pessoas.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    @Query("SELECT p FROM Pessoa p WHERE p.nome LIKE CONCAT('%',:nome,'%')")
    List<Pessoa> findByNome(@Param("nome") String nome);
}
