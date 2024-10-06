package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.mapper.PedidoMapper;
import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.repository.ItemPedidoRepository;
import com.fiap.tc.ms.gestao_pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
  @Autowired
  private PedidoRepository pedidoRepository;
  @Autowired
  private ItemPedidoRepository itemPedidoRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<PedidoPaginadoResponse> listarPedidos(Pageable pageable) {
    return pedidoRepository.findAll(pageable).map(PedidoMapper::toPedidoPaginado);
  }

  @Override
  @Transactional(readOnly = true)
  public PedidoResponse buscarPedidosPorId(Long id) {
    Pedido pedido = pedidoRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("Pedido com id: " + id + " não encontrado")
    );
    return PedidoMapper.toPedidoResponse(pedido);
  }

  @Override
  @Transactional
  public PedidoResponse cadastrarPedido(CadastrarPedidoRequest pedidoRequest) {
    Pedido pedido = new Pedido();
    pedido.setUsuarioId(pedidoRequest.usuarioId());
    pedido.setStatus(StatusPedido.CRIADO);
    double valorTotal = pedidoRequest
        .itensPedido()
        .stream()
        .mapToDouble(item -> item.preco() * item.quantidade())
        .sum();

    pedido.setValorTotal(valorTotal);

    for(ItemPedidoDto item : pedidoRequest.itensPedido()) {
      ItemPedido itemPedido = new ItemPedido(item.produtoId(), item.quantidade(), item.preco());
      itemPedido.setPedido(pedido);
      pedido.getItensPedido().add(itemPedido);
    }

    return PedidoMapper.toPedidoResponse(pedidoRepository.save(pedido));
  }

  @Override
  @Transactional
  public PedidoDeletadoResponse excluirPedido(Long id) {
    try {
      pedidoRepository.findById(id).orElseThrow(
          () -> new IllegalArgumentException("Pedido com id: " + id + " não encontrado")
      );

      pedidoRepository.deleteById(id);
      return new PedidoDeletadoResponse(true);
    } catch (Exception e) {
      return new PedidoDeletadoResponse(false);
    }


  }

  @Override
  @Transactional
  public PedidoStatusAtualizadoResponse atualizarStatusPedido(
      Long id, AtualizarStatusPedidoRequest statusPedidoRequest
  ) {
    Pedido pedido = pedidoRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("Pedido com id: " + id + " não encontrado")
    );
    pedido.setStatus(statusPedidoRequest.novoStatus());
    return PedidoMapper.toPedidoStatusAtualizado(pedido);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PedidoResponse> buscarPedidosPorUsuario(Long usuarioId) {
    return pedidoRepository.findByUsuarioId(usuarioId).stream().map(PedidoMapper::toPedidoResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PedidoResponse> listarPorStatus(StatusPedido status, Pageable pageable) {
    return pedidoRepository.findByStatus(status, pageable).map(PedidoMapper::toPedidoResponse);
  }

}