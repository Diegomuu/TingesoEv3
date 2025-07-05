package com.diegomuu.karting.tarifas.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarifa")
@NoArgsConstructor
@AllArgsConstructor
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int vueltas;  // Ej: 10, 15, 20

    @Column(name = "tiempo_max_minutos", nullable = false)
    private int tiempoMaxMinutos;  // Ej: 10, 15, 20

    @Column(nullable = false)
    private double precio;  // Ej: 15000 (CLP)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    public int getTiempoMaxMinutos() {
        return tiempoMaxMinutos;
    }

    public void setTiempoMaxMinutos(int tiempoMaxMinutos) {
        this.tiempoMaxMinutos = tiempoMaxMinutos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

