package com.adriankich.pessoaapi.domain.mapper;

import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.response.PessoaResponseDTO;
import com.adriankich.pessoaapi.domain.enums.PessoaState;
import com.adriankich.pessoaapi.domain.model.Pessoa;

public class PessoaMapper {

    public static PessoaResponseDTO entityToDto(Pessoa pessoa) {
        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getDtNascimento(),
                pessoa.getCpf(),
                pessoa.getState(),
                pessoa.getIdade(),
                pessoa.getEnderecos().stream().map(EnderecoMapper::entityToDto).toList());
    }

    public static Pessoa dtoToEntity(PessoaRequestDTO pessoaRequestDTO) {
        return Pessoa.builder()
                .nome(pessoaRequestDTO.nome())
                .cpf(formatCpf(pessoaRequestDTO.cpf()))
                .email(pessoaRequestDTO.email())
                .dtNascimento(pessoaRequestDTO.dtNascimento())
                .state(PessoaState.PENDENTE)
                .build();
    }

    private static String formatCpf(String cpf) {
        return cpf.replaceAll("\\.|-", "");
    }
}
