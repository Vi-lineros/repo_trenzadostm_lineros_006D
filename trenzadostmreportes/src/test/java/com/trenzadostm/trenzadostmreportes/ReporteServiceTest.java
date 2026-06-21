package com.trenzadostm.trenzadostmreportes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadostm.trenzadostmreportes.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmreportes.dto.EnvioDto;
import com.trenzadostm.trenzadostmreportes.dto.InventarioDto;
import com.trenzadostm.trenzadostmreportes.dto.PedidoDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteEnvioDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteInventarioDto;
import com.trenzadostm.trenzadostmreportes.dto.ReporteVentasDto;
import com.trenzadostm.trenzadostmreportes.model.Reporte;
import com.trenzadostm.trenzadostmreportes.repository.ReporteRepository;
import com.trenzadostm.trenzadostmreportes.services.ReporteService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reporte Service Test")
class ReporteServiceTest {

    @Mock
    private ExternalServiceClient externalServiceClient;

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    private Reporte reporteTest;
    private PedidoDto pedidoDto;
    private InventarioDto inventarioDto;
    private EnvioDto envioDto;

    @BeforeEach
    void setUp(){
        reporteTest = new Reporte();
        reporteTest.setIdReporte(1L);
        reporteTest.setNombreReporte("Reporte de Ventas");
        reporteTest.setTipoReporte("Ventas");
        reporteTest.setFechaGeneracion(LocalDateTime.now());

        pedidoDto = new PedidoDto();
        pedidoDto.setIdPedido(1L);
        pedidoDto.setTotalConIva(BigDecimal.valueOf(119000));
        pedidoDto.setEstadoPedido("COMPLETADO");

        inventarioDto = new InventarioDto();
        inventarioDto.setIdTipoProducto(1L);
        inventarioDto.setStock(100L);

        envioDto = new EnvioDto();
        envioDto.setIdEnvio(1L);
        envioDto.setEstadoEnvio("ENTREGADO");
        envioDto.setFechaEnvio(LocalDateTime.now());
        envioDto.setFechaEntrega(LocalDateTime.now());
    }

    @Test
    @DisplayName("debe generar reporte de ventas exitosamente")
    void testGenerarReporteVentas(){
        List<PedidoDto> pedidosSimulados = Arrays.asList(pedidoDto, new PedidoDto());
        when(externalServiceClient.obtenerPedidos()).thenReturn(pedidosSimulados);
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteTest);

        ReporteVentasDto resultado = reporteService.generarReporteVentas();

        assertNotNull(resultado);
        assertEquals("Reporte de Ventas", resultado.getNombreReporte());
        assertEquals("Ventas", resultado.getTipoReporte());
        assertEquals(2L, resultado.getCantidadPedidos());
        assertEquals(BigDecimal.valueOf(119000), resultado.getTotalVentas());
        verify(externalServiceClient, times(1)).obtenerPedidos();
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    @DisplayName("debe generar reporte de inventario exitosamente")
    void testGenerarReporteInventario(){
        List<InventarioDto> productosSimulados = Arrays.asList(inventarioDto, new InventarioDto());
        Reporte reporteInventario = new Reporte();
        reporteInventario.setIdReporte(1L);
        reporteInventario.setNombreReporte("Reporte de Inventario");
        reporteInventario.setTipoReporte("Inventario");
        reporteInventario.setFechaGeneracion(LocalDateTime.now());

        when(externalServiceClient.obtenerInventario()).thenReturn(productosSimulados);
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteInventario);

        ReporteInventarioDto resultado = reporteService.generarReporteInventario();

        assertNotNull(resultado);
        assertEquals("Reporte de Inventario", resultado.getNombreReporte());
        assertEquals("Inventario", resultado.getTipoReporte());
        assertEquals(2L, resultado.getCantidadProductos());
        verify(externalServiceClient, times(1)).obtenerInventario();
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    @DisplayName("debe generar reporte de envíos exitosamente")
    void testGenerarReporteEnvios(){
        EnvioDto envioPendiente = new EnvioDto();
        envioPendiente.setEstadoEnvio("PENDIENTE");
        
        EnvioDto envioEnCamino = new EnvioDto();
        envioEnCamino.setEstadoEnvio("EN_CAMINO");

        List<EnvioDto> enviosSimulados = Arrays.asList(envioDto, envioPendiente, envioEnCamino);
        
        Reporte reporteEnvios = new Reporte();
        reporteEnvios.setIdReporte(1L);
        reporteEnvios.setNombreReporte("Reporte de Envíos");
        reporteEnvios.setTipoReporte("envios");
        reporteEnvios.setFechaGeneracion(LocalDateTime.now());

        when(externalServiceClient.obtenerEnvios()).thenReturn(enviosSimulados);
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteEnvios);

        ReporteEnvioDto resultado = reporteService.generarReporteEnvios();

        assertNotNull(resultado);
        assertEquals("Reporte de Envíos", resultado.getNombreReporte());
        assertEquals("envios", resultado.getTipoReporte());
        assertEquals(1L, resultado.getEntregados());
        assertEquals(1L, resultado.getEnCamino());
        assertEquals(1L, resultado.getPendientes());
        verify(externalServiceClient, times(1)).obtenerEnvios();
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    @DisplayName("debe generar reporte de ventas con múltiples pedidos")
    void testGenerarReporteVentasConMultiplesPedidos(){
        PedidoDto pedido2 = new PedidoDto();
        pedido2.setIdPedido(2L);
        pedido2.setTotalConIva(BigDecimal.valueOf(89000));

        PedidoDto pedido3 = new PedidoDto();
        pedido3.setIdPedido(3L);
        pedido3.setTotalConIva(BigDecimal.valueOf(159000));

        List<PedidoDto> pedidosSimulados = Arrays.asList(pedidoDto, pedido2, pedido3);
        when(externalServiceClient.obtenerPedidos()).thenReturn(pedidosSimulados);
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteTest);

        ReporteVentasDto resultado = reporteService.generarReporteVentas();

        assertNotNull(resultado);
        assertEquals(3L, resultado.getCantidadPedidos());
        assertEquals(BigDecimal.valueOf(367000), resultado.getTotalVentas());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    @DisplayName("debe guardar reporte correctamente en base de datos")
    void testGuardarReporteEnBaseDatos(){
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteTest);

        Reporte resultado = reporteRepository.save(reporteTest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReporte());
        assertEquals("Reporte de Ventas", resultado.getNombreReporte());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }
}
