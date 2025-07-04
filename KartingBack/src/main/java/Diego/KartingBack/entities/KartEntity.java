package Diego.KartingBack.entities;

import jakarta.persistence.*;
import lombok.*;



@Table(name = "karts")
@Entity
public class KartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long codigo;
    private String estado;
    private String modelo;

    public KartEntity(Long codigo, String estado, String modelo) {
        this.codigo = codigo;
        this.estado = estado;
        this.modelo = modelo;
    }

    public KartEntity() {}

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}




