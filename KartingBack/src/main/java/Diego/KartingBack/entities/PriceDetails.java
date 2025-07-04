package Diego.KartingBack.entities;

import java.util.List;

public class PriceDetails {
    private double montoTotal;
    private double ivaTotal;
    private List<DetallePagoEntity> detalles;

    public PriceDetails(double montoTotal, double ivaTotal, List<DetallePagoEntity> detalles) {
        this.montoTotal = montoTotal;
        this.ivaTotal   = ivaTotal;
        this.detalles   = detalles;
    }
    // getters...

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(double ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public List<DetallePagoEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePagoEntity> detalles) {
        this.detalles = detalles;
    }
}

