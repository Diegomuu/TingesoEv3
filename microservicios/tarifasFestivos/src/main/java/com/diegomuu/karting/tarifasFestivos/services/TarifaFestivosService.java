package com.diegomuu.karting.tarifasFestivos.services;

import com.diegomuu.karting.tarifasFestivos.entity.TarifasFestivosEntity;
import com.diegomuu.karting.tarifasFestivos.repository.TarifasFestivosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifaFestivosService {

    @Autowired
    private TarifasFestivosRepository tarifaEspecialRepository;

    public void guardarTarifaEspecial(TarifasFestivosEntity nuevaTarifa) {
        tarifaEspecialRepository.save(nuevaTarifa);
    }

    public Double obtenerDescuento(String fecha) {
        return tarifaEspecialRepository.findByFecha(fecha)
                .map(TarifasFestivosEntity::getDescuento)
                .orElse(0.0); // Si no hay descuento, retorna 0%
    }

}
