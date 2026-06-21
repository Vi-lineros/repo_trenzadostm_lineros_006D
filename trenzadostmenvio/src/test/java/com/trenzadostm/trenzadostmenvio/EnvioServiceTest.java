package com.trenzadostm.trenzadostmenvio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadostm.trenzadostmenvio.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmenvio.dto.PedidoDto;
import com.trenzadostm.trenzadostmenvio.dto.RepartidorDto;
import com.trenzadostm.trenzadostmenvio.model.Envio;
import com.trenzadostm.trenzadostmenvio.repository.EnvioRepository;
import com.trenzadostm.trenzadostmenvio.services.EnvioService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Envio Service Test")
public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private ExternalServiceClient client;

    @InjectMocks
    private EnvioService envioService;

    private Envio envioTest;

    @BeforeEach
    void setUp(){
        envioTest = new Envio();
        envioTest.setIdEnvio(1L);
        envioTest.setIdPedido(1L);
        envioTest.setIdRepartidor(1L);
        envioTest.setEmpresaEnvio("DHL");
        envioTest.setFechaEnvio(LocalDateTime.now());
        envioTest.setFechaEntrega(LocalDateTime.now().plusDays(2));
        envioTest.setEstadoEnvio("PENDIENTE");
    }

    @Test
    @DisplayName("debe devolver la lista con todos los envios")
    void testBuscarTodos(){
        List<Envio> listaSimulada = Arrays.asList(envioTest, new Envio());
        when(envioRepository.findAll()).thenReturn(listaSimulada);

        List<Envio> resultado = envioService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un envio por id exitoso")
    void testBuscarPorIdExitoso(){
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioTest));

        Envio resultado = envioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdEnvio());
        assertEquals(1L, resultado.getIdPedido());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando el envio no existe")
    void testBuscarPorIdFallido(){
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            envioService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar un envio nuevo exitosamente")
    void testGuardarEnvio(){
        Envio envioParaGuardar = new Envio();
        envioParaGuardar.setIdPedido(1L);
        envioParaGuardar.setIdRepartidor(1L);
        envioParaGuardar.setEmpresaEnvio("DHL");
        envioParaGuardar.setFechaEnvio(LocalDateTime.now());
        envioParaGuardar.setFechaEntrega(LocalDateTime.now().plusDays(2));
        envioParaGuardar.setEstadoEnvio(null);

        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setIdPedido(1L);
        RepartidorDto repartidorDto = new RepartidorDto();
        repartidorDto.setIdRepartidor(1L);

        when(client.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(client.obtenerRepartidor(1L)).thenReturn(repartidorDto);
        when(envioRepository.save(any(Envio.class))).thenReturn(envioTest);

        Envio resultado = envioService.guardarEnvio(envioParaGuardar);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", envioParaGuardar.getEstadoEnvio());
        verify(envioRepository, times(1)).save(any(Envio.class));
    }

    @Test
    @DisplayName("debe lanzar excepcion al guardar cuando el pedido no existe")
    void testGuardarEnvioPedidoNoExiste(){
        when(client.obtenerPedido(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> envioService.guardarEnvio(envioTest));
    }

    @Test
    @DisplayName("debe lanzar excepcion al guardar cuando el repartidor no existe")
    void testGuardarEnvioRepartidorNoExiste(){
        Envio envioSinRepartidorExistente = new Envio();
        envioSinRepartidorExistente.setIdPedido(1L);
        envioSinRepartidorExistente.setIdRepartidor(2L);

        when(client.obtenerPedido(1L)).thenReturn(new PedidoDto());
        when(client.obtenerRepartidor(2L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> envioService.guardarEnvio(envioSinRepartidorExistente));
    }

    @Test
    @DisplayName("debe modificar un envio existente exitosamente")
    void testModificarEnvio(){
        Envio envioActualizado = new Envio();
        envioActualizado.setIdPedido(2L);
        envioActualizado.setIdRepartidor(3L);
        envioActualizado.setEmpresaEnvio("UPS");
        envioActualizado.setFechaEnvio(LocalDateTime.now().plusDays(1));
        envioActualizado.setFechaEntrega(LocalDateTime.now().plusDays(4));
        envioActualizado.setEstadoEnvio("ENTREGADO");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioTest));
        when(client.obtenerPedido(2L)).thenReturn(new PedidoDto());
        when(client.obtenerRepartidor(3L)).thenReturn(new RepartidorDto());
        when(envioRepository.save(any(Envio.class))).thenReturn(envioActualizado);

        Envio resultado = envioService.modificarEnvio(1L, envioActualizado);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getIdPedido());
        assertEquals("ENTREGADO", resultado.getEstadoEnvio());
        verify(envioRepository, times(1)).save(any(Envio.class));
    }

    @Test
    @DisplayName("debe eliminar un envio por su id")
    void testEliminarEnvio(){
        String resultado = envioService.eliminarEnvio(1L);

        assertEquals("Envio eliminado", resultado);
        verify(envioRepository, times(1)).deleteById(1L);
    }
}
