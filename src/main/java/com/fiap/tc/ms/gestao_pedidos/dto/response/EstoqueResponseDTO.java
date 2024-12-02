package com.fiap.tc.ms.gestao_pedidos.dto.response;

import java.time.LocalDate;

public record EstoqueResponseDTO(
    Long id,
    ProdutoResponseDTO produto,
    LocalDate dataEntrada,
    LocalDate dataSaida,
    int quantidade
) {
}
