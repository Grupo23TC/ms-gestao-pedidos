package com.fiap.tc.ms.gestao_pedidos.controller;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.service.PedidoService;
import jakarta.validation.Valid;
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
  public ResponseEntity<PedidoResponse> salvarPedido(@Valid @RequestBody CadastrarPedidoRequest pedido) {
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

  @PutMapping("/atualizar-quantidade-item/{id}")
  public ResponseEntity<PedidoResponse> adicionarItemPedido(
      @PathVariable Long id,
      @Valid @RequestBody ItemPedidoDto item
  ) {
    return ResponseEntity.ok(pedidoService.atualizarItem(id, item));
  }

  @GetMapping("/status")
  public ResponseEntity<Page<PedidoResponse>> listarStatusPedido(
      @RequestParam("status") String status,
      @PageableDefault(page = 0, size = 20) Pageable pageable
      ) {
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

  @PutMapping("atualizar-rastreio/{id}")
  public ResponseEntity<PedidoResponse> atualizarRastreioPedido(
      @PathVariable Long id,
      @RequestBody AtualizarRastreioRequest body
  ) {
    PedidoResponse pedidoAtualizado = pedidoService.atualizarRastreio(id, body);

    return ResponseEntity.ok(pedidoAtualizado);
  }
}
