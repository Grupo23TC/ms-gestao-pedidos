package com.fiap.tc.ms.gestao_clientes.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record ClienteResponse(
        Long usuarioId,
        String nome,
        int idade,
        String cpf,
        String email,
        String endereco,
        String cep) {


    public Long getUsuarioId() {
        return usuarioId;
    }

}
