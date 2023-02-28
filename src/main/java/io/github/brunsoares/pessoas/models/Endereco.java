package io.github.brunsoares.pessoas.models;

import io.github.brunsoares.pessoas.rest.dto.EnderecoDTO;
import io.github.brunsoares.pessoas.rest.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false, length = 8)
    private Integer numero;

    @Column(nullable = false, length = 150)
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Endereco enderecoBuilder(EnderecoDTO enderecoDTO){
        return new EnderecoBuilder()
                .logradouro(enderecoDTO.getLogradouro())
                .cep(enderecoDTO.getCep())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .build();
    }

}
