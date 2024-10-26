package com.fiap.tc.ms.gestao_clientes.mapper;

import com.fiap.tc.ms.gestao_clientes.dto.request.CadastrarClienteRequest;
import com.fiap.tc.ms.gestao_clientes.dto.response.ClienteResponse;
import com.fiap.tc.ms.gestao_clientes.model.Cliente;

public class ClienteMapper {


    public static ClienteResponse toClienteResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getClienteId(),
                cliente.getNome(),
                cliente.getIdade(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getEndereco(),
                cliente.getCep()
        );
    }

    public static Cliente toCliente(CadastrarClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(request.usuarioId());
        cliente.setNome(request.nome());
        cliente.setIdade(request.idade());
        cliente.setCpf(request.cpf());
        cliente.setEndereco(request.endereco());
        cliente.setCep(request.cep());

        return cliente;
    }
}
