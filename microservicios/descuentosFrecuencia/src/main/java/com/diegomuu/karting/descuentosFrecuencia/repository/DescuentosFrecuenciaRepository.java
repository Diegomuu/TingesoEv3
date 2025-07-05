package com.diegomuu.karting.descuentosFrecuencia.repository;

import com.diegomuu.karting.descuentosFrecuencia.entity.DescuentosFrecuenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DescuentosFrecuenciaRepository extends JpaRepository<DescuentosFrecuenciaEntity,Long> {
    Optional<DescuentosFrecuenciaEntity> findTopByMinVisitasLessThanEqualAndMaxVisitasGreaterThanEqual(int minVisitas, int maxVisitas);
}
