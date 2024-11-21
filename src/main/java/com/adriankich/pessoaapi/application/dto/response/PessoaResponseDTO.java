package com.adriankich.pessoaapi.application.dto.response;

import java.time.LocalDate;
import java.util.List;

public record PessoaResponseDTO(
        Long id,
        String nome,
        LocalDate dtNascimento,
        String cpf,
        int idade,
        List<EnderecoResponseDTO> enderecos
) {}
