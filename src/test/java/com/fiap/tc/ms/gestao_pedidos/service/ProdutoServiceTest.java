package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ProdutoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.exceptions.SemEstoqueException;
import com.fiap.tc.ms.gestao_pedidos.service.feign.ProdutoFeignClient;
import com.fiap.tc.ms.gestao_pedidos.service.impl.ProdutoServiceImpl;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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
    ProdutoResponseDTO mockResponse = new ProdutoResponseDTO(1L, "teste", 5, "Teste descricao", BigDecimal.ONE); // Configure as propriedades conforme necessário
    when(produtoFeignClient.buscarProdutoPorId(produtoId)).thenReturn(mockResponse);

    ProdutoResponseDTO response = produtoService.buscarPorId(produtoId);

    assertNotNull(response);
    verify(produtoFeignClient, times(1)).buscarProdutoPorId(produtoId);
  }

  @Test
  void deveLancarProdutoNotFoundException_QuandoProdutoNaoExistir() {
    Long produtoId = 1L;
    when(produtoFeignClient.buscarProdutoPorId(produtoId))
        .thenThrow(gerarFeignException(HttpStatus.NOT_FOUND.value()));

    ProdutoNotFoundException exception = assertThrows(
        ProdutoNotFoundException.class,
        () -> produtoService.buscarPorId(produtoId)
    );

    assertEquals("Produto de id: " + produtoId + " não encontrado", exception.getMessage());
    verify(produtoFeignClient, times(1)).buscarProdutoPorId(produtoId);
  }

  @Test
  void deveAtualizarQuantidade_ComSucesso() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = new AtualizarQuantidadeDTO(5);
    ProdutoResponseDTO mockResponse = new ProdutoResponseDTO(1L, "teste", 5, "Teste descricao", BigDecimal.ONE); // Configure as propriedades conforme necessário
    when(produtoFeignClient.atualizarQuantidade(produtoId, request)).thenReturn(mockResponse);

    ProdutoResponseDTO response = produtoService.atualizarQuantidade(produtoId, request);

    assertNotNull(response);
    verify(produtoFeignClient, times(1)).atualizarQuantidade(produtoId, request);
  }

  @Test
  void deveLancarSemEstoqueException_QuandoNaoHouverEstoque() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = new AtualizarQuantidadeDTO(5);
    when(produtoFeignClient.atualizarQuantidade(produtoId, request))
        .thenThrow(gerarFeignException(HttpStatus.NOT_FOUND.value()));

    SemEstoqueException exception = assertThrows(
        SemEstoqueException.class,
        () -> produtoService.atualizarQuantidade(produtoId, request)
    );

    assertEquals("Quantidade requerida maior do que tem em estoque", exception.getMessage());
    verify(produtoFeignClient, times(1)).atualizarQuantidade(produtoId, request);
  }

  @Test
  void deveLancarRuntimeException_ParaOutroErroFeign() {
    Long produtoId = 1L;
    AtualizarQuantidadeDTO request = new AtualizarQuantidadeDTO(5);
    when(produtoFeignClient.atualizarQuantidade(produtoId, request))
        .thenThrow(gerarFeignException(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> produtoService.atualizarQuantidade(produtoId, request)
    );

    assertNotNull(exception.getMessage());
    verify(produtoFeignClient, times(1)).atualizarQuantidade(produtoId, request);
  }

  private FeignException gerarFeignException(int status) {
    return FeignException.errorStatus(
        "error",
        Response.builder()
            .status(status)
            .reason("Feign Error")
            .request(Request.create(Request.HttpMethod.GET, "/fake", Map.of(), null, null, null))
            .build()
    );
  }
}
