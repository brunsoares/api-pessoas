package io.github.brunsoares.pessoas.rest.controller;

import io.github.brunsoares.pessoas.models.Endereco;
import io.github.brunsoares.pessoas.models.Pessoa;
import io.github.brunsoares.pessoas.rest.presenters.EnderecoPresenter;
import io.github.brunsoares.pessoas.repositories.EnderecoRepository;
import io.github.brunsoares.pessoas.repositories.PessoaRepository;
import io.github.brunsoares.pessoas.rest.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private PessoaRepository pessoaRepository;
    private EnderecoRepository repository;

    @Autowired
    public EnderecoController(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository){
        this.repository = enderecoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @PostMapping("/codigo/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EnderecoPresenter salvarEndereco(@PathVariable Integer id, @RequestBody @Valid EnderecoDTO enderecoDTO){
        Pessoa pessoa = checarPessoaCadastro(id);
        Endereco endereco = new Endereco().enderecoBuilder(enderecoDTO);
        endereco.setPessoa(pessoa);
        repository.save(endereco);
        return new EnderecoPresenter().enderecoPresenterBuilder(endereco);
    }

    @GetMapping("/listar/{id}")
    public List<EnderecoPresenter> listarEnderecosPorPessoa(@PathVariable Integer id){
        List<EnderecoPresenter> enderecoPresenters = new ArrayList<>();
        Pessoa pessoa = checarPessoaCadastro(id);
        List<Endereco> enderecos = repository.findByIdPessoa(pessoa.getId());
        for(Endereco endereco: enderecos){
            enderecoPresenters.add(new EnderecoPresenter().enderecoPresenterBuilder(endereco));
        }
        return enderecoPresenters;
    }

    @GetMapping("/principal/{id}")
    public EnderecoPresenter buscarEnderecoPrincipal(@PathVariable Integer id){
        return repository.findById(id).map(endereco -> new EnderecoPresenter().enderecoPresenterBuilder(endereco))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/codigo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEndereco(@PathVariable Integer id){
        repository.findById(id).map(endereco -> {
            repository.delete(endereco);
            return endereco;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/codigo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarEndereco(@PathVariable Integer id, @RequestBody @Valid EnderecoDTO enderecoDTO){
        repository.findById(id).map(endereco -> {
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setCep(enderecoDTO.getCep());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setCidade(enderecoDTO.getCidade());
            return repository.save(endereco);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Pessoa checarPessoaCadastro(Integer id){
        return pessoaRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não cadastrada!"));
    }

}
