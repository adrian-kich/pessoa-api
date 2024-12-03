package com.adriankich.pessoaapi.application.dto.response;

import com.adriankich.pessoaapi.domain.enums.EnderecoType;

public record EnderecoResponseDTO(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Long pessoaId,
        EnderecoType type
) {}
