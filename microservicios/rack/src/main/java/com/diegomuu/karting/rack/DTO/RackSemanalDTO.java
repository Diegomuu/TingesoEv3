package com.diegomuu.karting.rack.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public class RackSemanalDTO {
    private String dia;           // "Lunes", "Martes", etc.
    private LocalDate fecha;      // La fecha específica
    private String horaReserva;   // "10:00", "11:00", etc.
    private String codigoReserva;
    private String nombreReservante;
    private int cantidadPersonas;
    private int vueltas;
    private boolean confirmada;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getNombreReservante() {
        return nombreReservante;
    }

    public void setNombreReservante(String nombreReservante) {
        this.nombreReservante = nombreReservante;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }
}
