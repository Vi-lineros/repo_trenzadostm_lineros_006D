package com.trenzadostm.trenzadostmpedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadostm.trenzadostmpedido.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmpedido.dto.TipoProductoDto;
import com.trenzadostm.trenzadostmpedido.model.DetallePedido;
import com.trenzadostm.trenzadostmpedido.model.Pedido;
import com.trenzadostm.trenzadostmpedido.repository.DetallePedidoRepository;
import com.trenzadostm.trenzadostmpedido.repository.PedidoRepository;
import com.trenzadostm.trenzadostmpedido.services.DetallePedidoService;

@ExtendWith(MockitoExtension.class)
public class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoRepository detallePedidoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ExternalServiceClient externalServiceClient;

    @InjectMocks
    private DetallePedidoService detallePedidoService;

    private DetallePedido detallePedidoTest;

    @BeforeEach
    void setUp(){
        detallePedidoTest = new DetallePedido();
        detallePedidoTest.setIdDetallePedido(1L);
        detallePedidoTest.setIdTipoProducto(1L);
        detallePedidoTest.setCantidad(2);
        detallePedidoTest.setPrecioUnitario(BigDecimal.ZERO);
        detallePedidoTest.setSubtotal(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("debe devolver la lista con todos los detalles de pedido")
    void testBuscarTodos(){
        List<DetallePedido> listaSimulada = Arrays.asList(detallePedidoTest, new DetallePedido());
        when(detallePedidoRepository.findAll()).thenReturn(listaSimulada);

        List<DetallePedido> resultado = detallePedidoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(detallePedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un detalle de pedido por su id exitoso")
    void testBuscarPorIdExitoso(){
        when(detallePedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(detallePedidoTest));

        DetallePedido resultado = detallePedidoService.buscarDetallePedidoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdDetallePedido());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando el id del detalle no existe")
    void testBuscarPorIdFallido(){
        when(detallePedidoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            detallePedidoService.buscarDetallePedidoPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar y retornar el detalle de pedido creado")
    void testCrearDetallePedido(){
        Pedido pedidoTemporal = new Pedido();
        pedidoTemporal.setIdPedido(1L);
        detallePedidoTest.setPedido(pedidoTemporal);
        
        TipoProductoDto tipoProducto = new TipoProductoDto();
        tipoProducto.setPrecioSinIva(BigDecimal.valueOf(10));

        when(externalServiceClient.obtenerTipoProducto(any(Long.class))).thenReturn(tipoProducto);
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detallePedidoTest);
        when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoTemporal));
        when(detallePedidoRepository.findByPedido_IdPedido(1L)).thenReturn(Arrays.asList(detallePedidoTest));

        DetallePedido resultado = detallePedidoService.guardarDetallePedido(detallePedidoTest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdDetallePedido());
        verify(detallePedidoRepository, times(1)).save(any(DetallePedido.class));
    }

    @Test
    @DisplayName("debe eliminar un detalle de pedido por su id")
    void testEliminarDetallePedido(){
        Pedido pedidoTemporal = new Pedido();
        pedidoTemporal.setIdPedido(1L);
        detallePedidoTest.setPedido(pedidoTemporal);
        
        when(detallePedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(detallePedidoTest));
        when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoTemporal));
        when(detallePedidoRepository.findByPedido_IdPedido(1L)).thenReturn(Arrays.asList(detallePedidoTest));

        detallePedidoService.eliminarDetallePedido(1L);

        verify(detallePedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un detalle de pedido existente exitosamente")
    void testModificarDetallePedido(){
        Pedido pedidoTemporal = new Pedido();
        pedidoTemporal.setIdPedido(1L);
        detallePedidoTest.setPedido(pedidoTemporal);
        
        DetallePedido detalleModificado = new DetallePedido();
        detalleModificado.setIdDetallePedido(1L);
        detalleModificado.setIdTipoProducto(2L);
        detalleModificado.setCantidad(5);
        detalleModificado.setPrecioUnitario(BigDecimal.valueOf(15));
        detalleModificado.setSubtotal(BigDecimal.valueOf(75));
        detalleModificado.setPedido(pedidoTemporal);

        TipoProductoDto tipoProducto = new TipoProductoDto();
        tipoProducto.setPrecioSinIva(BigDecimal.valueOf(15));

        when(detallePedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(detallePedidoTest));
        when(externalServiceClient.obtenerTipoProducto(any(Long.class))).thenReturn(tipoProducto);
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detalleModificado);
        when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoTemporal));
        when(detallePedidoRepository.findByPedido_IdPedido(1L)).thenReturn(Arrays.asList(detalleModificado));

        DetallePedido resultado = detallePedidoService.modificarDetallePedido(1L, detalleModificado);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidad());
        assertEquals(BigDecimal.valueOf(15), resultado.getPrecioUnitario());
        verify(detallePedidoRepository, times(1)).save(any(DetallePedido.class));
    }
}
