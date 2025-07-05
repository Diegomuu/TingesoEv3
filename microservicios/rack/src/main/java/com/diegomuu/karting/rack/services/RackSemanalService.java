package com.diegomuu.karting.rack.services;

import com.diegomuu.karting.rack.DTO.RackSemanalDTO;
import com.diegomuu.karting.rack.DTO.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RackSemanalService {

    @Autowired
    private RestTemplate restTemplate;

    public List<RackSemanalDTO> obtenerReservasSemana(LocalDate fecha) {
        // Obtener el lunes de la semana
        LocalDate inicioSemana = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // Obtener el domingo
        LocalDate finSemana = inicioSemana.plusDays(6);

        try {
            // Llamar al servicio de reservas usando el formato específico
            List<ReservaDTO> reservas = Arrays.asList(
                    restTemplate.getForObject(
                            "http://reservas/reservas/fecha-entre?inicio=" + inicioSemana + "&fin=" + finSemana,
                            ReservaDTO[].class
                    )
            );


            if (reservas != null) {
                return reservas.stream()
                        .map(this::convertirADTO)
                        .collect(Collectors.toList());
            }

            return new ArrayList<>();
        } catch (RestClientException e) {
            // Manejar el error apropiadamente
            System.err.println("Error al obtener reservas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private RackSemanalDTO convertirADTO(ReservaDTO reserva) {
        RackSemanalDTO dto = new RackSemanalDTO();
        dto.setDia(obtenerNombreDia(LocalDate.parse(reserva.getFechaReserva())));
        dto.setFecha(LocalDate.parse(reserva.getFechaReserva()));
        dto.setHoraReserva(reserva.getHoraReserva());
        dto.setCodigoReserva(reserva.getCodigo());
        dto.setNombreReservante(reserva.getNombreReservante());
        dto.setCantidadPersonas(reserva.getCantidadPersonas());
        dto.setVueltas(reserva.getVueltas());
        dto.setConfirmada(reserva.isConfirmada());
        return dto;
    }

    private String obtenerNombreDia(LocalDate fecha) {
        return fecha.getDayOfWeek().getDisplayName(
                TextStyle.FULL,
                new Locale("es", "ES")
        );
    }
}
