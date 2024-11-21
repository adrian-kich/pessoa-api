package com.adriankich.pessoaapi.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UpdatePessoaDTO(
        @NotNull(message = "O nome é obrigatório.")
        String nome,

        @CPF(message = "O CPF informado é inválido.")
        @NotNull(message = "O CPF é obrigatório.")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento não deve ser uma data futura.")
        LocalDate dtNascimento
) {}
