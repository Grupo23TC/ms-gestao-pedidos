package com.fiap.tc.ms.gestao_clientes.dto.request;

import jakarta.validation.constraints.Positive;

public record CadastrarClienteRequest(
        @Positive(message = "O id do usuário deve ser positivo")
        Long usuarioId,
        String nome,
        int idade,
        String cpf,
        String endereco,
        String cep
) {
}
