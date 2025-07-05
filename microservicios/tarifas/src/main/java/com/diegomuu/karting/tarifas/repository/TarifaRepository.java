package com.diegomuu.karting.tarifas.repository;

import com.diegomuu.karting.tarifas.entity.TarifaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<TarifaEntity, Long> {

    Optional<TarifaEntity> findByVueltas(int vueltas);
}
