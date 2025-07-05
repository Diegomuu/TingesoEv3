package com.diegomuu.clientes.repository;

import com.diegomuu.clientes.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {

    Optional<ClienteEntity> findById(Long id);
    Optional<ClienteEntity> findByNombreIgnoreCase(String nombre);

}
