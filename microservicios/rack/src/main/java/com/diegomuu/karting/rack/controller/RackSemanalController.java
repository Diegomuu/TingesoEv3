package com.diegomuu.karting.rack.controller;


import com.diegomuu.karting.rack.DTO.RackSemanalDTO;
import com.diegomuu.karting.rack.services.RackSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rack")
public class RackSemanalController {

    @Autowired
    private RackSemanalService rackSemanalService;

    @GetMapping("/rack-semanal")
    public ResponseEntity<List<RackSemanalDTO>> obtenerRackSemanal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<RackSemanalDTO> rack = rackSemanalService.obtenerReservasSemana(fecha);
        return ResponseEntity.ok(rack);
    }
}
