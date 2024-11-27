package com.fiap.tc.ms.gestao_pedidos.service.feign;

import com.fiap.tc.ms.gestao_pedidos.config.feign.FeignConfiguration;
import com.fiap.tc.ms.gestao_pedidos.dto.request.AtualizarQuantidadeDTO;
import com.fiap.tc.ms.gestao_pedidos.dto.response.ProdutoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "produtoClient",
    url = "${application.client.produtos.api.host}",
    configuration = FeignConfiguration.class
)
public interface ProdutoFeignClient {
  @GetMapping("/produtos/{id}")
  ProdutoResponseDTO buscarProdutoPorId(@PathVariable("id") Long id);

  @PutMapping("/produtos/atualizar-quantidade/{id}")
  ProdutoResponseDTO atualizarQuantidade(@PathVariable Long id, @RequestBody AtualizarQuantidadeDTO atualizarQuantidadeDTO);
}
