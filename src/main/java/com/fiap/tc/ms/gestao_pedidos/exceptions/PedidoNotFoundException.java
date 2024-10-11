package com.fiap.tc.ms.gestao_pedidos.exceptions;

public class PedidoNotFoundException extends RuntimeException {
  public PedidoNotFoundException(String msg) {
    super(msg);
  }
}
