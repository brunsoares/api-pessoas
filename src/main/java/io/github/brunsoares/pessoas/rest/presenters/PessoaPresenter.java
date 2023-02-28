package io.github.brunsoares.pessoas.rest.presenters;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.brunsoares.pessoas.models.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaPresenter {
    private Integer codigo;

    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EnderecoPresenter enderecoPrincipal;

    public PessoaPresenter pessoaPresenterBuilder(Pessoa pessoa, EnderecoPresenter endereco){
        return new PessoaPresenterBuilder()
                .codigo(pessoa.getId())
                .nome(pessoa.getNome())
                .dataNascimento(pessoa.getDataNascimento())
                .enderecoPrincipal(endereco)
                .build();
    }

}
