package com.fiap.tc.ms.gestao_pedidos.handlers;

import com.fiap.tc.ms.gestao_pedidos.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
  private static final HttpStatus UNPROCESSABLE_ENTITY = HttpStatus.UNPROCESSABLE_ENTITY;

  @ExceptionHandler(PedidoNotFoundException.class)
  public ResponseEntity<ErroCustomizado> handlePedidoNotFoundException(
      PedidoNotFoundException ex,
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

  @ExceptionHandler(ProdutoNotFoundException.class)
  public ResponseEntity<ErroCustomizado> handleProdutoNotFoundException(ProdutoNotFoundException ex, HttpServletRequest request) {
    HttpStatus status  = HttpStatus.BAD_REQUEST;

    ErroCustomizado erro = new ErroCustomizado(
        ex.getMessage(),
        Instant.now(),
        request.getRequestURI(),
        status.value()
    );

    return ResponseEntity.status(status).body(erro);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErroCustomizado> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex,
      HttpServletRequest request
  ) {
    ValidacaoErro erro = new ValidacaoErro(
        "Dados inválidos",
        Instant.now(),
        request.getRequestURI(),
        UNPROCESSABLE_ENTITY.value()
    );

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      erro.addCampoErro(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(erro);
  }
}
