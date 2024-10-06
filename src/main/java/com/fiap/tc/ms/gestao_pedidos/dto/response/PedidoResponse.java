package com.fiap.tc.ms.gestao_pedidos.dto.response;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;

import java.util.List;

public record PedidoResponse(
    Long pedidoId,
    StatusPedido status,
    double valorTotal,
    Long codigoRastreio,
    List<ItemPedidoDto> itensPedido,
    Long usuarioId
) {
}
