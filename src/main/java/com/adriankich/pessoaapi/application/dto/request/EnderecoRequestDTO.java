package com.adriankich.pessoaapi.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDTO(
        @NotNull(message = "É obrigatório informar uma rua.")
        String rua,

        @NotNull(message = "É obrigatório informar um número.")
        String numero,

        @NotNull(message = "É obrigatório informar um bairro.")
        String bairro,

        @NotNull(message = "É obrigatório informar uma cidade.")
        String cidade,

        @NotNull(message = "É obrigatório informar um estado.")
        String estado,

        @NotNull(message = "É obrigatório informar um CEP.")
        String cep,

        @Min(value = 0, message = "O tipo deve ser 0 ou 1.")
        @Max(value = 1, message = "O tipo deve ser 0 ou 1.")
        int type
) {}
