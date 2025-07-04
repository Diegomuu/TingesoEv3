package Diego.KartingBack.services;

import Diego.KartingBack.entities.ClienteEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TarifaService {

    public Double calcularTarifaBase(int vueltas) {
        switch (vueltas) {
            case 10:
                return 15000.0;
            case 15:
                return 20000.0;
            case 20:
                return 25000.0;
            default:
                throw new IllegalArgumentException("Número de vueltas no válido");
        }
    }

    public Double calcularTarifaFinal(List<ClienteEntity> clientes, int vueltas) {
        Double tarifaBase = calcularTarifaBase(vueltas);
        Double tarifaTotal = 0.0;

        for (ClienteEntity cliente : clientes) {
            Double descuentoAplicado = 0.0;

            if (cliente.isCumpleanos()) {
                descuentoAplicado = tarifaBase * 0.5; // Descuento especial de cumpleaños
            } else {
                // Evaluamos el descuento más conveniente entre tamaño del grupo y cliente frecuente
                Double descuentoGrupo = obtenerDescuentoPorGrupo(clientes.size(), tarifaBase);
                Double descuentoFrecuente = obtenerDescuentoPorFrecuencia(cliente.getVisitasMensuales(), tarifaBase);

                descuentoAplicado = Math.max(descuentoGrupo, descuentoFrecuente); // Aplicamos el mayor descuento
            }

            tarifaTotal += tarifaBase - descuentoAplicado;
        }

        return tarifaTotal;
    }

    private Double obtenerDescuentoPorGrupo(int cantidadPersonas, Double tarifaBase) {
        if (cantidadPersonas >= 3 && cantidadPersonas <= 5) {
            return tarifaBase * 0.1;
        } else if (cantidadPersonas >= 6 && cantidadPersonas <= 10) {
            return tarifaBase * 0.2;
        } else if (cantidadPersonas >= 11 && cantidadPersonas <= 15) {
            return tarifaBase * 0.3;
        }
        return 0.0;
    }

    private Double obtenerDescuentoPorFrecuencia(int visitasMensuales, Double tarifaBase) {
        if (visitasMensuales >= 7) {
            return tarifaBase * 0.3;
        } else if (visitasMensuales >= 5) {
            return tarifaBase * 0.2;
        } else if (visitasMensuales >= 2) {
            return tarifaBase * 0.1;
        }
        return 0.0;
    }

    public List<Map<String, Object>> calcularTarifasPorCliente(List<ClienteEntity> clientes, int vueltas) {
        Double tarifaBase = calcularTarifaBase(vueltas);
        List<Map<String, Object>> tarifas = new ArrayList<>();

        for (ClienteEntity cliente : clientes) {
            Double descuentoAplicado = 0.0;

            if (cliente.isCumpleanos()) {
                descuentoAplicado = tarifaBase * 0.5; // Descuento especial de cumpleaños
            } else {
                Double descuentoGrupo = obtenerDescuentoPorGrupo(clientes.size(), tarifaBase);
                Double descuentoFrecuente = obtenerDescuentoPorFrecuencia(cliente.getVisitasMensuales(), tarifaBase);
                descuentoAplicado = Math.max(descuentoGrupo, descuentoFrecuente); // Aplicamos el mejor descuento
            }

            Map<String, Object> resultadoCliente = new HashMap<>();
            resultadoCliente.put("nombre", cliente.getNombre());
            resultadoCliente.put("tarifaFinal", tarifaBase - descuentoAplicado);

            tarifas.add(resultadoCliente);
        }

        return tarifas;
    }
}
