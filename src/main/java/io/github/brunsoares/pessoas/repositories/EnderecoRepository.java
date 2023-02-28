package io.github.brunsoares.pessoas.repositories;

import io.github.brunsoares.pessoas.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("SELECT e FROM Endereco e LEFT JOIN Pessoa p ON p.id = e.pessoa.id WHERE p.id = :id")
    List<Endereco> findByIdPessoa(@Param("id") Integer id);
}
