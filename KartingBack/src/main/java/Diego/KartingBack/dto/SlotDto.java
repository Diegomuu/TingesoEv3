package Diego.KartingBack.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class SlotDto {
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private boolean ocupado;
    private String codigoReserva; // null si está libre

    public DayOfWeek getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public boolean isOcupado() { return ocupado; }
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    public String getCodigoReserva() { return codigoReserva; }
    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }
}