package io.github.brunsoares.pessoas.rest.presenters;

import io.github.brunsoares.pessoas.models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoPresenter {
    private Integer codigo;

    private String logradouro;

    private String cep;

    private Integer numero;

    private String cidade;

    public EnderecoPresenter enderecoPresenterBuilder(Endereco endereco){
        return new EnderecoPresenterBuilder()
                .codigo(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .cep(endereco.getCep())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .build();
    }
}
