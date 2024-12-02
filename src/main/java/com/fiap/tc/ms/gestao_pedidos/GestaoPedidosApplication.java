package com.fiap.tc.ms.gestao_pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.fiap.tc.ms.gestao_pedidos.service.feign")
public class GestaoPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoPedidosApplication.class, args);
	}

}
