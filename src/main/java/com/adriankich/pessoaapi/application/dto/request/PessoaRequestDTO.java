package com.adriankich.pessoaapi.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record PessoaRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @CPF(message = "O CPF informado é inválido.")
        @NotBlank(message = "O CPF é obrigatório.")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento não deve ser uma data futura.")
        LocalDate dtNascimento,

        @NotEmpty(message = "É obrigatório informar ao menos um endereço")
        @Valid
        List<EnderecoRequestDTO> enderecos
) {}
