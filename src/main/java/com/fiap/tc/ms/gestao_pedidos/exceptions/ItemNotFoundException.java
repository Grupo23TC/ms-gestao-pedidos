package com.fiap.tc.ms.gestao_pedidos.exceptions;

public class ItemNotFoundException extends RuntimeException{
  public ItemNotFoundException(String message) {
    super(message);
  }
}
