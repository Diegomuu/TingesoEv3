
package Diego.KartingBack.dto;

public class DetallePagoRequest {
    private String nombreCliente;
    private Double tarifaBase;
    private Double descuentoGrupo;
    private Double descuentoEspecial;
    private Double montoFinal;
    private Double iva;

    public DetallePagoRequest() {}

    public DetallePagoRequest(String nombreCliente, Double tarifaBase, Double descuentoGrupo, Double descuentoEspecial, Double montoFinal, Double iva) {
        this.nombreCliente = nombreCliente;
        this.tarifaBase = tarifaBase;
        this.descuentoGrupo = descuentoGrupo;
        this.descuentoEspecial = descuentoEspecial;
        this.montoFinal = montoFinal;
        this.iva = iva;
    }

    // Getters y Setters
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(Double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public Double getDescuentoGrupo() {
        return descuentoGrupo;
    }

    public void setDescuentoGrupo(Double descuentoGrupo) {
        this.descuentoGrupo = descuentoGrupo;
    }

    public Double getDescuentoEspecial() {
        return descuentoEspecial;
    }

    public void setDescuentoEspecial(Double descuentoEspecial) {
        this.descuentoEspecial = descuentoEspecial;
    }

    public Double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(Double montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }
}
