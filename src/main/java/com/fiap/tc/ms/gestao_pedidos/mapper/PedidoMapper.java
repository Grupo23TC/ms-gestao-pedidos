package com.fiap.tc.ms.gestao_pedidos.mapper;

import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ItemPedidoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;


public class PedidoMapper {
  private PedidoMapper() {
    throw new IllegalArgumentException("Classe utilitária - PedidoMapper");
  }

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
        pedido.getItensPedido().stream().map(item -> new ItemPedidoResponseDTO(item.getProdutoId(), item.getQuantidade(), item.getPreco())).toList(),
        pedido.getUsuarioId()
    );
  }

  public static PedidoStatusAtualizadoResponse toPedidoStatusAtualizado(Pedido pedido) {
    return new PedidoStatusAtualizadoResponse(
        pedido.getPedidoId(),
        pedido.getStatus()
    );
  }

  public static Pedido toPedidoSemItems(CadastrarPedidoRequest request) {
    Pedido pedido = new Pedido();
    pedido.setUsuarioId(request.usuarioId());
    pedido.setStatus(StatusPedido.CRIADO);

    return pedido;
  }
}
