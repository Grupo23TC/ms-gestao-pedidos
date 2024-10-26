package com.fiap.tc.ms.gestao_clientes.handlers;

import com.fiap.tc.ms.gestao_clientes.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ClienteNotFoundException.class)
  public ResponseEntity<ErroCustomizado> handleClienteNotFoundException(
          ClienteNotFoundException ex,
          HttpServletRequest request
  ) {
    HttpStatus status  = HttpStatus.NOT_FOUND;
    ErroCustomizado erroCustomizado = new ErroCustomizado(
            ex.getMessage(),
            Instant.now(),
            request.getRequestURI(),
            status.value()
    );

    return ResponseEntity.status(status).body(erroCustomizado);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErroCustomizado> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException ex,
          HttpServletRequest request
  ) {
    HttpStatus status  = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidacaoErro erro = new ValidacaoErro(
            "Dados inválidos",
            Instant.now(),
            request.getRequestURI(),
            status.value()
    );

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      erro.addCampoErro(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(status).body(erro);
  }
}
