package com.fiap.tc.ms.gestao_pedidos.controller;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
  @Autowired
  private PedidoService pedidoService;

  @GetMapping
  public ResponseEntity<Page<PedidoPaginadoResponse>> listarPedidos(
      @PageableDefault(page = 0, size = 20) Pageable pageable
  ) {
    return ResponseEntity.ok(pedidoService.listarPedidos(pageable));
  }

  @PostMapping
  public ResponseEntity<PedidoResponse> salvarPedido(@RequestBody CadastrarPedidoRequest pedido) {
    PedidoResponse pedidoSalvo = pedidoService.cadastrarPedido(pedido);
    URI uri = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(pedidoSalvo.pedidoId())
        .toUri();

    return ResponseEntity.created(uri).body(pedidoSalvo);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<PedidoDeletadoResponse> deletarPedido(@PathVariable Long id) {
    return ResponseEntity.ok(pedidoService.excluirPedido(id));
  }

  @PutMapping("/atualizar-status/{id}")
  public ResponseEntity<PedidoStatusAtualizadoResponse> atualizarStatusPedido(
      @PathVariable Long id,
      @RequestBody AtualizarStatusPedidoRequest status
  ) {
    return ResponseEntity.ok(pedidoService.atualizarStatusPedido(id, status));
  }

  @GetMapping("/status")
  public ResponseEntity<Page<PedidoResponse>> listarStatusPedido(
      @RequestParam("status") String status,
      @PageableDefault(page = 0, size = 20) Pageable pageable
      ) {
    System.out.println(status);
    return ResponseEntity.ok(pedidoService.listarPorStatus(StatusPedido.valueOf(status.toUpperCase()), pageable));
  }

  @GetMapping("/usuario")
  public ResponseEntity<List<PedidoResponse>> listarPedidosPorUsuario(@RequestParam("id") Long id) {
    return ResponseEntity.ok(pedidoService.buscarPedidosPorUsuario(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoResponse> buscarPedidoPorId(@PathVariable Long id) {
    return ResponseEntity.ok(pedidoService.buscarPedidosPorId(id));
  }
}
