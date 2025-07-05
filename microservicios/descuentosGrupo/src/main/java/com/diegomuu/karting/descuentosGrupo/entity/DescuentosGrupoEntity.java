package com.diegomuu.karting.descuentosGrupo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DescuentosGrupoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int minPersonas; // Límite inferior del rango
    private int maxPersonas; // Límite superior del rango
    private Double descuento; // Porcentaje de descuento

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinPersonas() {
        return minPersonas;
    }

    public void setMinPersonas(int minPersonas) {
        this.minPersonas = minPersonas;
    }

    public int getMaxPersonas() {
        return maxPersonas;
    }

    public void setMaxPersonas(int maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
