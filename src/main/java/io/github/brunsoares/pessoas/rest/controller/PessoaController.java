package io.github.brunsoares.pessoas.rest.controller;

import io.github.brunsoares.pessoas.models.Endereco;
import io.github.brunsoares.pessoas.models.Pessoa;
import io.github.brunsoares.pessoas.rest.presenters.EnderecoPresenter;
import io.github.brunsoares.pessoas.rest.presenters.PessoaPresenter;
import io.github.brunsoares.pessoas.repositories.EnderecoRepository;
import io.github.brunsoares.pessoas.repositories.PessoaRepository;
import io.github.brunsoares.pessoas.rest.dto.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private PessoaRepository repository;
    private EnderecoRepository enderecoRepository;

    @Autowired
    public PessoaController(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository){
        this.repository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaPresenter salvarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO){
        Pessoa pessoa = new Pessoa().pessoaBuilder(pessoaDTO);
        repository.save(pessoa);
        return new PessoaPresenter().pessoaPresenterBuilder(pessoa, null);
    }

    @GetMapping("/codigo/{id}")
    public PessoaPresenter buscarPessoaPorId(@PathVariable Integer id){
        EnderecoPresenter endereco = null;
        Pessoa pessoa = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (pessoa.getEnderecoPrincipal() != null) {
            endereco = new EnderecoPresenter().enderecoPresenterBuilder(checarEnderecoCadastro(pessoa.getEnderecoPrincipal()));
        }
        return new PessoaPresenter().pessoaPresenterBuilder(pessoa, endereco);
    }

    @GetMapping("/nome/{nome}")
    public List<PessoaPresenter> buscarPessoasPorNome(@PathVariable String nome){
        List<PessoaPresenter> pessoasPresenter = new ArrayList<>();
        List<Pessoa> pessoas = repository.findByNome(nome);
        for(Pessoa pessoa: pessoas){
            EnderecoPresenter endereco = null;
            if(pessoa.getEnderecoPrincipal() != null) {
                endereco = new EnderecoPresenter().enderecoPresenterBuilder(checarEnderecoCadastro(pessoa.getEnderecoPrincipal()));
            }
            pessoasPresenter.add(new PessoaPresenter().pessoaPresenterBuilder(pessoa, endereco));
        }
        return pessoasPresenter;
    }

    @GetMapping("/listar")
    public List<PessoaPresenter> listarPessoas(){
        List<PessoaPresenter> pessoasPresenter = new ArrayList<>();
        List<Pessoa> pessoas = repository.findAll();
        for(Pessoa pessoa: pessoas){
            EnderecoPresenter endereco = null;
            if(pessoa.getEnderecoPrincipal() != null) {
                endereco = new EnderecoPresenter().enderecoPresenterBuilder(checarEnderecoCadastro(pessoa.getEnderecoPrincipal()));
            }
            pessoasPresenter.add(new PessoaPresenter().pessoaPresenterBuilder(pessoa, endereco));
        }
        return pessoasPresenter;
    }

    @DeleteMapping("/codigo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoa(@PathVariable Integer id){
        repository.findById(id).map(pessoa -> {
            repository.delete(pessoa);
            return pessoa;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/codigo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPessoa(@PathVariable Integer id, @RequestBody @Valid PessoaDTO pessoaAtualizarDTO){
        repository.findById(id).map(pessoa -> {
            pessoa.setNome(pessoaAtualizarDTO.getNome());
            return repository.save(pessoa);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/endereco-principal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarEnderecoPrincipal(@PathVariable Integer id,
                                           @RequestBody HashMap idEndereco ){
        Endereco endereco =  checarEnderecoCadastro(Integer.parseInt(idEndereco.get("idEndereco").toString()));
        repository.findById(id).map(pessoa -> {
            pessoa.setEnderecoPrincipal(endereco.getId());
            return repository.save(pessoa);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Endereco checarEnderecoCadastro(Integer id){
        return enderecoRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço não cadastrado!"));
    }

}
