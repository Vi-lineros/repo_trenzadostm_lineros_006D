package com.trenzadostm.trenzadostmreportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmreportes.dto.ReporteEnvioDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteInventarioDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteVentasDto;
import com.trenzadostm.trenzadostmreportes.services.ReporteService;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/ventas")
    public ResponseEntity<ReporteVentasDto> generarReporteVentas() {
        return ResponseEntity.ok(reporteService.generarReporteVentas());
    }

    @GetMapping("/inventario")
    public ResponseEntity<ReporteInventarioDto> generarReporteInventario() {
        return ResponseEntity.ok(reporteService.generarReporteInventario());
    }

    @GetMapping("/envios")
    public ResponseEntity<ReporteEnvioDto> generarReporteEnvios() {
        return ResponseEntity.ok(reporteService.generarReporteEnvios());
    }
}
