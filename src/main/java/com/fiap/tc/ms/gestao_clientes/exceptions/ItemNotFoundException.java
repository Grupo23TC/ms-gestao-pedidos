package com.fiap.tc.ms.gestao_clientes.exceptions;

public class ItemNotFoundException extends RuntimeException{
  public ItemNotFoundException(String message) {
    super(message);
  }
}
