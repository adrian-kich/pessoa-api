package com.adriankich.pessoaapi.domain.mapper;

import com.adriankich.pessoaapi.application.dto.response.PessoaResponseDTO;
import com.adriankich.pessoaapi.domain.model.Pessoa;

public class PessoaMapper {

    public static PessoaResponseDTO entityToDto(Pessoa pessoa) {
        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDtNascimento(),
                pessoa.getCpf(),
                pessoa.getIdade(),
                pessoa.getEnderecos().stream().map(EnderecoMapper::entityToDto).toList());
    }
}
