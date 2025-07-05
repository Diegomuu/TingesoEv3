package com.diegomuu.karting.tarifas.controller;

import com.diegomuu.karting.tarifas.entity.TarifaEntity;
import com.diegomuu.karting.tarifas.model.DatosReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diegomuu.karting.tarifas.services.TarifaService;

import java.util.List;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    public TarifaService tarifaService;

    @GetMapping("/read")
    public List<TarifaEntity> listarTatifas() {
        return tarifaService.getAllTarifas();
    }

    // Create (POST)
    @PostMapping("/create")
    public ResponseEntity<TarifaEntity> createTarifa(@RequestBody TarifaEntity tarifa) {
        TarifaEntity nuevaTarifa = tarifaService.createTarifa(tarifa);
        return new ResponseEntity<>(nuevaTarifa, HttpStatus.CREATED);
    }

    @PostMapping("/calcular")
    public ResponseEntity<List<Double>> calcularTarifa(@RequestBody DatosReserva datos) {
        List<Double> tarifasFinales = tarifaService.calcularTarifa(datos);
        return ResponseEntity.ok(tarifasFinales);
    }



}
