package Diego.KartingBack.entities;

import java.util.List;

public class TarifaRequest {
    private List<ClienteEntity> clientes;
    private int vueltas;

    // Getters y Setters
    public List<ClienteEntity> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEntity> clientes) {
        this.clientes = clientes;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }
}
