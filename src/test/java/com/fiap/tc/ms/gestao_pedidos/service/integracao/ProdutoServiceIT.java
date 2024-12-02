package com.fiap.tc.ms.gestao_pedidos.service.integracao;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ProdutoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.exceptions.SemEstoqueException;
import com.fiap.tc.ms.gestao_pedidos.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProdutoServiceIT {
  @Autowired
  private ProdutoService produtoService;

  @Test
  void deveRetornarProduto_QuandoBuscarPorId_ComSucesso() {
    Long produtoId = 1L;

    ProdutoResponseDTO response = produtoService.buscarPorId(produtoId);

    assertThat(response)
        .isNotNull()
        .isInstanceOf(ProdutoResponseDTO.class);
  }

  @Test
  void deveLancarProdutoNotFoundException_QuandoProdutoNaoExistir() {
    Long produtoId = 10000L;


    assertThatThrownBy(() -> produtoService.buscarPorId(produtoId))
        .isNotNull()
        .isInstanceOf(ProdutoNotFoundException.class)
        .hasMessage("Produto de id: " + produtoId + " não encontrado");
  }

  @Test
  void deveAtualizarQuantidade_ComSucesso() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = new AtualizarQuantidadeDTO(5);

    ProdutoResponseDTO response = produtoService.atualizarQuantidade(produtoId, request);

    assertThat(response)
      .isNotNull()
      .isInstanceOf(ProdutoResponseDTO.class);
  }

  @Test
  void deveLancarSemEstoqueException_QuandoNaoHouverEstoque() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = new AtualizarQuantidadeDTO(500000000);

    SemEstoqueException exception = assertThrows(
        SemEstoqueException.class,
        () -> produtoService.atualizarQuantidade(produtoId, request)
    );

    assertThatThrownBy(() -> produtoService.atualizarQuantidade(produtoId, request))
        .isNotNull()
        .isInstanceOf(SemEstoqueException.class)
        .hasMessage("Quantidade requerida maior do que tem em estoque");
  }
}
