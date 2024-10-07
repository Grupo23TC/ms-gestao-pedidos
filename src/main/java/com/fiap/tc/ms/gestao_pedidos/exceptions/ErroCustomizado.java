package com.fiap.tc.ms.gestao_pedidos.exceptions;

import java.time.Instant;
import java.time.LocalDateTime;

public record ErroCustomizado(
    String erro,
    Instant horario,
    String rota,
    Integer status
) {
}
