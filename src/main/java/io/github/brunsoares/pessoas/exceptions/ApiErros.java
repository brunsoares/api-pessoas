package io.github.brunsoares.pessoas.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApiErros {

    private List<String> erros;

    public ApiErros(List<String> erros) {
        this.erros = erros;
    }

    public ApiErros(String mensagem) {
        this.erros = Arrays.asList(mensagem);
    }
}
