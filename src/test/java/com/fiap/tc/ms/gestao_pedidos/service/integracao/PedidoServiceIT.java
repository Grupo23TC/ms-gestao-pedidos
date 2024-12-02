package com.fiap.tc.ms.gestao_pedidos.service.integracao;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.exceptions.PedidoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.service.PedidoService;
import com.fiap.tc.ms.gestao_pedidos.utils.PedidoUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PedidoServiceIT {
  @Autowired
  private PedidoService pedidoService;

  @Nested
  class BuscarPedidos {
    @Test
    void deveListarPedidosPaginado() {
      Pageable pageRequest = PageRequest.of(0, 10);

      Page<PedidoPaginadoResponse> pedidos = pedidoService.listarPedidos(pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(Page.class)
          .hasSizeGreaterThan(1);

      assertThat(pedidos.getContent().getFirst())
          .isNotNull()
          .isInstanceOf(PedidoPaginadoResponse.class);

    }

    @Test
    void deveBuscarPedidoPorId() {
      Long id = 1L;

      PedidoResponse pedidoBuscado = pedidoService.buscarPedidosPorId(id);

      assertThat(pedidoBuscado)
          .isNotNull()
          .isInstanceOf(PedidoResponse.class);

      assertThat(pedidoBuscado.pedidoId())
          .isEqualTo(id);
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedidoPorId_IdNaoEncontrado() {
      Long id = 100L;

      assertThatThrownBy(() -> pedidoService.buscarPedidosPorId(id))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");

    }

    @Test
    void deveBuscarPedidoPorUsuarioId() {
      Long id = 1L;

      List<PedidoResponse> pedidos = pedidoService.buscarPedidosPorUsuario(id);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(List.class)
          .hasSizeGreaterThan(0);
    }

    @Test
    void deveRetornarVazio_QuandoBuscarPedidoPorUsuarioId_IdUsuarioNaoEncontrado() {
      Long id = 100L;

      List<PedidoResponse> pedidos = pedidoService.buscarPedidosPorUsuario(id);

      assertThat(pedidos)
          .isNotNull()
          .isEmpty();
    }

    @Test
    void deveBuscarPedidosPorStatus() {
      StatusPedido status = StatusPedido.CRIADO;
      Pageable pageRequest = PageRequest.of(0, 10);

      Page<PedidoResponse> pedidos = pedidoService.listarPorStatus(status, pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isNotEmpty()
          .isInstanceOf(Page.class)
          .hasSizeGreaterThan(0);

      assertThat(pedidos.getContent().getFirst())
          .isInstanceOf(PedidoResponse.class);

      assertThat(pedidos.getContent().getFirst().status())
          .isEqualTo(status);
    }
  }

  @Nested
  class AtualizarPedido {
    @Test
    void devePermitirAtualizarRastreio() {
      Long id = 2L;
      Long idRastreio = 1L;
      AtualizarRastreioRequest request = new AtualizarRastreioRequest(idRastreio);

      PedidoResponse pedidoAtualizado = pedidoService.atualizarRastreio(id, request);

      assertThat(pedidoAtualizado)
          .isNotNull()
          .isInstanceOf(PedidoResponse.class);

      assertThat(pedidoAtualizado.pedidoId())
          .isEqualTo(id);

      assertThat(pedidoAtualizado.codigoRastreio())
          .isEqualTo(idRastreio);
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarRastreio_IdPedidoNaoEncontrado() {
      Long id = 100L;
      Long idRastreio = 1L;
      AtualizarRastreioRequest request = new AtualizarRastreioRequest(idRastreio);

      assertThatThrownBy(() -> pedidoService.atualizarRastreio(id, request))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");
    }

    @Test
    void devePermitirAtualizarStatusPedido() {
      StatusPedido status = StatusPedido.PROCESSANDO;
      Long id = 2L;
      AtualizarStatusPedidoRequest request = new AtualizarStatusPedidoRequest(status);

      PedidoStatusAtualizadoResponse pedidoAtualizado = pedidoService.atualizarStatusPedido(id, request);

      assertThat(pedidoAtualizado)
          .isNotNull()
          .isInstanceOf(PedidoStatusAtualizadoResponse.class);

      assertThat(pedidoAtualizado.produtoId())
          .isEqualTo(id);

      assertThat(pedidoAtualizado.status())
          .isEqualTo(status);
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarStatusPedido_IdPedidoNaoEncontrado() {
      Long id = 100L;
      StatusPedido status = StatusPedido.PROCESSANDO;
      AtualizarStatusPedidoRequest request = new AtualizarStatusPedidoRequest(status);

      assertThatThrownBy(() -> pedidoService.atualizarStatusPedido(id, request))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");
    }
  }

  @Nested
  class DeletarPedido {
    @Test
    void devePermitirExcluirPedido() {
      Long id = 4L;

      PedidoDeletadoResponse response = pedidoService.excluirPedido(id);

      assertThat(response)
          .isNotNull()
          .isInstanceOf(PedidoDeletadoResponse.class);

      assertThat(response.pedidoDeletado())
          .isTrue();

    }

    @Test
    void deveGerarExcecao_QuandoExcluirPedido_IdPedidoNaoEncontrado() {
      Long id = 100L;

      assertThatThrownBy(() -> pedidoService.excluirPedido(id))
          .isNotNull()
          .isInstanceOf(PedidoNotFoundException.class)
          .hasMessage("Pedido com id: " + id + " não encontrado");

    }
  }

  @Test
  void devePermitirCadastrarPedido() {
    CadastrarPedidoRequest request = PedidoUtil.gerarCadastrarPedidoRequest();

    PedidoResponse pedidoCriado = pedidoService.cadastrarPedido(request);

    assertThat(pedidoCriado)
        .isNotNull()
        .isInstanceOf(PedidoResponse.class);

    assertThat(pedidoCriado.status())
        .isEqualTo(StatusPedido.CRIADO);

    assertThat(pedidoCriado.itensPedido())
        .hasSize(request.itensPedido().size());
  }
}
