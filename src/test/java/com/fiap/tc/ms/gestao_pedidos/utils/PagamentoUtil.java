package com.fiap.tc.ms.gestao_pedidos.utils;

import com.fiap.tc.ms.gestao_pedidos.model.Pagamento;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPagamento;

import java.math.BigDecimal;
import java.util.UUID;

public class PagamentoUtil {
  public static Pagamento gerarPagamento() {
   Pagamento pagamento = new Pagamento();
    pagamento.setId(UUID.randomUUID().toString());
    pagamento.setTitulo("Pagamento teste");
    pagamento.setDescricao("Descrição teste");
    pagamento.setValor(BigDecimal.valueOf(500.0));
    pagamento.setStatus(StatusPagamento.APROVADO);

    return pagamento;
  }
}
