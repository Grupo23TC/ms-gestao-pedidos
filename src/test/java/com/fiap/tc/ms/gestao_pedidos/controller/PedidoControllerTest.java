package com.fiap.tc.ms.gestao_pedidos.controller;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoDeletadoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoResponse;
import com.fiap.tc.ms.gestao_pedidos.dto.response.PedidoStatusAtualizadoResponse;
import com.fiap.tc.ms.gestao_pedidos.exceptions.PedidoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ProdutoNotFoundException;
import com.fiap.tc.ms.gestao_pedidos.handlers.ControllerExceptionHandler;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.service.PedidoService;
import com.fiap.tc.ms.gestao_pedidos.utils.PedidoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class PedidoControllerTest {

  private MockMvc mockMvc;

  @Mock
  private PedidoService pedidoService;

  AutoCloseable mock;

  @BeforeEach
  void setup() {
    mock = MockitoAnnotations.openMocks(this);
    PedidoController controller = new PedidoController(pedidoService);
    PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setCustomArgumentResolvers(pageableResolver)
        .setControllerAdvice(new ControllerExceptionHandler())
        .addFilter((request, response, chain) -> {
          response.setCharacterEncoding("UTF-8");
          chain.doFilter(request, response);
        })
        .build();
  }

  @AfterEach
  void tearDown() throws Exception {
    mock.close();
  }

  @Test
  void deveListarPedidos() throws Exception {
    var pedido = PedidoUtil.gerarPedidoPaginadoResponse();
    var pageable = PageRequest.of(0, 10);
    var page = new PageImpl<>(Collections.singletonList(pedido), pageable, 1);

    when(pedidoService.listarPedidos(any(Pageable.class))).thenReturn(page);

    mockMvc.perform(
            get("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.totalElements").value(1));
  }

  @Test
  void deveSalvarPedido() throws Exception {
    PedidoResponse response = PedidoUtil.gerarPedidoResponse();
    when(pedidoService.cadastrarPedido(any(CadastrarPedidoRequest.class))).thenReturn(response);

    mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"usuarioId\":1,\"itensPedido\":[]}")
        )
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.pedidoId").value(1));
  }

  @Test
  void deveRetornarBadRequest_QuandoProdutoNaoEncontrado() throws Exception {
    when(pedidoService.cadastrarPedido(any(CadastrarPedidoRequest.class)))
        .thenThrow(new ProdutoNotFoundException("Produto não encontrado"));

    mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"usuarioId\":1,\"itensPedido\":[]}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erro").value("Produto não encontrado"))
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void deveDeletarPedido() throws Exception {
    PedidoDeletadoResponse response = PedidoUtil.gerarPedidoDeletadoResponse();
    when(pedidoService.excluirPedido(eq(1L))).thenReturn(response);

    mockMvc.perform(delete("/pedidos/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.pedidoDeletado").value(true));
  }

  @Test
  void deveAtualizarStatusPedido() throws Exception {
    AtualizarStatusPedidoRequest request = new AtualizarStatusPedidoRequest(StatusPedido.ENVIADO);
    PedidoStatusAtualizadoResponse response = new PedidoStatusAtualizadoResponse(1L, StatusPedido.ENVIADO);
    when(pedidoService.atualizarStatusPedido(eq(1L), any(AtualizarStatusPedidoRequest.class))).thenReturn(response);

    mockMvc.perform(put("/pedidos/atualizar-status/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"status\":\"ENVIADO\"}") // JSON de exemplo
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("ENVIADO"));
  }

  @Test
  void deveListarStatusPedido() throws Exception {
    var pedido = PedidoUtil.gerarPedidoResponse();
    var pageable = PageRequest.of(0, 10);
    var page = new PageImpl<>(Collections.singletonList(pedido), pageable, 1);

    when(pedidoService.listarPorStatus(eq(StatusPedido.CRIADO), any(Pageable.class))).thenReturn(page);

    mockMvc.perform(get("/pedidos/status")
            .param("status", "CRIADO"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].pedidoId").value(pedido.pedidoId()));
  }

  @Test
  void deveListarPedidosPorUsuario() throws Exception {
    PedidoResponse pedido1 = PedidoUtil.gerarPedidoResponse();
    PedidoResponse pedido2 = PedidoUtil.gerarPedidoResponse();
    PedidoResponse pedido3 = PedidoUtil.gerarPedidoResponse();
    PedidoResponse pedido4 = PedidoUtil.gerarPedidoResponse();
    PedidoResponse pedido5 = PedidoUtil.gerarPedidoResponse();

    List<PedidoResponse> pedidos = List.of(pedido1, pedido2, pedido3, pedido4, pedido5);
    when(pedidoService.buscarPedidosPorUsuario(eq(1L))).thenReturn(pedidos);

    mockMvc.perform(get("/pedidos/usuario")
            .param("id", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].pedidoId").value(pedido1.pedidoId()))
        .andExpect(jsonPath("$[1].pedidoId").value(pedido2.pedidoId()));
  }

  @Test
  void deveBuscarPedidoPorId() throws Exception {
    Long id = 1L;
    PedidoResponse response = PedidoUtil.gerarPedidoResponse();
    when(pedidoService.buscarPedidosPorId(any(Long.class))).thenReturn(response);

    mockMvc.perform(get("/pedidos/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.pedidoId").value(id));
  }

  @Test
  void deveRetornarNotFound_QuandoPedidoNaoEncontrado() throws Exception {
    when(pedidoService.buscarPedidosPorId(eq(1L))).thenThrow(new PedidoNotFoundException("Pedido não encontrado"));

    mockMvc.perform(get("/pedidos/1"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.erro").value("Pedido não encontrado"))
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  void deveAtualizarRastreioPedido() throws Exception {
    Long id = 1L;
    PedidoResponse response = PedidoUtil.gerarPedidoResponse();
    when(pedidoService.atualizarRastreio(anyLong(), any(AtualizarRastreioRequest.class))).thenReturn(response);

    mockMvc.perform(put("/pedidos/atualizar-rastreio/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"codigoRastreio\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.codigoRastreio").value(id));
  }
}
