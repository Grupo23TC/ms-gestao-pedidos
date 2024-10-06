package com.fiap.tc.ms.gestao_pedidos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "tb_item_pedido")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ItemPedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NonNull
  private Long produtoId;
  @NonNull
  private int quantidade;
  @NonNull
  private double preco;
  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;


}
