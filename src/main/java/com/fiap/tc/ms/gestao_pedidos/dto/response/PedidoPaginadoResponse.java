package com.fiap.tc.ms.gestao_pedidos.dto.response;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

public record PedidoPaginadoResponse(
    Long pedidoId,
    StatusPedido statusPedido,
    double valorTotal
) {
}
