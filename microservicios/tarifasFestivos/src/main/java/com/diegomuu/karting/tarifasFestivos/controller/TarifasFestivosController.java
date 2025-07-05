package com.diegomuu.karting.tarifasFestivos.controller;


import com.diegomuu.karting.tarifasFestivos.entity.TarifasFestivosEntity;
import com.diegomuu.karting.tarifasFestivos.services.TarifaFestivosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/festivos")
public class TarifasFestivosController {

    @Autowired
    private TarifaFestivosService tarifaEspecialService;

    @PostMapping("/crear")
    public ResponseEntity<String> agregarFechaEspecial(@RequestBody TarifasFestivosEntity nuevaTarifa) {
        tarifaEspecialService.guardarTarifaEspecial(nuevaTarifa);
        return ResponseEntity.ok("Tarifa especial registrada para " + nuevaTarifa.getFecha());
    }

    @GetMapping("/{fecha}")
    public Double obtenerTarifaEspecial(@PathVariable String fecha) {
        return tarifaEspecialService.obtenerDescuento(fecha);
    }


}
