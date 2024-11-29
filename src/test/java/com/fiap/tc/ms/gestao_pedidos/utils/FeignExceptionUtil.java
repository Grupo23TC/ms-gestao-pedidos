package com.fiap.tc.ms.gestao_pedidos.utils;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class FeignExceptionUtil {
  public static FeignException gerarFeignExceptionNotFound() {
    Response response = Response.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .reason("Not Found")
        .request(Request.create(Request.HttpMethod.GET, "http://example.com", Collections.emptyMap(), null, new RequestTemplate()))
        .build();

    return FeignException.errorStatus("GET", response);
  }

  public static FeignException gerarFeignExceptionInternalServerError() {
// Create a mock Response with 500 status code
    Response response = Response.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .reason("Internal Server Error")
        .request(Request.create(Request.HttpMethod.GET, "http://example.com", Collections.emptyMap(), null, new RequestTemplate()))
        .build();

    // Return a FeignException using the Response object
    return FeignException.errorStatus("GET", response);
  }
}
