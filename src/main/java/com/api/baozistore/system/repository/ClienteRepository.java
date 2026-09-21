package com.api.baozistore.system.repository;

import com.api.baozistore.system.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findAllByOrderByClienteDesdeAsc();
}
