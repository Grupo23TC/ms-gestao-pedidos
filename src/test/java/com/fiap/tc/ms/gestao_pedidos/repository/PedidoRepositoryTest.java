package com.fiap.tc.ms.gestao_pedidos.repository;

import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoRepositoryTest {
  @Mock
  private PedidoRepository repository;
  AutoCloseable openMocks;

  @BeforeEach
  void setUp() {
    openMocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    openMocks.close();
  }

  @Test
  void deveCadastrarPedido() {
    Pedido pedido = PedidoUtil.gerarPedido();

    when(repository.save(any(Pedido.class))).thenAnswer(answer -> answer.getArgument(0));

    Pedido pedidoSalvo = repository.save(pedido);

    assertThat(pedidoSalvo)
        .isNotNull()
        .isInstanceOf(Pedido.class)
        .isEqualTo(pedido);

    assertThat(pedidoSalvo.getPedidoId())
        .isEqualTo(pedido.getPedidoId());

    verify(repository, times(1)).save(any(Pedido.class));
  }

  @Nested
  class BuscarPedidos {
    @Test
    void deveListarPedidosPaginado() {
      Pedido pedido1 = PedidoUtil.gerarPedido();
      Pedido pedido2 = PedidoUtil.gerarPedido();
      Pedido pedido3 = PedidoUtil.gerarPedido();
      List<Pedido> listaDePedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2, pedido3));

      Pageable pageRequest = PageRequest.of(0, 10);
      Page<Pedido> pedidosPaginado = new PageImpl<>(listaDePedidos, pageRequest, listaDePedidos.size());

      when(repository.findAll(any(Pageable.class))).thenReturn(pedidosPaginado);

      Page<Pedido> pedidos = repository.findAll(pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(Page.class)
          .hasSize(listaDePedidos.size());

      assertThat(pedidos.getContent())
          .containsExactlyElementsOf(listaDePedidos);

      verify(repository, times(1)).findAll(pageRequest);
    }

    @Test
    void deveBuscarPedidoPorId() {
      Pedido pedido = PedidoUtil.gerarPedido();

      when(repository.findById(any(Long.class))).thenReturn(Optional.of(pedido));

      Optional<Pedido> pedidoOptional = repository.findById(pedido.getPedidoId());

      assertThat(pedidoOptional)
        .isNotNull()
        .isPresent()
        .isInstanceOf(Optional.class);

      assertThat(pedidoOptional.get().getPedidoId())
          .isEqualTo(pedido.getPedidoId());

      verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void deveRetornarVazio_QuandoBuscarPedidoPorId_IdPedidoNaoEncontrado() {
      when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

      Optional<Pedido> pedidoOptional = repository.findById(1L);

      assertThat(pedidoOptional)
          .isEmpty();

      verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void deveBuscarPedidoPorUsuarioId() {
      Pedido pedido1 = PedidoUtil.gerarPedido();
      Pedido pedido2 = PedidoUtil.gerarPedido();
      Pedido pedido3 = PedidoUtil.gerarPedido();
      List<Pedido> listaDePedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2, pedido3));

      when(repository.findByUsuarioId(any(Long.class))).thenReturn(listaDePedidos);

      List<Pedido> pedidos = repository.findByUsuarioId(1L);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(List.class)
          .hasSize(listaDePedidos.size());

      assertThat(pedidos)
          .containsExactlyElementsOf(listaDePedidos);

      verify(repository, times(1)).findByUsuarioId(anyLong());
    }

    @Test
    void deveRetornarVazio_QuandoBuscarPedidoPorUsuarioId_IdUsuarioNaoEncontrado() {
      when(repository.findByUsuarioId(any(Long.class))).thenReturn(List.of());

      List<Pedido> pedidos = repository.findByUsuarioId(1L);

      assertThat(pedidos)
          .isEmpty();

      verify(repository, times(1)).findByUsuarioId(anyLong());
    }

    @Test
    void deveBuscarPedidosPorStatus() {
      StatusPedido statusPedido = StatusPedido.CRIADO;
      Pedido pedido1 = PedidoUtil.gerarPedido();
      Pedido pedido2 = PedidoUtil.gerarPedido();
      Pedido pedido3 = PedidoUtil.gerarPedido();
      List<Pedido> listaDePedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2, pedido3));

      Pageable pageRequest = PageRequest.of(0, 10);
      Page<Pedido> pedidosPaginado = new PageImpl<>(listaDePedidos, pageRequest, listaDePedidos.size());

      when(repository.findByStatus(any(StatusPedido.class), any(Pageable.class))).thenReturn(pedidosPaginado);

      Page<Pedido> pedidos = repository.findByStatus(statusPedido, pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(Page.class)
          .hasSize(listaDePedidos.size());

      assertThat(pedidos.getContent())
          .containsExactlyElementsOf(listaDePedidos);

      verify(repository, times(1)).findByStatus(any(StatusPedido.class), any(Pageable.class));
    }
  }

  @Test
  void deveExcluirPedido() {
    Long id = 1L;

    doNothing().when(repository).deleteById(any(Long.class));

    repository.deleteById(id);

    verify(repository, times(1)).deleteById(anyLong());
  }
}
