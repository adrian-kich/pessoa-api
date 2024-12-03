package com.adriankich.pessoaapi.domain.dto;

public record EmailVerificationDTO (
        Long id,
        String name,
        String email,
        String cpf)
{}
