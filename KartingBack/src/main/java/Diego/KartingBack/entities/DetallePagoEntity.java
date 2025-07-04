package Diego.KartingBack.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pago")
public class DetallePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Asociación al comprobante que agrupa este detalle.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comprobante_id", nullable = false)
    private ComprobanteEntity comprobante;

    /**
     * Opcional: si prefieres vincular al cliente persistente,
     * en lugar de guardar solo el nombre.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @Column(name = "tarifa_base", nullable = false)
    private Double tarifaBase;

    @Column(name = "descuento_grupo", nullable = false)
    private Double descuentoGrupo;

    @Column(name = "descuento_especial", nullable = false)
    private Double descuentoEspecial;

    @Column(name = "monto_final", nullable = false)
    private Double montoFinal;

    @Column(nullable = false)
    private Double iva;

    public DetallePagoEntity() {}

    public DetallePagoEntity(ComprobanteEntity comprobante,
                             ClienteEntity cliente,
                             Double tarifaBase,
                             Double descuentoGrupo,
                             Double descuentoEspecial,
                             Double montoFinal,
                             Double iva) {
        this.comprobante       = comprobante;
        this.cliente           = cliente;
        this.tarifaBase        = tarifaBase;
        this.descuentoGrupo    = descuentoGrupo;
        this.descuentoEspecial = descuentoEspecial;
        this.montoFinal        = montoFinal;
        this.iva               = iva;
    }

    //— Conveniencia para mantener bidireccionalidad —
    public void asignarComprobante(ComprobanteEntity comp) {
        this.comprobante = comp;
        comp.getDetallesPago().add(this);
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public ComprobanteEntity getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobanteEntity comprobante) {
        this.comprobante = comprobante;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
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