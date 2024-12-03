package com.adriankich.pessoaapi.model;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.domain.model.Endereco;

public class EnderecoStub {

    public static Endereco createEnderecoWithId(Long id) {
        return Endereco.builder()
                .id(id)
                .rua("Rua 1")
                .numero("400")
                .bairro("Bairro 1")
                .cidade("Cidade 1")
                .estado("Estado 1")
                .cep("95900-972")
                .build();
    }

    public static EnderecoRequestDTO createEnderecoRequestDTO() {
        return new EnderecoRequestDTO(
                "Rua 1",
                "400",
                "Bairro 1",
                "Cidade 1",
                "Estado 1",
                "95900-872",
                1);
    }
}
