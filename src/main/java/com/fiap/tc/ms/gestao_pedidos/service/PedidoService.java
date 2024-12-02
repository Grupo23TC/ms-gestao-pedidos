package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoService {
  Page<PedidoPaginadoResponse> listarPedidos(Pageable pageable);

  PedidoResponse buscarPedidosPorId(Long id);

  List<PedidoResponse> buscarPedidosPorUsuario(Long usuarioId);

  PedidoResponse cadastrarPedido(CadastrarPedidoRequest pedido);

  PedidoDeletadoResponse excluirPedido(Long id);

  PedidoStatusAtualizadoResponse atualizarStatusPedido(Long id, AtualizarStatusPedidoRequest status);

  Page<PedidoResponse> listarPorStatus(StatusPedido status, Pageable pageable);

  PedidoResponse atualizarRastreio(Long id, AtualizarRastreioRequest body);

}
