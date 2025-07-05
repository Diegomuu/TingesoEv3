package com.diegomuu.karting.descuentosFrecuencia.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private String nombre;
    private LocalDate cumpleanos;
    private int visitasMensuales;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate isCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(LocalDate cumpleanos) {
        this.cumpleanos = cumpleanos;
    }

    public int getVisitasMensuales() {
        return visitasMensuales;
    }

    public void setVisitasMensuales(int visitasMensuales) {
        this.visitasMensuales = visitasMensuales;
    }
}
