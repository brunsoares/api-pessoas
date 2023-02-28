package io.github.brunsoares.pessoas.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.brunsoares.pessoas.rest.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "data_nascimento", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(name = "endereco_principal")
    private Integer enderecoPrincipal;

    public Pessoa pessoaBuilder(PessoaDTO pessoaDTO){
        return new PessoaBuilder()
                .nome(pessoaDTO.getNome())
                .dataNascimento(pessoaDTO.getDataNascimento())
                .build();
    }

}
