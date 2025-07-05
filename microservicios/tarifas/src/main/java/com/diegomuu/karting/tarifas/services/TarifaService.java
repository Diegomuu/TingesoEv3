package com.diegomuu.karting.tarifas.services;

import com.diegomuu.karting.tarifas.entity.TarifaEntity;
import com.diegomuu.karting.tarifas.model.ClienteDTO;
import com.diegomuu.karting.tarifas.model.DatosReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diegomuu.karting.tarifas.repository.TarifaRepository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarifaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TarifaRepository tarifaRepository;

    // Read del crud
    public List<TarifaEntity> getAllTarifas() {
        return tarifaRepository.findAll();
    }

    // Create del crud
    public TarifaEntity createTarifa(TarifaEntity tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public Double obtenerTarifaBase(int vueltas) {
        return tarifaRepository.findByVueltas(vueltas)
                .map(TarifaEntity::getPrecio)
                .orElseThrow(() -> new RuntimeException("No se encontró tarifa para " + vueltas + " vueltas."));
    }


    public List<Double> calcularTarifa(DatosReserva datos) {
        Double tarifaBase = obtenerTarifaBase(datos.getVueltas());
        LocalDate fechaReserva = datos.getFechaReserva();
        List<Double> tarifasFinales = new ArrayList<>();

        for (ClienteDTO cliente : datos.getClientes()) {
            Double descuentoFrecuencia = restTemplate.getForObject("http://descuentosFrecuencia/descuentofrecuencia/" + cliente.getId(), Double.class);
            Double descuentoGrupo = restTemplate.getForObject("http://descuentosGrupo/descuentogrupo/" + datos.getCantidadPersonas(), Double.class);

            // ✅ Comparación de cumpleaños con la fecha enviada
            Double descuentoCumpleanios = cliente.getFechaNacimiento().getMonth().equals(fechaReserva.getMonth()) &&
                    cliente.getFechaNacimiento().getDayOfMonth() == fechaReserva.getDayOfMonth() ? 0.50 : 0.0;

            // ✅ Consulta de descuentos por fecha festiva
            Double descuentoFestivo = restTemplate.getForObject("http://tarifasFestivos/festivos/" + fechaReserva, Double.class);

            // ✅ Determinar el mejor descuento para cada cliente
            Double mejorDescuento = Math.max(descuentoFrecuencia, Math.max(descuentoCumpleanios, Math.max(descuentoGrupo, descuentoFestivo)));

            // ✅ Calcular la tarifa final individual
            Double tarifaFinalCliente = tarifaBase * (1 - mejorDescuento);
            tarifasFinales.add(tarifaFinalCliente);
        }

        return tarifasFinales;
    }
}
