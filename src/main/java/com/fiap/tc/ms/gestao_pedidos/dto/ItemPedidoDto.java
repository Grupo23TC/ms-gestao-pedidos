package com.fiap.tc.ms.gestao_pedidos.dto;

public record ItemPedidoDto(
    Long produtoId,
    int quantidade,
    double preco
) {
}
