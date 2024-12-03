package com.adriankich.email.dto;

public record PessoaMessageDTO (
        Long id,
        String name,
        String email,
        String cpf
) {}
