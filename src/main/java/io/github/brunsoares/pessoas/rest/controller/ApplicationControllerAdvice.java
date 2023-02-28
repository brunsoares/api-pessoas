package io.github.brunsoares.pessoas.rest.controller;

import io.github.brunsoares.pessoas.exceptions.ApiErros;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleValidationErrors(MethodArgumentNotValidException exception){
        BindingResult retornoResultados = exception.getBindingResult();
        List<String> mensagensErros = retornoResultados.getAllErrors()
                                                        .stream()
                                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                        .collect(Collectors.toList());

        return new ApiErros(mensagensErros);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException exception){
        String mensagemErro = exception.getReason();
        HttpStatus statusErro = exception.getStatus();
        ApiErros apiErros = new ApiErros(mensagemErro);
        return new ResponseEntity(apiErros, statusErro);
    }

}
