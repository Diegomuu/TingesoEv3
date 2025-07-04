package Diego.KartingBack.controllers;

import Diego.KartingBack.dto.ReservaRequestDto;
import Diego.KartingBack.dto.ReservaResponseDto;
import Diego.KartingBack.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@Validated
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * RF5 – Registro de Reservas
     * Recibe datos de reserva, crea el comprobante, asigna slot y devuelve el resumen.
     */
    @PostMapping
    public ResponseEntity<ReservaResponseDto> crearReserva(
            @Valid @RequestBody ReservaRequestDto request
    ) {
        ReservaResponseDto response = reservaService.registrarReserva(request);
        return ResponseEntity.ok(response);
    }

    // Opcional: podrías añadir más endpoints, por ejemplo:
    // @GetMapping("/{codigo}") para buscar una reserva por su código.
}
