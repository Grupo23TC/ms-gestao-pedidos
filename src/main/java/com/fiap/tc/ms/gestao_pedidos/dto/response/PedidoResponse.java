package com.fiap.tc.ms.gestao_pedidos.dto.response;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
    Long pedidoId,
    StatusPedido status,
    BigDecimal valorTotal,
    Long codigoRastreio,
    List<ItemPedidoResponseDTO> itensPedido,
    Long usuarioId
) {
}
