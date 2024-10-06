package com.fiap.tc.ms.gestao_pedidos.dto.request;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;

import java.util.List;

public record CadastrarPedidoRequest(
    Long usuarioId,
    List<ItemPedidoDto> itensPedido
) {
}
