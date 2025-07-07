package com.diegomuu.karting.reservas.controller;

import com.diegomuu.karting.reservas.entity.ReservaEntity;
import com.diegomuu.karting.reservas.services.ReservaService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<ReservaEntity> crearReserva(@RequestBody ReservaEntity reserva) {
        ReservaEntity nuevaReserva = reservaService.crearReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    @PutMapping("/confirmar/{id}")
    public ResponseEntity<ReservaEntity> confirmarReserva(@PathVariable Long id) {
        ReservaEntity reservaConfirmada = reservaService.confirmarReserva(id);
        return ResponseEntity.ok(reservaConfirmada);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ReservaEntity> obtenerReservaPorCodigo(@PathVariable String codigo) {
        return reservaService.obtenerReservaPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/comprobante/{codigo}")
    public ResponseEntity<Resource> descargarComprobante(@PathVariable String codigo) throws IOException {
        // 1. Obtener la reserva
        Optional<ReservaEntity> reservaOpt = reservaService.obtenerReservaPorCodigo(codigo);
        if (reservaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 2. Generar el Excel en memoria
        ReservaEntity reserva = reservaOpt.get();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Comprobante de Reserva");

            // ✅ Sección de Información de la Reserva
            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("Código de Reserva:");
            row1.createCell(1).setCellValue(reserva.getCodigo());

            Row row2 = sheet.createRow(1);
            row2.createCell(0).setCellValue("Fecha y Hora:");
            row2.createCell(1).setCellValue(reserva.getFechaReserva() + " " + reserva.getHoraReserva());

            Row row3 = sheet.createRow(2);
            row3.createCell(0).setCellValue("Número de vueltas:");
            row3.createCell(1).setCellValue(reserva.getVueltas());

            Row row4 = sheet.createRow(3);
            row4.createCell(0).setCellValue("Cantidad de personas:");
            row4.createCell(1).setCellValue(reserva.getCantidadPersonas());

            Row row5 = sheet.createRow(4);
            row5.createCell(0).setCellValue("Reservado por:");
            row5.createCell(1).setCellValue(reserva.getNombreReservante());

            // ✅ Encabezado de la tabla de pagos
            Row headerRow = sheet.createRow(6);
            String[] headers = {"Cliente", "Tarifa Base", "Descuento Grupo", "Descuento Frecuencia",
                    "Descuento Cumpleaños", "Monto Final", "IVA", "Total con IVA"};

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Escribir el workbook al ByteArrayOutputStream
            workbook.write(baos);
        }

        // 3. Convertir el Excel a un recurso y devolverlo
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=comprobante_reserva_" + codigo + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/fecha-entre")
    public ResponseEntity<List<ReservaEntity>> obtenerReservasEntreFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    ) {
        try {
            List<ReservaEntity> reservas = reservaService.obtenerReservasEntreFechas(inicio, fin);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}