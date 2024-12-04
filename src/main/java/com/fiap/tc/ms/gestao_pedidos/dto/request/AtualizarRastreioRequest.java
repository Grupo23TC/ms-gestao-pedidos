package com.fiap.tc.ms.gestao_pedidos.dto.request;

import jakarta.validation.constraints.NotNull;

public record AtualizarRastreioRequest(
    @NotNull(message = "O campo código de rastreio não deve ser nulo")
    Long codigoRastreio
) {
}
