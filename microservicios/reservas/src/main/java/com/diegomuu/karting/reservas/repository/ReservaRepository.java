package com.diegomuu.karting.reservas.repository;

import com.diegomuu.karting.reservas.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity,Long> {
    Optional<ReservaEntity> findByCodigo(String codigo); // Buscar reserva por código único
    List<ReservaEntity> findByFechaReserva(LocalDate fecha);

    @Query("SELECT r FROM ReservaEntity r " +
            "WHERE r.fechaReserva BETWEEN :inicio AND :fin " +
            "ORDER BY r.fechaReserva ASC, r.horaReserva ASC")
    List<ReservaEntity> findByFechaReservaBetweenOrderByHoraReservaAsc(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}
