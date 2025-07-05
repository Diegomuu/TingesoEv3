package com.diegomuu.karting.descuentosGrupo.controller;

import com.diegomuu.karting.descuentosGrupo.entity.DescuentosGrupoEntity;
import com.diegomuu.karting.descuentosGrupo.services.DescuentosGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/descuentogrupo")
public class DescuentosGrupoController {

    @Autowired
    private DescuentosGrupoService descuentosGrupoService;

    @PostMapping("/crear")
    public ResponseEntity<String> agregarDescuento(@RequestBody DescuentosGrupoEntity nuevoDescuento) {
        descuentosGrupoService.guardarDescuentosGrupo(nuevoDescuento);
        return ResponseEntity.ok("Descuento registrado para grupos de " + nuevoDescuento.getMinPersonas() + " - " + nuevoDescuento.getMaxPersonas() + " personas.");
    }

    @GetMapping("/{cantidad}")
    public Double obtenerDescuento(@PathVariable int cantidad) {
        return descuentosGrupoService.obtenerDescuento(cantidad);
    }

}
