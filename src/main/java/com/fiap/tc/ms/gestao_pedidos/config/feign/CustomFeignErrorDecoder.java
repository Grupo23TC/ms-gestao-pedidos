package com.fiap.tc.ms.gestao_pedidos.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ErroCustomizado;
import com.fiap.tc.ms.gestao_pedidos.exceptions.ProdutoNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomFeignErrorDecoder implements ErrorDecoder {
  private final ErrorDecoder defaultDecoder = new Default();
  private final ObjectMapper objectMapper = new ObjectMapper(); // Para processar JSON.

  @Override
  public Exception decode(String methodKey, Response response) {
    if (response.status() == HttpStatus.NOT_FOUND.value()) {
      try (InputStream bodyIs = response.body().asInputStream()) {
        String body = new String(bodyIs.readAllBytes(), StandardCharsets.UTF_8);

        ErroCustomizado errorResponse = objectMapper.readValue(body, ErroCustomizado.class);

        return new ProdutoNotFoundException(
            errorResponse.getErro(),
            errorResponse.getHorario(),
            errorResponse.getRota(),
            response.status()
        );
      } catch (IOException e) {
        return new RuntimeException("Erro ao processar o corpo da resposta: " + e.getMessage());
      }
    }
    return defaultDecoder.decode(methodKey, response);
  }
}
