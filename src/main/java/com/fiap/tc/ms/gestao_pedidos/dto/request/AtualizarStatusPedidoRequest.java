package com.fiap.tc.ms.gestao_pedidos.dto.request;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

public record AtualizarStatusPedidoRequest(
    StatusPedido novoStatus
) {
}
