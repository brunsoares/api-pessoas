package io.github.brunsoares.pessoas.model;

import io.github.brunsoares.pessoas.models.Endereco;
import io.github.brunsoares.pessoas.models.Pessoa;
import io.github.brunsoares.pessoas.rest.dto.EnderecoDTO;
import io.github.brunsoares.pessoas.rest.dto.PessoaDTO;
import io.github.brunsoares.pessoas.rest.presenters.EnderecoPresenter;
import io.github.brunsoares.pessoas.rest.presenters.PessoaPresenter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class BuildersTest {

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;
    private PessoaPresenter pessoaPresenter;
    private Endereco endereco;
    private EnderecoDTO enderecoDTO;
    private EnderecoPresenter enderecoPresenter;

    @Before
    public void inicial(){
        pessoa = new Pessoa();
        pessoaDTO = new PessoaDTO();
        pessoaPresenter = new PessoaPresenter();

        endereco = new Endereco();
        enderecoDTO = new EnderecoDTO();
        enderecoPresenter = new EnderecoPresenter();
    }

    @Test
    public void pessoaBuilder(){
        pessoaDTO.setNome("Bruno");
        pessoaDTO.setDataNascimento(LocalDate.of(2003, 02, 22));

        Pessoa pessoaBuilder = pessoa.pessoaBuilder(pessoaDTO);

        PessoaPresenter pessoaPresenterBuilder =  pessoaPresenter.pessoaPresenterBuilder(pessoaBuilder, null);

        Assert.assertTrue(pessoaDTO.getNome().equalsIgnoreCase(pessoaBuilder.getNome()));
        Assert.assertTrue(pessoaBuilder.getNome().equalsIgnoreCase(pessoaPresenterBuilder.getNome()));
    }

    @Test
    public void enderecoBuilder(){
        enderecoDTO.setCep("13211726");
        enderecoDTO.setLogradouro("Jardim América");
        enderecoDTO.setNumero(123);
        enderecoDTO.setCidade("Jundiaí");

        Endereco enderecoBuilder = endereco.enderecoBuilder(enderecoDTO);

        EnderecoPresenter enderecoPresenterBuilder = enderecoPresenter.enderecoPresenterBuilder(enderecoBuilder);

        Assert.assertTrue(enderecoDTO.getCidade().equalsIgnoreCase(enderecoBuilder.getCidade()));
        Assert.assertTrue(enderecoBuilder.getCidade().equalsIgnoreCase(enderecoPresenterBuilder.getCidade()));
    }

}
