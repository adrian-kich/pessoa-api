package com.adriankich.pessoaapi.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDTO(
        @NotBlank(message = "É obrigatório informar uma rua.")
        String rua,

        @NotBlank(message = "É obrigatório informar um número.")
        String numero,

        @NotBlank(message = "É obrigatório informar um bairro.")
        String bairro,

        @NotBlank(message = "É obrigatório informar uma cidade.")
        String cidade,

        @NotBlank(message = "É obrigatório informar um estado.")
        String estado,

        @NotBlank(message = "É obrigatório informar um CEP.")
        String cep,

        @Min(value = 0, message = "O tipo deve ser 0 ou 1.")
        @Max(value = 1, message = "O tipo deve ser 0 ou 1.")
        int type
) {}
