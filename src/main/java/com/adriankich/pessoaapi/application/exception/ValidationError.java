package com.adriankich.pessoaapi.application.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ValidationError extends StandardError {

    private String fields;
    private String fieldsMessage;
}