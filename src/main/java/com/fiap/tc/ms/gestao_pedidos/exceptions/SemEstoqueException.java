package com.fiap.tc.ms.gestao_pedidos.exceptions;

public class SemEstoqueException extends RuntimeException {
  public SemEstoqueException(String message) {
    super(message);
  }
}
