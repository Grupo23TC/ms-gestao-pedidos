package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

public class PedidoUtil {
  public static Pedido gerarPedido() {
    Pedido pedido = new Pedido();
    pedido.setPedidoId(1L);
    pedido.setUsuarioId(1L);
    pedido.setValorTotal(500.0);
    pedido.setStatus(StatusPedido.CRIADO);
    pedido.setPagamento(PagamentoUtil.gerarPagamento());

    return pedido;
  }
}
