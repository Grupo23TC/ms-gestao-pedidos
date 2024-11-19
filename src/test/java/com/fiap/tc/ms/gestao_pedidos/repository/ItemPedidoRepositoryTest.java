package com.fiap.tc.ms.gestao_pedidos.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemPedidoRepositoryTest {
  @Mock
  private ItemPedidoRepository repository;
  AutoCloseable openMocks;


  @BeforeEach
  void setUp()  {
    openMocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    openMocks.close();
  }

  @Test
  void devePermitirExcluirItemPedido() {
    Long id = 1L;

    doNothing().when(repository).deleteById(any(Long.class));

    repository.deleteById(id);

    verify(repository, times(1)).deleteById(any(Long.class));
  }
}
