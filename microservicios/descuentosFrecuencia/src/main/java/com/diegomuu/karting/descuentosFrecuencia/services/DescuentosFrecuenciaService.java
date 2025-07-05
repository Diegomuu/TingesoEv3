package com.diegomuu.karting.descuentosFrecuencia.services;

import com.diegomuu.karting.descuentosFrecuencia.entity.DescuentosFrecuenciaEntity;
import com.diegomuu.karting.descuentosFrecuencia.model.Cliente;
import com.diegomuu.karting.descuentosFrecuencia.repository.DescuentosFrecuenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DescuentosFrecuenciaService {
    @Autowired
    private DescuentosFrecuenciaRepository descuentoFrecuenciaRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Double obtenerDescuento(Long clienteId) {
        Cliente cliente = restTemplate.getForObject("http://clientes/clientes/" + clienteId, Cliente.class);

        if (cliente == null) {
            return 0.0; // Si el cliente no existe o su cantidad de visitas es desconocida, no hay descuento.
        }

        int visitas = cliente.getVisitasMensuales();

        // Ajustar la consulta para que pase `visitas` como el rango de búsqueda
        return descuentoFrecuenciaRepository.findTopByMinVisitasLessThanEqualAndMaxVisitasGreaterThanEqual(visitas, visitas)
                .map(DescuentosFrecuenciaEntity::getDescuento)
                .orElse(0.0);
    }


    public void guardarDescuento(DescuentosFrecuenciaEntity nuevoDescuento) {
        descuentoFrecuenciaRepository.save(nuevoDescuento);
    }
}
