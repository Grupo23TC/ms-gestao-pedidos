package com.fiap.tc.ms.gestao_pedidos.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
    Long produtoId,
    int quantidade,
    BigDecimal preco
) {
}
