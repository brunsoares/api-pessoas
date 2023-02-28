package io.github.brunsoares.pessoas.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EnderecoDTO {
    @NotEmpty(message = "{campo.logradouro.obrigatorio}")
    private String logradouro;

    @NotEmpty(message = "{campo.cep.obrigatorio}")
    private String cep;

    @NotNull(message = "{campo.numero.obrigatorio}")
    private Integer numero;

    @NotEmpty(message = "{campo.cidade.obrigatorio}")
    private String cidade;

}
