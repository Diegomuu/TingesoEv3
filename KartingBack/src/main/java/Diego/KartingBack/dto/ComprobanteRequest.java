package Diego.KartingBack.dto;

import java.util.List;

public class ComprobanteRequest {
    private String nombreReservante;
    private int vueltasReservadas;
    private List<DetallePagoRequest> detallesPago;

    public ComprobanteRequest() {
    }

    public ComprobanteRequest(String nombreReservante, int vueltasReservadas, List<DetallePagoRequest> detallesPago) {
        this.nombreReservante = nombreReservante;
        this.vueltasReservadas = vueltasReservadas;
        this.detallesPago = detallesPago;
    }

    public String getNombreReservante() {
        return nombreReservante;
    }

    public void setNombreReservante(String nombreReservante) {
        this.nombreReservante = nombreReservante;
    }

    public int getVueltasReservadas() {
        return vueltasReservadas;
    }

    public void setVueltasReservadas(int vueltasReservadas) {
        this.vueltasReservadas = vueltasReservadas;
    }

    public List<DetallePagoRequest> getDetallesPago() {
        return detallesPago;
    }

    public void setDetallesPago(List<DetallePagoRequest> detallesPago) {
        this.detallesPago = detallesPago;
    }
}

