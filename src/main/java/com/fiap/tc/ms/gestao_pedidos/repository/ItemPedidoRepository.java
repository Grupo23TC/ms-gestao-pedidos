package com.fiap.tc.ms.gestao_pedidos.repository;

import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
