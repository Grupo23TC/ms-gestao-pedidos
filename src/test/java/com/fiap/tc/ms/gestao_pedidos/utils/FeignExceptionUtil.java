package com.fiap.tc.ms.gestao_pedidos.utils;

import feign.FeignException;
import feign.Request;
import feign.Response;

import java.util.Map;

public class FeignExceptionUtil {
  public static FeignException gerarFeignException(int status) {
    return FeignException.errorStatus(
        "error",
        Response.builder()
            .status(status)
            .reason("Feign Error")
            .request(Request.create(Request.HttpMethod.GET, "/fake", Map.of(), null, null, null))
            .build()
    );
  }
}
