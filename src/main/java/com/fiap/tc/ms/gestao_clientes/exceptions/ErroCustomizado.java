package com.fiap.tc.ms.gestao_clientes.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class ErroCustomizado {
    private final String erro;
    private final Instant horario;
    private final String rota;
    private final Integer status;
}
