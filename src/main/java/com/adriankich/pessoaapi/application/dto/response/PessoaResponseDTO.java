package com.adriankich.pessoaapi.application.dto.response;

import com.adriankich.pessoaapi.domain.enums.PessoaState;

import java.time.LocalDate;
import java.util.List;

public record PessoaResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDate dtNascimento,
        String cpf,
        PessoaState state,
        int idade,
        List<EnderecoResponseDTO> enderecos
) {}
