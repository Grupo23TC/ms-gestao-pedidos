package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;

public interface ProdutoService {
  ProdutoResponseDTO buscarPorId(Long id);

  ProdutoResponseDTO atualizarQuantidade(Long id, AtualizarQuantidadeDTO atualizarQuantidadeDTO);
}
