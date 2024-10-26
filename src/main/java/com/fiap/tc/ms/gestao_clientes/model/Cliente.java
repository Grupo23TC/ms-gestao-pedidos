package com.fiap.tc.ms.gestao_clientes.model;

import jakarta.persistence.*;
import lombok.Data;

    @Entity
    @Table(name = "tb_cliente")
    @Data
    public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long clienteId;

        private String nome;
        private String email;
        private String cpf;
        private int idade;

        private String endereco;
        private String cep;
    }

