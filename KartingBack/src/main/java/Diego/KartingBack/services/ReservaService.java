package Diego.KartingBack.services;

import Diego.KartingBack.dto.ReservaRequestDto;
import Diego.KartingBack.dto.ReservaResponseDto;
import Diego.KartingBack.entities.*;
import Diego.KartingBack.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    private final ClienteRepository clienteRepo;
    private final RackSemanalRepository rackRepo;
    private final SlotRepository slotRepo;
    private final ComprobanteRepository compRepo;
    private final PricingService pricingService;

    public ReservaService(ClienteRepository clienteRepo,
                          RackSemanalRepository rackRepo,
                          SlotRepository slotRepo,
                          ComprobanteRepository compRepo,
                          PricingService pricingService) {
        this.clienteRepo     = clienteRepo;
        this.rackRepo        = rackRepo;
        this.slotRepo        = slotRepo;
        this.compRepo        = compRepo;
        this.pricingService  = pricingService;
    }

    @Transactional
    public ReservaResponseDto registrarReserva(ReservaRequestDto req) {
        // 1. Calcular lunes de la semana
        LocalDate fecha = req.getFechaHora().toLocalDate();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY);

        // 2. Obtener o crear RackSemanal
        RackSemanalEntity rack = rackRepo
                .findBySemanaInicio(lunes)
                .orElseGet(() -> {
                    var newRack = new RackSemanalEntity(lunes, new ArrayList<>());
                    return rackRepo.save(newRack);
                });

        // 3. Buscar primer slot libre en el día
        DayOfWeek dia = fecha.getDayOfWeek();
        SlotEntity slot = slotRepo
                .findFirstByRackSemanalAndDiaSemanaAndOcupadoFalse(rack, dia)
                .orElseThrow(() -> new IllegalStateException("No hay slots libres para " + dia));

        // 4. Cliente principal (quien paga)
        ClienteEntity clientePrincipal = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // 5. Calcular montos y detalles
        PriceDetails pd = pricingService.calcularPrecio(
                req.getVueltas(),
                req.getCantidadPersonas(),
                req.getIntegrantesIds(),
                req.getFechaHora()
        );

        // 6. Crear Comprobante con totales ya calculados
        String codigo = UUID.randomUUID().toString();
        ComprobanteEntity comp = new ComprobanteEntity(
                codigo,
                req.getFechaHora(),
                req.getVueltas(),
                req.getCantidadPersonas(),
                clientePrincipal,
                pd.getMontoTotal(),
                pd.getIvaTotal()
        );

        // 7. Adjuntar cada detalle de pago al comprobante
        pd.getDetalles().forEach(comp::addDetallePago);

        // 8. Asociar comprobante al slot y marcarlo ocupado
        slot.setReserva(comp);
        slot.setOcupado(true);

        // 9. Persistir comprobante (y detalles) y luego el slot
        compRepo.save(comp);
        slotRepo.save(slot);

        // 10. Construir y devolver la respuesta
        ReservaResponseDto resp = new ReservaResponseDto();
        resp.setCodigoReserva(codigo);
        resp.setMontoTotal(pd.getMontoTotal());
        resp.setIvaTotal(pd.getIvaTotal());
        resp.setSlotDia(dia.name());
        resp.setSlotHora(slot.getHoraInicio().toString());

        return resp;
    }
}