package com.fiap.tc.ms.gestao_clientes.service;

import com.fiap.tc.ms.gestao_clientes.dto.request.CadastrarClienteRequest;
import com.fiap.tc.ms.gestao_clientes.dto.response.ClienteDeletadoResponse;
import com.fiap.tc.ms.gestao_clientes.dto.response.ClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClienteService {

    Page<ClienteResponse> listarClientes(Pageable pageable);

    ClienteResponse buscarClientePorId(Long id);

    ClienteResponse cadastrarCliente(CadastrarClienteRequest cliente);

    ClienteDeletadoResponse excluirCliente(Long id);

    ClienteResponse atualizarCliente(Long id, AtualizarClienteRequest clienteAtualizado);

    @Transactional(readOnly = true)
    List<ClienteResponse> buscarClientesPorCep(String cep);
}
