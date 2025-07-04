package Diego.KartingBack.controllers;

import Diego.KartingBack.dto.RackSemanalDto;
import Diego.KartingBack.dto.SlotDto;
import Diego.KartingBack.entities.RackSemanalEntity;
import Diego.KartingBack.entities.SlotEntity;
import Diego.KartingBack.services.RackSemanalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rack")
public class RackController {

    private final RackSemanalService rackService;

    public RackController(RackSemanalService rackService) {
        this.rackService = rackService;
    }

    /**
     * RF7 – Rack Semanal de Ocupación de la Pista
     * @param fecha cualquier fecha dentro de la semana (yyyy-MM-dd)
     */
    @GetMapping("/{fecha}")
    public RackSemanalDto obtenerRack(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha
    ) {
        RackSemanalEntity rack = rackService.getOrCreateRack(fecha);
        return toDto(rack);
    }

    // MAPEADO MANUAL DE ENTITY → DTO
    private RackSemanalDto toDto(RackSemanalEntity rack) {
        RackSemanalDto dto = new RackSemanalDto();
        dto.setSemanaInicio(rack.getSemanaInicio());

        List<SlotDto> slotDtos = new ArrayList<>();
        for (SlotEntity s : rack.getSlots()) {
            SlotDto sd = new SlotDto();
            sd.setDiaSemana(s.getDiaSemana());
            sd.setHoraInicio(s.getHoraInicio());
            sd.setOcupado(s.isOcupado());
            sd.setCodigoReserva(
                    s.getReserva() != null
                            ? s.getReserva().getCodigoReserva()
                            : null
            );
            slotDtos.add(sd);
        }
        dto.setSlots(slotDtos);
        return dto;
    }
}