package com.fiap.tc.ms.gestao_pedidos.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ErroCustomizado {
    @NonNull
    private String erro;
    @NonNull
    private Instant horario;
    @NonNull
    private String rota;
    @NonNull
    private Integer status;
}
