package Diego.KartingBack.entities;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "slot_reserva")
public class SlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek diaSemana;    // LUNES…DOMINGO

    @Column(nullable = false)
    private LocalTime horaInicio;   // p.ej. 14:00, 14:30

    @Column(nullable = false)
    private boolean ocupado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_semanal_id", nullable = false)
    private RackSemanalEntity rackSemanal;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comprobante_id")
    private ComprobanteEntity reserva;

    public SlotEntity() {}

    public SlotEntity(DayOfWeek diaSemana,
                      LocalTime horaInicio,
                      RackSemanalEntity rackSemanal) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.rackSemanal = rackSemanal;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public RackSemanalEntity getRackSemanal() {
        return rackSemanal;
    }

    public void setRackSemanal(RackSemanalEntity rackSemanal) {
        this.rackSemanal = rackSemanal;
    }

    public ComprobanteEntity getReserva() {
        return reserva;
    }

    public void setReserva(ComprobanteEntity reserva) {
        this.reserva = reserva;
        this.ocupado = (reserva != null);
    }
}
