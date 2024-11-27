package com.fiap.tc.ms.gestao_pedidos.service.impl;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.service.ProdutoService;
import com.fiap.tc.ms.gestao_pedidos.service.feign.ProdutoFeignClient;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {
  private final ProdutoFeignClient produtoFeignClient;

  public ProdutoServiceImpl(ProdutoFeignClient produtoFeignClient) {
    this.produtoFeignClient = produtoFeignClient;
  }

  @Override
  public ProdutoResponseDTO buscarPorId(Long id) {
    return produtoFeignClient.buscarProdutoPorId(id);
  }

  @Override
  public ProdutoResponseDTO atualizarQuantidade(Long id, AtualizarQuantidadeDTO atualizarQuantidadeDTO) {
    return produtoFeignClient.atualizarQuantidade(id, atualizarQuantidadeDTO);
  }
}
