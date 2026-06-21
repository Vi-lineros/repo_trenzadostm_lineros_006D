package com.trenzadostm.trenzadostmreportes.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmreportes.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmreportes.dto.EnvioDto;
import com.trenzadostm.trenzadostmreportes.dto.InventarioDto;
import com.trenzadostm.trenzadostmreportes.dto.PedidoDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteEnvioDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteInventarioDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteVentasDto;
import com.trenzadostm.trenzadostmreportes.model.Reporte;
import com.trenzadostm.trenzadostmreportes.repository.ReporteRepository;

@Service
public class ReporteService {

    @Autowired
    private ExternalServiceClient client;

    @Autowired
    private ReporteRepository reporteRepository;

    public ReporteVentasDto generarReporteVentas(){
        List<PedidoDto> pedidos = client.obtenerPedidos();
        
        BigDecimal totalVentas = pedidos.stream().map(PedidoDto::getTotalConIva).reduce(BigDecimal.ZERO, BigDecimal::add);
        Reporte reporte = new Reporte();
        reporte.setNombreReporte("Reporte de Ventas");
        reporte.setTipoReporte("Ventas");
        reporte.setFechaGeneracion(LocalDateTime.now());

        Reporte guardado = reporteRepository.save(reporte);

        ReporteVentasDto reporteVentasDto = new ReporteVentasDto();

        reporteVentasDto.setIdReporte(guardado.getIdReporte());
        reporteVentasDto.setNombreReporte(reporte.getNombreReporte());
        reporteVentasDto.setTipoReporte(reporte.getTipoReporte());
        reporteVentasDto.setCantidadPedidos((long) pedidos.size());
        reporteVentasDto.setTotalVentas(totalVentas);
        reporteVentasDto.setPedidos(pedidos);
        
        return reporteVentasDto;
    }

    public ReporteInventarioDto generarReporteInventario(){
        List<InventarioDto> productos  = client.obtenerInventario();
        
        Reporte reporte = new Reporte();
        reporte.setNombreReporte("Reporte de Inventario");
        reporte.setTipoReporte("Inventario");
        reporte.setFechaGeneracion(LocalDateTime.now());

        Reporte guardado = reporteRepository.save(reporte);

        ReporteInventarioDto reporteInventarioDto = new ReporteInventarioDto();

        reporteInventarioDto.setIdReporte(guardado.getIdReporte());
        reporteInventarioDto.setNombreReporte(reporte.getNombreReporte());
        reporteInventarioDto.setTipoReporte(reporte.getTipoReporte());
        reporteInventarioDto.setCantidadProductos((long) productos.size());
        reporteInventarioDto.setProductos(productos);
        
        return reporteInventarioDto;
    }

    public ReporteEnvioDto generarReporteEnvios(){
        List<EnvioDto> envios  = client.obtenerEnvios();
        
        Long entregados = envios.stream().filter(e -> "ENTREGADO".equalsIgnoreCase(e.getEstadoEnvio())).count();
        Long enCamino = envios.stream().filter(e -> "EN_CAMINO".equalsIgnoreCase(e.getEstadoEnvio())).count();
        Long pendientes = envios.stream().filter(e -> "PENDIENTE".equalsIgnoreCase(e.getEstadoEnvio())).count();

        Reporte reporte = new Reporte();
        reporte.setNombreReporte("Reporte de Envíos");
        reporte.setTipoReporte("envios");
        reporte.setFechaGeneracion(LocalDateTime.now());

        Reporte guardado = reporteRepository.save(reporte);

        ReporteEnvioDto reporteEnvioDto = new ReporteEnvioDto();

        reporteEnvioDto.setIdReporte(guardado.getIdReporte());
        reporteEnvioDto.setNombreReporte(reporte.getNombreReporte());
        reporteEnvioDto.setTipoReporte(reporte.getTipoReporte());
        reporteEnvioDto.setEntregados(entregados);
        reporteEnvioDto.setEnCamino(enCamino);
        reporteEnvioDto.setPendientes(pendientes);

        reporteEnvioDto.setEnvios(envios);

        return reporteEnvioDto;
    }
}
