package com.fiap.tc.ms.gestao_pedidos.exceptions;

public record CampoErro(
    String campo,
    String mensagem
) {
}
