package com.fiap.tc.ms.gestao_pedidos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
  @Setter
  private Pedido pedido;


}
