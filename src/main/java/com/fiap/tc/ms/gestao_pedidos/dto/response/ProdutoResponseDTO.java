package com.fiap.tc.ms.gestao_pedidos.dto.response;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    int quantidadeEstoque,
    String descricao,
    BigDecimal valor
) {
}
