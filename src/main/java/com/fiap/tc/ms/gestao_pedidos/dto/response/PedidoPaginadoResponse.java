package com.fiap.tc.ms.gestao_pedidos.dto.response;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;

public record PedidoPaginadoResponse(
    Long pedidoId,
    StatusPedido statusPedido,
    BigDecimal valorTotal
) {
}
