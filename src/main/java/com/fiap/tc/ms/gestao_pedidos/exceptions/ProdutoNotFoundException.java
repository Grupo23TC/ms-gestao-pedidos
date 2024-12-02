package com.fiap.tc.ms.gestao_pedidos.exceptions;

public class ProdutoNotFoundException extends RuntimeException {
  public ProdutoNotFoundException(String message) {
    super(message);
  }
}
