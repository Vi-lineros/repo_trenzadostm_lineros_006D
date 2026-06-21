package com.trenzadostm.trenzadostmseguimiento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadostm.trenzadostmseguimiento.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmseguimiento.dto.EnvioDto;
import com.trenzadostm.trenzadostmseguimiento.dto.PedidoDto;
import com.trenzadostm.trenzadostmseguimiento.dto.RepartidorDto;
import com.trenzadostm.trenzadostmseguimiento.dto.SeguimientoDto;
import com.trenzadostm.trenzadostmseguimiento.services.SeguimientoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Seguimiento Service Test")
class SeguimientoServiceTest {

    @Mock
    private ExternalServiceClient externalServiceClient;

    @InjectMocks
    private SeguimientoService seguimientoService;

    private PedidoDto pedidoDto;
    private EnvioDto envioDto;
    private RepartidorDto repartidorDto;

    @BeforeEach
    void setUp(){
        pedidoDto = new PedidoDto();
        pedidoDto.setIdPedido(1L);
        pedidoDto.setIdUsuario(1L);
        pedidoDto.setIdSucursal(1L);
        pedidoDto.setOpcionEntrega("Domicilio");
        pedidoDto.setEstadoPedido("COMPLETADO");
        pedidoDto.setFechaPedido(LocalDateTime.now());
        pedidoDto.setTotal(BigDecimal.valueOf(100000));
        pedidoDto.setIva(BigDecimal.valueOf(19000));
        pedidoDto.setTotalConIva(BigDecimal.valueOf(119000));

        envioDto = new EnvioDto();
        envioDto.setIdEnvio(1L);
        envioDto.setIdPedido(1L);
        envioDto.setIdRepartidor(1L);
        envioDto.setEmpresaEnvio("DHL");
        envioDto.setFechaEnvio(LocalDateTime.now());
        envioDto.setFechaEntrega(LocalDateTime.now().plusDays(2));
        envioDto.setEstadoEnvio("EN_CAMINO");

        repartidorDto = new RepartidorDto();
        repartidorDto.setIdRepartidor(1L);
        repartidorDto.setIdUsuario(10L);
        repartidorDto.setTipoVehiculo("Moto");
        repartidorDto.setPatente("XX1234YY");
        repartidorDto.setEstadoRepartidor("ACTIVO");
    }

    @Test
    @DisplayName("debe obtener seguimiento exitosamente con repartidor")
    void testObtenerSeguimientoExitoso(){
        when(externalServiceClient.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(externalServiceClient.obtenerEnvioPorPedido(1L)).thenReturn(envioDto);
        when(externalServiceClient.obtenerRepartidor(1L)).thenReturn(repartidorDto);

        SeguimientoDto resultado = seguimientoService.obtenerSeguimiento(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
        assertEquals("COMPLETADO", resultado.getEstadoPedido());
        assertEquals("EN_CAMINO", resultado.getEstadoEnvio());
        assertEquals("DHL", resultado.getEmpresaEnvio());
        assertEquals("Moto", resultado.getTipoVehiculo());
        assertEquals("XX1234YY", resultado.getPatente());
        verify(externalServiceClient, times(1)).obtenerPedido(1L);
        verify(externalServiceClient, times(1)).obtenerEnvioPorPedido(1L);
        verify(externalServiceClient, times(1)).obtenerRepartidor(1L);
    }

    @Test
    @DisplayName("debe obtener seguimiento sin repartidor cuando es nulo")
    void testObtenerSeguimientoSinRepartidor(){
        envioDto.setIdRepartidor(null);
        when(externalServiceClient.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(externalServiceClient.obtenerEnvioPorPedido(1L)).thenReturn(envioDto);

        SeguimientoDto resultado = seguimientoService.obtenerSeguimiento(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
        assertEquals("COMPLETADO", resultado.getEstadoPedido());
        assertEquals("EN_CAMINO", resultado.getEstadoEnvio());
        assertEquals("DHL", resultado.getEmpresaEnvio());
        verify(externalServiceClient, times(1)).obtenerPedido(1L);
        verify(externalServiceClient, times(1)).obtenerEnvioPorPedido(1L);
    }

    @Test
    @DisplayName("debe lanzar excepción cuando el pedido no existe")
    void testObtenerSeguimientoPedidoNoExiste(){
        when(externalServiceClient.obtenerPedido(99L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            seguimientoService.obtenerSeguimiento(99L);
        });

        verify(externalServiceClient, times(1)).obtenerPedido(99L);
    }

    @Test
    @DisplayName("debe lanzar excepción cuando el envío no existe")
    void testObtenerSeguimientoEnvioNoExiste(){
        when(externalServiceClient.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(externalServiceClient.obtenerEnvioPorPedido(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            seguimientoService.obtenerSeguimiento(1L);
        });

        verify(externalServiceClient, times(1)).obtenerPedido(1L);
        verify(externalServiceClient, times(1)).obtenerEnvioPorPedido(1L);
    }

    @Test
    @DisplayName("debe obtener seguimiento con estados diferentes")
    void testObtenerSeguimientoConEstadosDiferentes(){
        pedidoDto.setEstadoPedido("PENDIENTE");
        envioDto.setEstadoEnvio("PENDIENTE");
        
        when(externalServiceClient.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(externalServiceClient.obtenerEnvioPorPedido(1L)).thenReturn(envioDto);
        when(externalServiceClient.obtenerRepartidor(1L)).thenReturn(repartidorDto);

        SeguimientoDto resultado = seguimientoService.obtenerSeguimiento(1L);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstadoPedido());
        assertEquals("PENDIENTE", resultado.getEstadoEnvio());
        verify(externalServiceClient, times(1)).obtenerPedido(1L);
        verify(externalServiceClient, times(1)).obtenerEnvioPorPedido(1L);
    }

    @Test
    @DisplayName("debe obtener seguimiento con empresa de envío diferente")
    void testObtenerSeguimientoConEmpresaDiferente(){
        envioDto.setEmpresaEnvio("FedEx");
        
        when(externalServiceClient.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(externalServiceClient.obtenerEnvioPorPedido(1L)).thenReturn(envioDto);
        when(externalServiceClient.obtenerRepartidor(1L)).thenReturn(repartidorDto);

        SeguimientoDto resultado = seguimientoService.obtenerSeguimiento(1L);

        assertNotNull(resultado);
        assertEquals("FedEx", resultado.getEmpresaEnvio());
        verify(externalServiceClient, times(1)).obtenerEnvioPorPedido(1L);
    }
}
