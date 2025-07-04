package Diego.KartingBack.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rack_semanal")
public class RackSemanalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Lunes que marca el inicio de la semana
     */
    private LocalDate semanaInicio;

    @OneToMany(
            mappedBy = "rackSemanal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SlotEntity> slots;

    public RackSemanalEntity() {}

    public RackSemanalEntity(LocalDate semanaInicio, List<SlotEntity> slots) {
        this.semanaInicio = semanaInicio;
        this.slots = slots;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public LocalDate getSemanaInicio() {
        return semanaInicio;
    }

    public void setSemanaInicio(LocalDate semanaInicio) {
        this.semanaInicio = semanaInicio;
    }

    public List<SlotEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotEntity> slots) {
        this.slots = slots;
    }
}
