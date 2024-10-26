package com.fiap.tc.ms.gestao_clientes.exceptions;

public class ClienteNotFoundException extends RuntimeException {
  public ClienteNotFoundException(String msg) {
    super(msg);
  }
}
