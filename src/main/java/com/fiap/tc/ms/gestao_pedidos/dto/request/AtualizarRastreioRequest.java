package com.fiap.tc.ms.gestao_pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarRastreioRequest(
    @NotBlank(message = "O campo não pode estar vazio")
    Long codigoRastreio
) {
}
