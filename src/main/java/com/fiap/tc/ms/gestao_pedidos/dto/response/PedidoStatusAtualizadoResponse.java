package com.fiap.tc.ms.gestao_pedidos.dto.response;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

public record PedidoStatusAtualizadoResponse(
    Long produtoId,
    StatusPedido status
) {
}
