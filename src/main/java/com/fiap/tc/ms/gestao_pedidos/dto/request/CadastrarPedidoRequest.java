package com.fiap.tc.ms.gestao_pedidos.dto.request;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CadastrarPedidoRequest(
    @Positive(message = "O id do usuário deve ser positivo")
    Long usuarioId,
    @Valid
    List<ItemPedidoDTO> itensPedido
) {
}
