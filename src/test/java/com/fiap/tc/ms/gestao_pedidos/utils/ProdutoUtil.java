package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;

import java.math.BigDecimal;

public class ProdutoUtil {
  public static ProdutoResponseDTO gerarProdutoResponseDTO() {
    return new ProdutoResponseDTO(1L, "teste", 5, "Teste descricao", BigDecimal.ONE);
  }

  public static AtualizarQuantidadeDTO gerarAtualizarQuantidadeDTO() {
    return new AtualizarQuantidadeDTO(5);
  }
}
