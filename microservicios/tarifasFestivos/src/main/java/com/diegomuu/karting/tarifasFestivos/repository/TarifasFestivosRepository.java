package com.diegomuu.karting.tarifasFestivos.repository;

import com.diegomuu.karting.tarifasFestivos.entity.TarifasFestivosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifasFestivosRepository extends JpaRepository<TarifasFestivosEntity,Long> {

    Optional<TarifasFestivosEntity> findByFecha(String fecha);



}
