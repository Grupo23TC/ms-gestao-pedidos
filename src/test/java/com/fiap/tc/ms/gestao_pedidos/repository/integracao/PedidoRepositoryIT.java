package com.fiap.tc.ms.gestao_pedidos.repository.integracao;

import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.repository.PedidoRepository;
import com.fiap.tc.ms.gestao_pedidos.utils.PedidoUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PedidoRepositoryIT {
  @Autowired
  private PedidoRepository repository;

  @Test
  void deveCadastrarPedido() {
    Pedido pedido = PedidoUtil.gerarPedido();

    Pedido pedidoSalvo = repository.save(pedido);

    assertThat(pedidoSalvo)
        .isNotNull()
        .isInstanceOf(Pedido.class);

    assertThat(pedidoSalvo.getPedidoId())
        .isEqualTo(pedido.getPedidoId());

    assertThat(pedidoSalvo.getStatus())
        .isEqualTo(StatusPedido.CRIADO);

  }

  @Nested
  class BuscarPedido {
    @Test
    void deveListarPedidosPaginados() {
      PageRequest pageRequest = PageRequest.of(0, 10);

      Page<Pedido> pedidos = repository.findAll(pageRequest);

      assertThat(pedidos)
          .isNotNull()
          .isInstanceOf(Page.class)
          .isNotEmpty()
          .hasSizeGreaterThan(0);

    }

    @Test
    void deveBuscarPedidoPorId() {
      Long id = 1L;

      Optional<Pedido> pedido = repository.findById(id);

      assertThat(pedido)
        .isNotNull()
        .isPresent();

      assertThat(pedido.get().getPedidoId())
          .isEqualTo(id);
    }

    @Test
    void deveRetornarVazio_QuandoBuscarPedidoPorId_IdPedidoNaoEncontrado() {
      Long id = 100L;

      Optional<Pedido> pedido = repository.findById(id);

      assertThat(pedido)
          .isEmpty();
    }

    @Test
    void deveBuscarPedidoPorUsuarioId() {
      Long idUsuario = 1L;

      List<Pedido> pedidos = repository.findByUsuarioId(idUsuario);

      assertThat(pedidos)
          .isNotEmpty()
          .hasSizeGreaterThan(0)
          .isInstanceOf(List.class);
    }

    @Test
    void deveRetornarVazio_QuandoBuscarPedidoPorUsuarioId_UsuarioIdNaoEncontrado() {
      Long idUsuario = 100L;

      List<Pedido> pedidos = repository.findByUsuarioId(idUsuario);

      assertThat(pedidos)
          .isEmpty();
    }

    @Test
    void deveBuscarPedidosPorStatus() {
      StatusPedido status = StatusPedido.CRIADO;
      PageRequest pageRequest = PageRequest.of(0, 10);

      Page<Pedido> pedidos = repository.findByStatus(status, pageRequest);

      assertThat(pedidos)
          .isNotEmpty()
          .hasSizeGreaterThan(0)
          .isInstanceOf(Page.class);

      assertThat(pedidos.getContent().get(0).getStatus())
          .isEqualTo(status);
    }
  }

  @Test
  void deveExcluirPedido() {
    Long id = 5L;

    repository.deleteById(id);

    Optional<Pedido> pedido = repository.findById(id);

    assertThat(pedido)
        .isNotPresent();
  }
}
