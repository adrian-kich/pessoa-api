package com.adriankich.pessoaapi.domain.mapper;

import com.adriankich.pessoaapi.application.dto.response.EnderecoResponseDTO;
import com.adriankich.pessoaapi.domain.model.Endereco;

public class EnderecoMapper {

    public static EnderecoResponseDTO entityToDto(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getPessoa().getId(),
                endereco.getType()
        );
    }
}
