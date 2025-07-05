package com.diegomuu.karting.descuentosFrecuencia.controller;

import com.diegomuu.karting.descuentosFrecuencia.entity.DescuentosFrecuenciaEntity;
import com.diegomuu.karting.descuentosFrecuencia.services.DescuentosFrecuenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/descuentofrecuencia")
@CrossOrigin
public class DescuentosFrecuenciaController {
    @Autowired
    private DescuentosFrecuenciaService descuentoFrecuenciaService;

    @GetMapping("/{clienteId}")
    public Double obtenerDescuento(@PathVariable Long clienteId) {
        return descuentoFrecuenciaService.obtenerDescuento(clienteId);
    }

    @PostMapping("/crear")
    public ResponseEntity<String> agregarDescuento(@RequestBody DescuentosFrecuenciaEntity nuevoDescuento) {
        descuentoFrecuenciaService.guardarDescuento(nuevoDescuento);
        return ResponseEntity.ok("Descuento registrado para clientes con " + nuevoDescuento.getMinVisitas() + " - " + nuevoDescuento.getMaxVisitas() + " visitas.");
    }
}