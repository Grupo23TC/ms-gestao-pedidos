package com.fiap.tc.ms.gestao_pedidos.service;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDto;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoPaginadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.exceptions.StatusPedidoInvalidoException;
import com.fiap.tc.ms.gestao_pedidos.mapper.PedidoMapper;
import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.repository.ItemPedidoRepository;
import com.fiap.tc.ms.gestao_pedidos.repository.PedidoRepository;
import com.fiap.tc.ms.gestao_pedidos.exceptions.PedidoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.utils.MatematicaUtil;
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
    Pedido pedido = buscarPedidoPorIdOuLancarExcecao(id);
    return PedidoMapper.toPedidoResponse(pedido);
  }

  @Override
  @Transactional
  public PedidoResponse cadastrarPedido(CadastrarPedidoRequest pedidoRequest) {
    Pedido pedido = PedidoMapper.toPedido(pedidoRequest);

    return PedidoMapper.toPedidoResponse(pedidoRepository.save(pedido));
  }

  @Override
  @Transactional
  public PedidoDeletadoResponse excluirPedido(Long id) {
    buscarPedidoPorIdOuLancarExcecao(id);

    pedidoRepository.deleteById(id);
    return new PedidoDeletadoResponse(true);
  }

  @Override
  @Transactional
  public PedidoStatusAtualizadoResponse atualizarStatusPedido(
      Long id, AtualizarStatusPedidoRequest statusPedidoRequest
  ) {
    Pedido pedido = buscarPedidoPorIdOuLancarExcecao(id);
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

  @Override
  @Transactional
  public PedidoResponse atualizarItem(Long id, ItemPedidoDto itemPedidoRequest) {
    Pedido pedidoBuscado = buscarPedidoPorIdOuLancarExcecao(id);

    if(
        !pedidoBuscado.getStatus().equals(StatusPedido.CRIADO)
        && !pedidoBuscado.getStatus().equals(StatusPedido.PROCESSANDO)
    ) {
      throw new StatusPedidoInvalidoException("O pedido não pode mais ser editado ");
    }

    ItemPedido item = buscarItemNoPedido(pedidoBuscado, itemPedidoRequest.produtoId());

    if(item != null && itemPedidoRequest.quantidade() != 0) {
      atualizarItemPedido(item, itemPedidoRequest);
    } else if(item != null) {
      excluirItemPedido(pedidoBuscado, item);
    } else {
      adicionarNovoItem(pedidoBuscado, itemPedidoRequest);
    }

    atualizarValorTotalPedido(pedidoBuscado);

    return PedidoMapper.toPedidoResponse(pedidoRepository.save(pedidoBuscado));
  }

  @Override
  @Transactional
  public PedidoResponse atualizarRastreio(Long id, AtualizarRastreioRequest body) {
    Pedido pedido = buscarPedidoPorIdOuLancarExcecao(id);
    pedido.setCodigoRastreio(body.codigoRastreio());

    return PedidoMapper.toPedidoResponse(pedidoRepository.save(pedido));
  }

  private Pedido buscarPedidoPorIdOuLancarExcecao(Long id) {
    return pedidoRepository.findById(id).orElseThrow(
        () -> new PedidoNotFoundException("Pedido com id: " + id + " não encontrado")
    );
  }

  private ItemPedido buscarItemNoPedido(Pedido pedido, Long produtoId) {
    return pedido.getItensPedido().stream()
        .filter(item -> item.getProdutoId().equals(produtoId))
        .findFirst()
        .orElse(null);
  }

  private void atualizarItemPedido(ItemPedido itemExistente, ItemPedidoDto itemDto) {
    itemExistente.setQuantidade(itemDto.quantidade());
    itemExistente.setPreco(itemDto.preco());
  }

  private void adicionarNovoItem(Pedido pedido, ItemPedidoDto itemDto) {
    ItemPedido novoItem = new ItemPedido(itemDto.produtoId(), itemDto.quantidade(), itemDto.preco());
    novoItem.setPedido(pedido);
    pedido.getItensPedido().add(novoItem);
  }

  @Transactional
  protected void excluirItemPedido(Pedido pedido, ItemPedido itemExistente) {
    itemPedidoRepository.deleteById(itemExistente.getId());
    pedido.getItensPedido().remove(itemExistente);
  }

  private void atualizarValorTotalPedido(Pedido pedido) {
    double novoValorTotal = MatematicaUtil.calcularValorTotal(pedido.getItensPedido());
    pedido.setValorTotal(novoValorTotal);
  }
}
