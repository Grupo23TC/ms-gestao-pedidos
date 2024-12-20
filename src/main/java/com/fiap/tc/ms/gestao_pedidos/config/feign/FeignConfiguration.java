package com.fiap.tc.ms.gestao_pedidos.config.feign;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {
  @Bean
  public Retryer feignRetryer() {
    return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 2);
  }
}
