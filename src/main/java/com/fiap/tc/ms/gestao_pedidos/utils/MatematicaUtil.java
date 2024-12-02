package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;

import java.math.BigDecimal;
import java.util.List;

public class MatematicaUtil {
  private MatematicaUtil() {
    throw new IllegalStateException("Classe utilitária - MatematicaUtil");
  }

  public static BigDecimal calcularValorTotal(List<ItemPedido> itens) {
    return itens
        .stream()
        .map(item -> item.getPreco().multiply(new BigDecimal(item.getQuantidade())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
