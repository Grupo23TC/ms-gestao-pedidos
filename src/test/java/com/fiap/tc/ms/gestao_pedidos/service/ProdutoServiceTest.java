package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ProdutoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.exceptions.SemEstoqueException;
import com.fiap.tc.ms.gestao_pedidos.service.feign.ProdutoFeignClient;
import com.fiap.tc.ms.gestao_pedidos.service.impl.ProdutoServiceImpl;
import com.fiap.tc.ms.gestao_pedidos.utils.FeignExceptionUtil;
import com.fiap.tc.ms.gestao_pedidos.utils.ProdutoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

  @Mock
  private ProdutoFeignClient produtoFeignClient;

  @InjectMocks
  private ProdutoServiceImpl produtoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deveRetornarProduto_QuandoBuscarPorId_ComSucesso() {
    Long produtoId = 1L;
    ProdutoResponseDTO mockResponse = ProdutoUtil.gerarProdutoResponseDTO();

    when(produtoFeignClient.buscarProdutoPorId(produtoId)).thenReturn(mockResponse);

    ProdutoResponseDTO response = produtoService.buscarPorId(produtoId);

    assertThat(response)
        .isNotNull()
        .isInstanceOf(ProdutoResponseDTO.class);

    verify(produtoFeignClient, times(1)).buscarProdutoPorId(produtoId);
  }

  @Test
  void deveLancarProdutoNotFoundException_QuandoProdutoNaoExistir() {
    Long produtoId = 100L;

    when(produtoFeignClient.buscarProdutoPorId(produtoId))
        .thenThrow(FeignExceptionUtil.gerarFeignException(HttpStatus.NOT_FOUND.value()));

    assertThatThrownBy(() -> produtoService.buscarPorId(produtoId))
        .isNotNull()
        .isInstanceOf(ProdutoNotFoundException.class)
        .hasMessage("Produto de id: " + produtoId + " não encontrado");

    verify(produtoFeignClient, times(1)).buscarProdutoPorId(produtoId);
  }

  @Test
  void deveLancarRunTimeException_QuandoProdutoNaoExistir() {
    Long produtoId = 100L;

    when(produtoFeignClient.buscarProdutoPorId(produtoId))
        .thenThrow(FeignExceptionUtil.gerarFeignException(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    assertThatThrownBy(() -> produtoService.buscarPorId(produtoId))
        .isNotNull()
        .isInstanceOf(RuntimeException.class);

    verify(produtoFeignClient, times(1)).buscarProdutoPorId(produtoId);
  }

  @Test
  void deveAtualizarQuantidade_ComSucesso() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = ProdutoUtil.gerarAtualizarQuantidadeDTO();
    ProdutoResponseDTO mockResponse = ProdutoUtil.gerarProdutoResponseDTO();

    when(produtoFeignClient.atualizarQuantidade(produtoId, request)).thenReturn(mockResponse);

    ProdutoResponseDTO response = produtoService.atualizarQuantidade(produtoId, request);

    assertThat(response)
        .isNotNull()
        .isInstanceOf(ProdutoResponseDTO.class);

    verify(produtoFeignClient, times(1)).atualizarQuantidade(produtoId, request);
  }

  @Test
  void deveLancarSemEstoqueException_QuandoNaoHouverEstoque() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = ProdutoUtil.gerarAtualizarQuantidadeDTO();

    when(produtoFeignClient.atualizarQuantidade(produtoId, request))
        .thenThrow(FeignExceptionUtil.gerarFeignException(HttpStatus.NOT_FOUND.value()));

    assertThatThrownBy(() -> produtoService.atualizarQuantidade(produtoId, request))
        .isNotNull()
        .isInstanceOf(SemEstoqueException.class)
        .hasMessage("Quantidade requerida maior do que tem em estoque");


    verify(produtoFeignClient, times(1)).atualizarQuantidade(produtoId, request);
  }

  @Test
  void deveLancarSemRunTimeException_QuandoServicoDerErro() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = ProdutoUtil.gerarAtualizarQuantidadeDTO();

    when(produtoFeignClient.atualizarQuantidade(produtoId, request))
        .thenThrow(FeignExceptionUtil.gerarFeignException(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    assertThatThrownBy(() -> produtoService.atualizarQuantidade(produtoId, request))
        .isNotNull()
        .isInstanceOf(RuntimeException.class);


    verify(produtoFeignClient, times(1)).atualizarQuantidade(produtoId, request);
  }
}
