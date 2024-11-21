package com.adriankich.pessoaapi.application.exception.handler;

import com.adriankich.pessoaapi.application.exception.StandardError;
import com.adriankich.pessoaapi.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler implements ResourceExceptionHandler<NotFoundException> {

    /**
     * handle
     *
     * @param ex AlreadyExistsException
     * @param request HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @Override
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handle(NotFoundException ex, HttpServletRequest request) {
        return response(ex, request, HttpStatus.NOT_FOUND);
    }
}
