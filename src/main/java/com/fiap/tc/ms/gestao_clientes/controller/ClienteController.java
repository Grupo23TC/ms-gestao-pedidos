package com.fiap.tc.ms.gestao_clientes.controller;

import com.fiap.tc.ms.gestao_clientes.dto.request.CadastrarClienteRequest;
import com.fiap.tc.ms.gestao_clientes.dto.response.ClienteDeletadoResponse;
import com.fiap.tc.ms.gestao_clientes.dto.response.ClienteResponse;
import com.fiap.tc.ms.gestao_clientes.service.AtualizarClienteRequest;
import com.fiap.tc.ms.gestao_clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public ResponseEntity<Page<ClienteResponse>> listarClientes(
          @PageableDefault(page = 0, size = 20) Pageable pageable
  ) {
    return ResponseEntity.ok(clienteService.listarClientes(pageable));
  }

  @PostMapping
  public ResponseEntity<ClienteResponse> salvarCliente(@Valid @RequestBody CadastrarClienteRequest cliente) {
    ClienteResponse clienteSalvo = clienteService.cadastrarCliente(cliente);
    URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(clienteSalvo.getUsuarioId())
            .toUri();

    return ResponseEntity.created(uri).body(clienteSalvo);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ClienteDeletadoResponse> deletarCliente(@PathVariable Long id) {
    return ResponseEntity.ok(clienteService.excluirCliente(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClienteResponse> atualizarCliente(
          @PathVariable Long id,
          @Valid @RequestBody AtualizarClienteRequest clienteAtualizado
  ) {
    return ResponseEntity.ok(clienteService.atualizarCliente(id, clienteAtualizado));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponse> buscarClientePorId(@PathVariable Long id) {
    return ResponseEntity.ok(clienteService.buscarClientePorId(id));
  }
}
