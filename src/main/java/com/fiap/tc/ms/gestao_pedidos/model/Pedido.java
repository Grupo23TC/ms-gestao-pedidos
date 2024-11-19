package com.fiap.tc.ms.gestao_pedidos.model;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@Data
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pedidoId;
  private Long usuarioId;
  private Long codigoRastreio;
  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
  private final List<ItemPedido> itensPedido = new ArrayList<>();
  private double valorTotal;
  @Enumerated(EnumType.STRING)
  private StatusPedido status;
  @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
  private Pagamento pagamento;

}
