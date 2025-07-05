package com.diegomuu.karting.reservas.services;

import com.diegomuu.karting.reservas.entity.ReservaEntity;
import com.diegomuu.karting.reservas.repository.ReservaRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    public ReservaEntity crearReserva(ReservaEntity reserva) {
        reserva.setCodigo(UUID.randomUUID().toString()); // Genera un código único
        reserva.setConfirmada(false); // Por defecto, la reserva está pendiente
        return reservaRepository.save(reserva);
    }

    public ReservaEntity confirmarReserva(Long reservaId) {
        ReservaEntity reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setConfirmada(true); // Cambia estado a confirmada
        return reservaRepository.save(reserva);
    }

    public Optional<ReservaEntity> obtenerReservaPorCodigo(String codigo) {
        return reservaRepository.findByCodigo(codigo);
    }

    public void generarComprobanteExcel(ReservaEntity reserva) throws IOException {
        Workbook workbook = new XSSFWorkbook();
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
        String[] headers = {"Cliente", "Tarifa Base", "Descuento Grupo", "Descuento Frecuencia", "Descuento Cumpleaños", "Monto Final", "IVA", "Total con IVA"};

        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // ✅ Guardar archivo
        try (FileOutputStream fileOut = new FileOutputStream("comprobante_reserva_" + reserva.getCodigo() + ".xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    public List<ReservaEntity> obtenerReservasEntreFechas(LocalDate inicio, LocalDate fin) {
        // Validar que las fechas sean válidas
        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha inicial");
        }

        // Buscar las reservas entre las fechas dadas
        return reservaRepository.findByFechaReservaBetweenOrderByHoraReservaAsc(inicio, fin);
    }
}
