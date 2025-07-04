package Diego.KartingBack.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comprobante")
public class ComprobanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_reserva", nullable = false, unique = true)
    private String codigoReserva;

    @Column(name = "fecha_hora_reserva", nullable = false)
    private LocalDateTime fechaHoraReserva;

    @Column(name = "vueltas_reservadas", nullable = false)
    private int vueltasReservadas;

    @Column(name = "cantidad_personas", nullable = false)
    private int cantidadPersonas;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "iva_total", nullable = false)
    private Double ivaTotal;

    /**
     * Relación al cliente que hizo la reserva.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    /**
     * Detalle de pagos por cada integrante.
     * Mapea en DetallePagoEntity.comprobante.
     */
    @OneToMany(
            mappedBy = "comprobante",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetallePagoEntity> detallesPago = new ArrayList<>();

    public ComprobanteEntity() {}

    // Constructor simplificado (sin detallesPago)
    public ComprobanteEntity(String codigoReserva,
                             LocalDateTime fechaHoraReserva,
                             int vueltasReservadas,
                             int cantidadPersonas,
                             ClienteEntity cliente,
                             Double montoTotal,
                             Double ivaTotal) {
        this.codigoReserva = codigoReserva;
        this.fechaHoraReserva = fechaHoraReserva;
        this.vueltasReservadas = vueltasReservadas;
        this.cantidadPersonas = cantidadPersonas;
        this.cliente = cliente;
        this.montoTotal = montoTotal;
        this.ivaTotal = ivaTotal;
    }

    // Métodos de conveniencia para manejar la lista
    public void addDetallePago(DetallePagoEntity detalle) {
        detallesPago.add(detalle);
        detalle.setComprobante(this);
    }

    public void removeDetallePago(DetallePagoEntity detalle) {
        detallesPago.remove(detalle);
        detalle.setComprobante(null);
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public LocalDateTime getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public int getVueltasReservadas() {
        return vueltasReservadas;
    }

    public void setVueltasReservadas(int vueltasReservadas) {
        this.vueltasReservadas = vueltasReservadas;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
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

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public List<DetallePagoEntity> getDetallesPago() {
        return detallesPago;
    }
}
