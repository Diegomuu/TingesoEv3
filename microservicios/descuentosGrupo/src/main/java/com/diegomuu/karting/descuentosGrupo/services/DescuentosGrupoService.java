package com.diegomuu.karting.descuentosGrupo.services;

import com.diegomuu.karting.descuentosGrupo.entity.DescuentosGrupoEntity;
import com.diegomuu.karting.descuentosGrupo.repository.DescuentosGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescuentosGrupoService {
    @Autowired
    private DescuentosGrupoRepository descuentoGrupoRepository;

    public void guardarDescuentosGrupo(DescuentosGrupoEntity descuentosGrupo) {
        descuentoGrupoRepository.save(descuentosGrupo);
    }

    public Double obtenerDescuento(int cantidadPersonas) {
        return descuentoGrupoRepository.findTopByMinPersonasLessThanEqualAndMaxPersonasGreaterThanEqual(cantidadPersonas, cantidadPersonas)
                .map(DescuentosGrupoEntity::getDescuento)
                .orElse(0.0); // Si no hay descuento, retorna 0%
    }


}
