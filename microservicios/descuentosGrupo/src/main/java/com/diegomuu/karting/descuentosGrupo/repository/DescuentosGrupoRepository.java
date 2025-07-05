package com.diegomuu.karting.descuentosGrupo.repository;

import com.diegomuu.karting.descuentosGrupo.entity.DescuentosGrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DescuentosGrupoRepository extends JpaRepository<DescuentosGrupoEntity,Long> {
    Optional<DescuentosGrupoEntity> findTopByMinPersonasLessThanEqualAndMaxPersonasGreaterThanEqual(int cantidad, int cantidad2);


}
