package com.fiap.tc.ms.gestao_pedidos.repository.integracao;

import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;
import com.fiap.tc.ms.gestao_pedidos.repository.ItemPedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemPedidoRepositoryIT {
  @Autowired
  private ItemPedidoRepository repository;

  @Test
  void devePermitirExcluirItemPedido() {
    Long id = 10L;

    repository.deleteById(id);

    Optional<ItemPedido> itemPedido = repository.findById(id);

    assertThat(itemPedido)
        .isNotPresent();
  }
}
