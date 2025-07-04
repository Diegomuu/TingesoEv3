package Diego.KartingBack.services;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.entities.PriceDetails;
import Diego.KartingBack.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PricingService {

    private final ClienteRepository clienteRepo;
    private static final double IVA_RATE = 0.19;

    /** Días feriados fijos; en real lo cargas desde BD o API */
    private static final Set<LocalDate> FERIADOS = Set.of(
            LocalDate.of(2025, 9, 18),
            LocalDate.of(2025, 12, 25)
    );

    public PricingService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public PriceDetails calcularPrecio(
            int vueltas,
            int cantidadPersonas,
            List<Long> integrantesIds,
            LocalDateTime fechaHora
    ) {
        double tarifaBase = obtenerTarifaBase(vueltas);
        List<DetallePagoEntity> detalles = new ArrayList<>();
        double sumaNeta = 0;

        for (Long cid : integrantesIds) {
            ClienteEntity cliente = clienteRepo.findById(cid)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no existe: " + cid));

            double descGrupo      = calcularDescuentoGrupo(cantidadPersonas);
            double descFrecuencia = calcularDescuentoFrecuencia(cliente.getVisitasMensuales());
            double descFestivo    = calcularDescuentoFestivo(fechaHora);
            double descCumple     = calcularDescuentoCumpleanios(fechaHora, cliente);

            double mejorDesc = Collections.max(
                    List.of(descGrupo, descFrecuencia, descFestivo, descCumple)
            );

            double montoNeto = tarifaBase * (1 - mejorDesc);
            double iva       = montoNeto * IVA_RATE;
            sumaNeta += montoNeto;

            DetallePagoEntity det = new DetallePagoEntity();
            det.setCliente(cliente);
            det.setTarifaBase(tarifaBase);
            det.setDescuentoGrupo(descGrupo);
            det.setDescuentoEspecial(mejorDesc);
            det.setMontoFinal(montoNeto);
            det.setIva(iva);
            detalles.add(det);
        }

        double ivaTotal   = sumaNeta * IVA_RATE;
        double montoTotal = sumaNeta + ivaTotal;
        return new PriceDetails(montoTotal, ivaTotal, detalles);
    }

    public double obtenerTarifaBase(int vueltas) {
        return switch (vueltas) {
            case 10 -> 15_000.0;
            case 15 -> 20_000.0;
            case 20 -> 25_000.0;
            default -> throw new IllegalArgumentException("Vueltas inválidas: 10,15 o 20");
        };
    }

    public double calcularDescuentoGrupo(int cp) {
        if (cp >= 11) return 0.30;
        if (cp >= 6)  return 0.20;
        if (cp >= 3)  return 0.10;
        return 0.0;
    }

    public double calcularDescuentoFrecuencia(int visitas) {
        if (visitas >= 7) return 0.30;
        if (visitas >= 5) return 0.20;
        if (visitas >= 2) return 0.10;
        return 0.0;
    }

    public double calcularDescuentoFestivo(LocalDateTime fechaHora) {
        LocalDate d = fechaHora.toLocalDate();
        DayOfWeek wd = d.getDayOfWeek();
        if (FERIADOS.contains(d))                   return 0.20;
        if (wd == DayOfWeek.SATURDAY || wd == DayOfWeek.SUNDAY) return 0.10;
        return 0.0;
    }

    public double calcularDescuentoCumpleanios(LocalDateTime fechaHora,
                                               ClienteEntity cliente) {
        // asumimos que cliente.isCumpleanos() ya indica si hoy es su cumpleaños
        return cliente.isCumpleanos() ? 0.50 : 0.0;
    }
}
