package com.adriankich.pessoaapi.application.exception.handler;

import com.adriankich.pessoaapi.application.exception.StandardError;
import com.adriankich.pessoaapi.application.exception.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public interface ResourceExceptionHandler<T extends Exception> {

    /**
     * handle
     *
     * @param ex T
     * @param request HttpServletRequest
     * @return ResponseEntity
     */
    public ResponseEntity<?> handle(T ex, HttpServletRequest request );

    /**
     * response
     *
     * @param ex Exception
     * @param request HttpServletRequest
     * @param status HttpStatus
     * @return ResponseEntity<StandardError>
     */
    public default ResponseEntity<StandardError> response(Exception ex, HttpServletRequest request, HttpStatus status )
    {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    /**
     * responseValidation
     *
     * @param ex MethodArgumentNotValidException
     * @param request HttpServletRequest
     * @param status HttpStatus
     * @return ResponseEntity<ValidationError>
     */
    public default ResponseEntity<ValidationError> responseValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request, HttpStatus status )
    {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ValidationError error = ValidationError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error("Erro na validação dos campos")
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}