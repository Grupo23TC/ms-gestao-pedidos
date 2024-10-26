package com.fiap.tc.ms.gestao_clientes.exceptions;

public record CampoErro(
    String campo,
    String mensagem
) {
}
