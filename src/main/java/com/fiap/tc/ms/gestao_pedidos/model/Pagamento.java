package com.fiap.tc.ms.gestao_pedidos.model;

import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_pagamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
  @Id
  @UuidGenerator
  private String id;
  private String titulo;
  private String descricao;
  private BigDecimal valor;
  @Enumerated(EnumType.STRING)
  private StatusPagamento status;
  @OneToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;
}
