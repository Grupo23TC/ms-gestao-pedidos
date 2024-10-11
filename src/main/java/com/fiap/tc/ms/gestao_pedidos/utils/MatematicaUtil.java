package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;
import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;

import java.util.List;

public class MatematicaUtil {
  public static double calcularValorTotalDto(List<ItemPedidoDto> itens) {
    return itens.stream().mapToDouble(item -> item.preco() * item.quantidade()).sum();
  }

  public static double calcularValorTotal(List<ItemPedido> itens) {
    return itens.stream().mapToDouble(item -> item.getPreco() * item.getQuantidade()).sum();
  }
}
