package com.fiap.tc.ms.gestao_pedidos.service.impl;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ProdutoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.exceptions.SemEstoqueException;
import com.fiap.tc.ms.gestao_pedidos.service.ProdutoService;
import com.fiap.tc.ms.gestao_pedidos.service.feign.ProdutoFeignClient;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {
  private final ProdutoFeignClient produtoFeignClient;

  public ProdutoServiceImpl(ProdutoFeignClient produtoFeignClient) {
    this.produtoFeignClient = produtoFeignClient;
  }

  @Override
  public ProdutoResponseDTO buscarPorId(Long id) {
    try {
      return produtoFeignClient.buscarProdutoPorId(id);
    } catch (FeignException e) {
      if(e.status() == HttpStatus.NOT_FOUND.value()) {
        throw new ProdutoNotFoundException("Produto de id: " + id + " não encontrado");
      } else
        throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public ProdutoResponseDTO atualizarQuantidade(Long id, AtualizarQuantidadeDTO atualizarQuantidadeDTO) {
    try {
      return produtoFeignClient.atualizarQuantidade(id, atualizarQuantidadeDTO);
    } catch (FeignException e) {
      if (e.status() == HttpStatus.NOT_FOUND.value()) {
        throw new SemEstoqueException("Quantidade requerida maior do que tem em estoque");
      } else {
        throw new RuntimeException(e.getMessage());
      }
    }
  }
}
