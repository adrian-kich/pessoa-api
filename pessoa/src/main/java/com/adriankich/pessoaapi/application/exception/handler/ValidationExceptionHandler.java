package com.adriankich.pessoaapi.application.exception.handler;

import com.adriankich.pessoaapi.application.exception.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler implements ResourceExceptionHandler<MethodArgumentNotValidException>{

    /**
     * handle
     *
     * @param ex AlreadyExistsException
     * @param request HttpServletRequest
     * @return ResponseEntity<ValidationError>
     */
    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return responseValidation(ex, request, HttpStatus.BAD_REQUEST);
    }
}
