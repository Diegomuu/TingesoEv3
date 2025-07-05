package com.diegomuu.karting.descuentosFrecuencia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DescuentosFrecuenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int minVisitas;
    private int maxVisitas;
    private Double descuento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinVisitas() {
        return minVisitas;
    }

    public void setMinVisitas(int minVisitas) {
        this.minVisitas = minVisitas;
    }

    public int getMaxVisitas() {
        return maxVisitas;
    }

    public void setMaxVisitas(int maxVisitas) {
        this.maxVisitas = maxVisitas;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
