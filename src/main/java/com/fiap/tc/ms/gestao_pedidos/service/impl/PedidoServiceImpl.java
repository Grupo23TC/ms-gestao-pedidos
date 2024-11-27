package com.fiap.tc.ms.gestao_pedidos.service.impl;

import com.fiap.tc.ms.gestao_pedidos.dto.ItemPedidoDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.*;
import com.fiap.tc.ms.gestao_pedidos.mapper.PedidoMapper;
import com.fiap.tc.ms.gestao_pedidos.model.ItemPedido;
import com.fiap.tc.ms.gestao_pedidos.model.Pagamento;
import com.fiap.tc.ms.gestao_pedidos.model.Pedido;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPagamento;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.repository.PedidoRepository;
import com.fiap.tc.ms.gestao_pedidos.exceptions.PedidoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.service.PedidoService;
import com.fiap.tc.ms.gestao_pedidos.service.ProdutoService;
import com.fiap.tc.ms.gestao_pedidos.utils.MatematicaUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
  private final PedidoRepository pedidoRepository;
  private final ProdutoService produtoService;

  public PedidoServiceImpl(
      PedidoRepository pedidoRepository,
      ProdutoServiceImpl produtoService
      ) {
    this.pedidoRepository = pedidoRepository;
    this.produtoService = produtoService;
  }

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
    Pedido pedido = PedidoMapper.toPedidoSemItems(pedidoRequest);

    adicionarItensNoPedido(pedidoRequest.itensPedido(), pedido);

    BigDecimal valorTotalPedido = MatematicaUtil.calcularValorTotal(pedido.getItensPedido());
    pedido.setValorTotal(valorTotalPedido);
    Pagamento pagamento = gerarPagamento(valorTotalPedido);
    pedido.setPagamento(pagamento);

    Pedido salvo = pedidoRepository.save(pedido);

    atualizarEstoque(salvo.getItensPedido());

    return PedidoMapper.toPedidoResponse(salvo);
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

  private void adicionarItensNoPedido(List<ItemPedidoDTO> itens, Pedido pedido) {
    itens.forEach(item -> {
      ProdutoResponseDTO produto = produtoService.buscarPorId(item.produtoId());
      pedido.getItensPedido().add(new ItemPedido(item.produtoId(), item.quantidade(), produto.valor()));
    });
  }

  private void atualizarEstoque(List<ItemPedido> itens) {
    itens.forEach(item -> {
      AtualizarQuantidadeDTO dto = new AtualizarQuantidadeDTO(item.getQuantidade());
      produtoService.atualizarQuantidade(item.getProdutoId(), dto);
    });
  }

  private Pagamento gerarPagamento(BigDecimal valorTotalPedido) {
    Pagamento pagamento = new Pagamento();
    pagamento.setTitulo("Compra realizada com sucesso");
    pagamento.setDescricao("Pagamento realizado no dia " + Instant.now() + " com o valor total de " + valorTotalPedido);
    pagamento.setValor(valorTotalPedido);
    pagamento.setStatus(StatusPagamento.APROVADO);

    return pagamento;
  }
}
