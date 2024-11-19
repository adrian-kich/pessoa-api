package com.adriankich.pessoaapi.domain.enums;

import java.util.Arrays;

public enum EnderecoType {

    PRINCIPAL(1),
    SECUNDARIO(0);

    private final int value;

    EnderecoType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EnderecoType fromValue(int value) {
        return Arrays.stream(EnderecoType.values())
                .filter(tipo -> tipo.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código inválido para o tipo de endereço: " + value));
    }
}
