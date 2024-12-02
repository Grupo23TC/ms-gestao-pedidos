package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ItemPedidoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;

import java.math.BigDecimal;

public class ItemPedidoUtil {
  public static ItemPedidoResponseDTO gerarItemPedido() {
    return new  ItemPedidoResponseDTO(
        1L,
        1,
        BigDecimal.ONE
    );
  }

  public static ItemPedidoDTO gerarItemPedidoDto() {
    return new ItemPedidoDTO(
        1L,
        1
    );
  }

  public static ItemPedidoDTO gerarItemPedidoDtoProdutoNaoExiste() {
    return new ItemPedidoDTO(
        1000000000000L,
        1
    );
  }
}
