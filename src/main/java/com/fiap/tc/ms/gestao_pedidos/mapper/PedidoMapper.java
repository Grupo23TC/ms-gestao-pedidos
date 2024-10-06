package com.fiap.tc.ms.gestao_pedidos.mapper;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;

public class PedidoMapper {
  public static PedidoPaginadoResponse toPedidoPaginado(Pedido pedido) {
    return new PedidoPaginadoResponse(
        pedido.getPedidoId(),
        pedido.getStatus(),
        pedido.getValorTotal()
    );
  }

  public static PedidoResponse toPedidoResponse(Pedido pedido) {
    return new PedidoResponse(
        pedido.getPedidoId(),
        pedido.getStatus(),
        pedido.getValorTotal(),
        pedido.getCodigoRastreio(),
        pedido.getItensPedido().stream().map(item -> new ItemPedidoDto(item.getProdutoId(), item.getQuantidade(), item.getPreco())).toList(),
        pedido.getUsuarioId()
    );
  }

  public static PedidoStatusAtualizadoResponse toPedidoStatusAtualizado(Pedido pedido) {
    return new PedidoStatusAtualizadoResponse(
        pedido.getPedidoId(),
        pedido.getStatus()
    );
  }
}
