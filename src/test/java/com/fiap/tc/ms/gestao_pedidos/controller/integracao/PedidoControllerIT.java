package com.fiap.tc.ms.gestao_pedidos.controller.integracao;

import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarRastreioRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarStatusPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.dto.request.CadastrarPedidoRequest;
import com.fiap.tc.ms.gestao_pedidos.model.enums.StatusPedido;
import com.fiap.tc.ms.gestao_pedidos.utils.PedidoUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PedidoControllerIT {
  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void deveListarPedidos() {
    given()
        .param("page", 0)
        .param("size", 10)
        .when()
        .get("/pedidos")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoPaginadoResponse.schema.json"));
  }

  @Test
  void deveSalvarPedido() {
    given()
        .contentType(ContentType.JSON)
        .body(PedidoUtil.gerarCadastrarPedidoRequest())
        .when()
        .post("/pedidos")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoResponse.schema.json"));
  }

  @Test
  void deveRetornarBadRequest_QuandoProdutoNaoEncontrado() {
    given()
        .contentType(ContentType.JSON)
        .body(PedidoUtil.gerarCadastrarPedidoRequestProdutoNaoExiste())
        .when()
        .post("/pedidos")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body(matchesJsonSchemaInClasspath("schemas/erroCustomizado.schema.json"));
  }

  @Test
  void deveDeletarPedido() {
    given()
        .when()
        .delete("/pedidos/{id}", 3)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoDeletado.schema.json"));
  }

  @Test
  void deveAtualizarStatusPedido() {
    AtualizarStatusPedidoRequest request = new AtualizarStatusPedidoRequest(StatusPedido.ENVIADO);

    given()
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .put("/pedidos/atualizar-status/{id}", 1)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoStatusAtualizado.schema.json"));
  }

  @Test
  void deveListarStatusPedido() {
    given()
        .param("status", "CRIADO")
        .when()
        .get("/pedidos/status")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoResponsePaginado.schema.json"));
  }

  @Test
  void deveListarPedidosPorUsuario() {
    given()
        .param("id", 1)
        .when()
        .get("/pedidos/usuario")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/listaPedidoUsuario.schema.json"));
  }

  @Test
  void deveBuscarPedidoPorId() {
    given()
        .when()
        .get("/pedidos/{id}", 1)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoResponse.schema.json"));
  }

  @Test
  void deveRetornarNotFound_QuandoPedidoNaoEncontrado() {
    given()
        .when()
        .get("/pedidos/{id}", 999)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(matchesJsonSchemaInClasspath("schemas/erroCustomizado.schema.json"));
  }

  @Test
  void deveAtualizarRastreioPedido() {
    AtualizarRastreioRequest request = new AtualizarRastreioRequest(1L);

    given()
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .put("/pedidos/atualizar-rastreio/{id}", 2)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(matchesJsonSchemaInClasspath("schemas/pedidoResponse.schema.json"));
  }
}
