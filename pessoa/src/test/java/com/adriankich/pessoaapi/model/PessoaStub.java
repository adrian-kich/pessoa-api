package com.adriankich.pessoaapi.model;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.UpdatePessoaDTO;
import com.adriankich.pessoaapi.domain.model.Endereco;
import com.adriankich.pessoaapi.domain.model.Pessoa;

import java.time.LocalDate;
import java.util.List;

public class PessoaStub {

    public static Pessoa createPessoaWithoutId() {
        return Pessoa.builder()
                .nome("Adrian Kich")
                .cpf("03116037000")
                .dtNascimento(LocalDate.of(2001, 10, 26))
                .enderecos(List.of(Endereco.builder()
                                .rua("Rua 1")
                                .numero("400")
                                .bairro("Bairro 1")
                                .cidade("Cidade 1")
                                .estado("Estado 1")
                                .cep("95900-972")
                        .build()))
                .build();
    }

    public static Pessoa createPessoaWithId(Long id) {
        return Pessoa.builder()
                .id(id)
                .nome("Adrian Kich")
                .cpf("03116037000")
                .dtNascimento(LocalDate.of(2001, 10, 26))
                .enderecos(List.of(Endereco.builder()
                        .rua("Rua 1")
                        .numero("400")
                        .bairro("Bairro 1")
                        .cidade("Cidade 1")
                        .estado("Estado 1")
                        .cep("95900-972")
                        .build()))
                .build();
    }

    public static PessoaRequestDTO createPessoaRequestDTO() {
        return new PessoaRequestDTO(
                "Adrian Kich",
                "03116037000",
                "email@gmail.com",
                LocalDate.of(2001, 10, 26),
                List.of(new EnderecoRequestDTO(
                        "Rua 1",
                        "400",
                        "Bairro 1",
                        "Cidade 1",
                        "Estado 1",
                        "95900-872",
                        1
                ))
        );
    }

    public static UpdatePessoaDTO createUpdatePessoaDTO() {
        return new UpdatePessoaDTO(
                "Adrian Kich Rename",
                "03116037000",
                LocalDate.of(2001, 10, 26)
        );
    }
}
