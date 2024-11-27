package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.exceptions.PedidoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.utils.PedidoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {
  @Mock
  private PedidoService pedidoSerivce;


  AutoCloseable openMocks;

  @BeforeEach
  void setUp() {
    openMocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    openMocks.close();
  }

  @Nested
  class BuscarPedidos {
    @Test
    void deveListarPedidosPaginado() {
      PedidoPaginadoResponse pedido1 = PedidoUtil.gerarPedidoPaginadoResponse();
      PedidoPaginadoResponse pedido2 = PedidoUtil.gerarPedidoPaginadoResponse();
      PedidoPaginadoResponse pedido3 = PedidoUtil.gerarPedidoPaginadoResponse();
      List<PedidoPaginadoResponse> listaDePedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2, pedido3));

      Pageable pageRequest = PageRequest.of(0, 10);
      Page<PedidoPaginadoResponse> pedidosPaginado = new PageImpl<>(listaDePedidos, pageRequest, listaDePedidos.size());

      when(pedidoSerivce.listarPedidos(any(Pageable.class))).thenReturn(pedidosPaginado);

      Page<PedidoPaginadoResponse> pedidos = pedidoSerivce.listarPedidos(pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(Page.class)
          .hasSize(listaDePedidos.size());

      assertThat(pedidos.getContent())
          .containsExactlyElementsOf(listaDePedidos);

      verify(pedidoSerivce, times(1)).listarPedidos(any(Pageable.class));
    }

    @Test
    void deveBuscarPedidoPorId() {
      PedidoResponse pedido = PedidoUtil.gerarPedidoResponse();

      when(pedidoSerivce.buscarPedidosPorId(any(Long.class))).thenReturn(pedido);

      PedidoResponse pedidoBuscado = pedidoSerivce.buscarPedidosPorId(pedido.pedidoId());

      assertThat(pedidoBuscado)
          .isNotNull()
          .isInstanceOf(PedidoResponse.class);

      assertThat(pedidoBuscado.pedidoId())
          .isEqualTo(pedido.pedidoId());

      assertThat(pedidoBuscado.status())
          .isEqualTo(pedido.status());

      assertThat(pedidoBuscado.itensPedido())
        .containsExactlyElementsOf(pedido.itensPedido());

      assertThat(pedidoBuscado.usuarioId())
          .isEqualTo(pedido.usuarioId());

      verify(pedidoSerivce, times(1)).buscarPedidosPorId(anyLong());
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedidoPorId_IdNaoEncontrado() {
      Long id = 1L;

      when(pedidoSerivce.buscarPedidosPorId(anyLong()))
          .thenThrow(new PedidoNotFoundException("Pedido com id: " + id + " não encontrado"));

      assertThatThrownBy(() -> pedidoSerivce.buscarPedidosPorId(id))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");
    }

    @Test
    void deveBuscarPedidoPorUsuarioId() {
      PedidoResponse pedido1 = PedidoUtil.gerarPedidoResponse();
      PedidoResponse pedido2 = PedidoUtil.gerarPedidoResponse();
      PedidoResponse pedido3 = PedidoUtil.gerarPedidoResponse();
      List<PedidoResponse> listaDePedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2, pedido3));

      when(pedidoSerivce.buscarPedidosPorUsuario(any(Long.class))).thenReturn(listaDePedidos);

      List<PedidoResponse> pedidos = pedidoSerivce.buscarPedidosPorUsuario(1L);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(List.class)
          .hasSize(listaDePedidos.size());

      assertThat(pedidos)
          .containsExactlyElementsOf(listaDePedidos);

      verify(pedidoSerivce, times(1)).buscarPedidosPorUsuario(anyLong());
    }

    @Test
    void deveRetornarVazio_QuandoBuscarPedidoPorUsuarioId_IdUsuarioNaoEncontrado() {
      when(pedidoSerivce.buscarPedidosPorUsuario(any(Long.class))).thenReturn(List.of());

      List<PedidoResponse> pedidos = pedidoSerivce.buscarPedidosPorUsuario(1L);

      assertThat(pedidos)
          .isEmpty();

      verify(pedidoSerivce, times(1)).buscarPedidosPorUsuario(anyLong());
    }

    @Test
    void deveBuscarPedidosPorStatus() {
      StatusPedido statusPedido = StatusPedido.CRIADO;
      PedidoResponse pedido1 = PedidoUtil.gerarPedidoResponse();
      PedidoResponse pedido2 = PedidoUtil.gerarPedidoResponse();
      PedidoResponse pedido3 = PedidoUtil.gerarPedidoResponse();
      List<PedidoResponse> listaDePedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2, pedido3));

      Pageable pageRequest = PageRequest.of(0, 10);
      Page<PedidoResponse> pedidosPaginado = new PageImpl<>(listaDePedidos, pageRequest, listaDePedidos.size());

      when(pedidoSerivce.listarPorStatus(any(StatusPedido.class), any(Pageable.class))).thenReturn(pedidosPaginado);

      Page<PedidoResponse> pedidos = pedidoSerivce.listarPorStatus(statusPedido, pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(Page.class)
          .hasSize(listaDePedidos.size());

      assertThat(pedidos.getContent())
          .containsExactlyElementsOf(listaDePedidos);

      verify(pedidoSerivce, times(1)).listarPorStatus(any(StatusPedido.class), any(Pageable.class));
    }
  }

  @Nested
  class AtualizarPedido {
    @Test
    void devePermitirAtualizarRastreio() {
      Long id = 1L;
      Long idRastreio = 1L;
      AtualizarRastreioRequest request = new AtualizarRastreioRequest(idRastreio);
      PedidoResponse pedido = PedidoUtil.gerarPedidoResponse();

      when(pedidoSerivce.atualizarRastreio(any(Long.class), any(AtualizarRastreioRequest.class))).thenReturn(pedido);

      PedidoResponse pedidoAtualizado = pedidoSerivce.atualizarRastreio(id, request);

      assertThat(pedidoAtualizado)
          .isNotNull()
          .isInstanceOf(PedidoResponse.class);

      assertThat(pedidoAtualizado.pedidoId())
          .isEqualTo(id);

      assertThat(pedidoAtualizado.codigoRastreio())
          .isEqualTo(idRastreio);

      verify(pedidoSerivce, times(1)).atualizarRastreio(any(Long.class), any(AtualizarRastreioRequest.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarRastreio_IdPedidoNaoEncontrado() {
      Long id = 1L;
      Long idRastreio = 1L;
      AtualizarRastreioRequest request = new AtualizarRastreioRequest(idRastreio);

      when(pedidoSerivce.atualizarRastreio(any(Long.class), any(AtualizarRastreioRequest.class)))
          .thenThrow(new PedidoNotFoundException("Pedido com id: " + id + " não encontrado"));

      assertThatThrownBy(() -> pedidoSerivce.atualizarRastreio(id, request))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");

    }

    @Test
    void devePermitirAtualizarStatusPedido() {
      StatusPedido status = StatusPedido.PROCESSANDO;
      Long id = 1L;
      AtualizarStatusPedidoRequest request = new AtualizarStatusPedidoRequest(status);
      PedidoStatusAtualizadoResponse pedido = PedidoUtil.gerarPedidoStatusAtualizadoResponse();

      when(pedidoSerivce.atualizarStatusPedido(any(Long.class), any(AtualizarStatusPedidoRequest.class))).thenReturn(pedido);

      PedidoStatusAtualizadoResponse pedidoAtualizado = pedidoSerivce.atualizarStatusPedido(id, request);

      assertThat(pedidoAtualizado)
          .isNotNull()
          .isInstanceOf(PedidoStatusAtualizadoResponse.class);

      assertThat(pedidoAtualizado.produtoId())
          .isEqualTo(id);

      assertThat(pedidoAtualizado.status())
          .isEqualTo(status);

      verify(pedidoSerivce, times(1)).atualizarStatusPedido(any(Long.class), any(AtualizarStatusPedidoRequest.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarStatusPedido_IdPedidoNaoEncontrado() {
      Long id = 1L;
      StatusPedido status = StatusPedido.PROCESSANDO;
      AtualizarStatusPedidoRequest request = new AtualizarStatusPedidoRequest(status);

      when(pedidoSerivce.atualizarStatusPedido(any(Long.class), any(AtualizarStatusPedidoRequest.class)))
          .thenThrow(new PedidoNotFoundException("Pedido com id: " + id + " não encontrado"));

      assertThatThrownBy(() -> pedidoSerivce.atualizarStatusPedido(id, request))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");

    }
  }

  @Nested
  class DeletarPedido {
    @Test
    void devePermitirExcluirPedido() {
      Long id = 1L;
      PedidoDeletadoResponse pedido = PedidoUtil.gerarPedidoDeletadoResponse();

      when(pedidoSerivce.excluirPedido(any(Long.class))).thenReturn(pedido);

      PedidoDeletadoResponse response = pedidoSerivce.excluirPedido(id);

      assertThat(response)
          .isNotNull()
          .isInstanceOf(PedidoDeletadoResponse.class);

      assertThat(response.pedidoDeletado())
          .isTrue();

      verify(pedidoSerivce, times(1)).excluirPedido(any(Long.class));
    }

    @Test
    void deveGerarExcecao_QuandoExcluirPedido_IdPedidoNaoEncontrado() {
      Long id = 1L;

      when(pedidoSerivce.excluirPedido(any(Long.class)))
          .thenThrow(new PedidoNotFoundException("Pedido com id: " + id + " não encontrado"));

      assertThatThrownBy(() -> pedidoSerivce.excluirPedido(id))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");
    }
  }

  @Test
  void devePermitirCadastrarPedido() {
    CadastrarPedidoRequest request = PedidoUtil.gerarCadastrarPedidoRequest();
    PedidoResponse pedido = PedidoUtil.gerarPedidoResponse();

    when(pedidoSerivce.cadastrarPedido(any(CadastrarPedidoRequest.class))).thenReturn(pedido);

    PedidoResponse pedidoCriado = pedidoSerivce.cadastrarPedido(request);

    assertThat(pedidoCriado)
      .isNotNull()
      .isInstanceOf(PedidoResponse.class);

    assertThat(pedidoCriado.status())
        .isEqualTo(StatusPedido.CRIADO);

    assertThat(pedidoCriado.itensPedido())
        .hasSize(request.itensPedido().size());

    verify(pedidoSerivce, times(1)).cadastrarPedido(any(CadastrarPedidoRequest.class));
  }
}
