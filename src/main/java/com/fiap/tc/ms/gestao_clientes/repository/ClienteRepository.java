package com.fiap.tc.ms.gestao_clientes.repository;

import com.fiap.tc.ms.gestao_clientes.model.Cliente;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByCep(String cep);

}
