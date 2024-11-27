package com.fiap.tc.ms.gestao_pedidos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ProdutoNotFoundException extends RuntimeException {
  private final String erro;
  private final Instant horario;
  private final String rota;
  private final Integer status;
}
