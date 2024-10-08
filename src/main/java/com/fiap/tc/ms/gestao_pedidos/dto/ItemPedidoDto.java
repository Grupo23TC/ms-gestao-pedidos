package com.fiap.tc.ms.gestao_pedidos.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemPedidoDto(
    @Positive(message = "O id do produto deve ser positivo")
    Long produtoId,
    @PositiveOrZero(message = "A quantidade deve ser um valor positivo ou zero")
    int quantidade,
    @PositiveOrZero(message = "O preço deve ser positivo ou zero")
    double preco
) {
}
