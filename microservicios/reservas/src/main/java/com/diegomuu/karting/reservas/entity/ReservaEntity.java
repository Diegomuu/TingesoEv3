package com.diegomuu.karting.reservas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la reserva

    private String codigo; // Código único para la reserva
    private LocalDate fechaReserva; // Fecha de la reserva
    private LocalTime horaReserva; // Hora exacta de la reserva
    private Integer vueltas; // Número de vueltas reservadas
    private Integer cantidadPersonas; // Cantidad de personas en la reserva
    private String nombreReservante; // Nombre de quien hizo la reserva
    private Double montoTotal; // Monto total calculado
    private Boolean confirmada; // Estado de la reserva (true = confirmada, false = pendiente)

    @ElementCollection // Guardamos IDs en lugar de objetos
    private List<Long> clienteIds; // Lista de IDs de los clientes en la reserva


    // Getters, Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(LocalTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    public Integer getVueltas() {
        return vueltas;
    }

    public void setVueltas(Integer vueltas) {
        this.vueltas = vueltas;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getNombreReservante() {
        return nombreReservante;
    }

    public void setNombreReservante(String nombreReservante) {
        this.nombreReservante = nombreReservante;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Boolean getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(Boolean confirmada) {
        this.confirmada = confirmada;
    }

    public List<Long> getClienteIds() {
        return clienteIds;
    }

    public void setClienteIds(List<Long> clienteIds) {
        this.clienteIds = clienteIds;
    }
}
