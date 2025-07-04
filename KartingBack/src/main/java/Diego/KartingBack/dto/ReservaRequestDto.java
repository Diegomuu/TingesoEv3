package Diego.KartingBack.dto;

import jakarta.validation.constraints.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class ReservaRequestDto {
    @NotNull
    private Long clienteId;

    @NotNull
    private LocalDateTime fechaHora;

    @Min(1) @Max(20)
    private int vueltas;

    @Min(1) @Max(15)
    private int cantidadPersonas;

    @NotEmpty
    private List<Long> integrantesIds; // IDs de ClienteEntity para el detalle

    // getters + setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public List<Long> getIntegrantesIds() {
        return integrantesIds;
    }

    public void setIntegrantesIds(List<Long> integrantesIds) {
        this.integrantesIds = integrantesIds;
    }
}

