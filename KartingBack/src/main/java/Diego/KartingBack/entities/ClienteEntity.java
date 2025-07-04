package Diego.KartingBack.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int visitasMensuales;
    private boolean cumpleanos;

    public ClienteEntity() {}

    public ClienteEntity(String nombre, int visitasMensuales, boolean cumpleanos) {
        this.nombre = nombre;
        this.visitasMensuales = visitasMensuales;
        this.cumpleanos = cumpleanos;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVisitasMensuales() {
        return visitasMensuales;
    }

    public void setVisitasMensuales(int visitasMensuales) {
        this.visitasMensuales = visitasMensuales;
    }

    public boolean isCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(boolean cumpleanos) {
        this.cumpleanos = cumpleanos;
    }
}

