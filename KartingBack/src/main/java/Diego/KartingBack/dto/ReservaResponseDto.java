package Diego.KartingBack.dto;

public class ReservaResponseDto {
    private String codigoReserva;
    private Double montoTotal;
    private Double ivaTotal;
    private String slotDia;      // p.ej. “MARTES”
    private String slotHora;     // p.ej. “14:00”
    // getters + setters


    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(Double ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public String getSlotDia() {
        return slotDia;
    }

    public void setSlotDia(String slotDia) {
        this.slotDia = slotDia;
    }

    public String getSlotHora() {
        return slotHora;
    }

    public void setSlotHora(String slotHora) {
        this.slotHora = slotHora;
    }
}
