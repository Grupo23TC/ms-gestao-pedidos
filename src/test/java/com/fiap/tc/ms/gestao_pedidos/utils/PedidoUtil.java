package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public class PedidoUtil {
  public static Pedido gerarPedido() {
    Pedido pedido = new Pedido();
    pedido.setPedidoId(1L);
    pedido.setUsuarioId(1L);
    pedido.setStatus(StatusPedido.CRIADO);
    pedido.setPagamento(PagamentoUtil.gerarPagamento());

    return pedido;
  }

  public static PedidoPaginadoResponse gerarPedidoPaginadoResponse() {
    return new PedidoPaginadoResponse(
        1L,
        StatusPedido.CRIADO,
        BigDecimal.ONE
    );
  }

  public static PedidoResponse gerarPedidoResponse() {
    return new PedidoResponse(
        1L,
        StatusPedido.CRIADO,
        BigDecimal.ONE,
        1L,
        List.of(
            ItemPedidoUtil.gerarItemPedido()
        ),
        1L
    );
  }

  public static PedidoStatusAtualizadoResponse gerarPedidoStatusAtualizadoResponse() {
    return new PedidoStatusAtualizadoResponse(
        1L,
        StatusPedido.PROCESSANDO
    );
  }

  public static PedidoDeletadoResponse gerarPedidoDeletadoResponse() {
    return new PedidoDeletadoResponse(
        true
    );
  }

  public static CadastrarPedidoRequest gerarCadastrarPedidoRequest() {
    return new CadastrarPedidoRequest(
      1L,
      List.of(ItemPedidoUtil.gerarItemPedidoDto())
    );
  }

  public static CadastrarPedidoRequest gerarCadastrarPedidoRequestProdutoNaoExiste() {
    return new CadastrarPedidoRequest(
        1L,
        List.of(ItemPedidoUtil.gerarItemPedidoDtoProdutoNaoExiste())
    );
  }
}
