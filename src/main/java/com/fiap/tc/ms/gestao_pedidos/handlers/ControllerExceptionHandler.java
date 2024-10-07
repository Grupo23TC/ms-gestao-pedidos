package com.fiap.tc.ms.gestao_pedidos.handlers;

import com.fiap.tc.ms.gestao_pedidos.exceptions.ErroCustomizado;
import com.fiap.tc.ms.gestao_pedidos.exceptions.PedidoNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
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
}
