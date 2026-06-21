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
import com.trenzadostm.trenzadostmpedido.dto.SucursalDto;
import com.trenzadostm.trenzadostmpedido.dto.UsuarioDto;
import com.trenzadostm.trenzadostmpedido.model.Pedido;
import com.trenzadostm.trenzadostmpedido.repository.PedidoRepository;
import com.trenzadostm.trenzadostmpedido.services.PedidoService;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ExternalServiceClient externalServiceClient;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoTest;

    @BeforeEach
    void setUp(){
        pedidoTest = new Pedido();
        pedidoTest.setIdPedido(1L);
        pedidoTest.setIdUsuario(1L);
        pedidoTest.setIdSucursal(1L);
        pedidoTest.setOpcionEntrega("DOMICILIO");
        pedidoTest.setTotal(BigDecimal.valueOf(100));
        pedidoTest.setIva(BigDecimal.valueOf(19));
        pedidoTest.setTotalConIva(BigDecimal.valueOf(119));
    }

    @Test
    @DisplayName("debe devolver la lista con todos los pedidos")
    void testBuscarTodos(){
        List<Pedido> listaSimulada = Arrays.asList(pedidoTest, new Pedido());
        when(pedidoRepository.findAll()).thenReturn(listaSimulada);

        List<Pedido> resultado = pedidoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un pedido por su id exitoso")
    void testBuscarPorIdExitoso(){
        when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoTest));

        Pedido resultado = pedidoService.buscarPedidoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
        assertEquals(1L, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("debe devolver una excepcion cuando el id del pedido no existe")
    void testBuscarPorIdFallido(){
        when(pedidoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            pedidoService.buscarPedidoPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar y retornar el pedido creado")
    void testCrearPedido(){
        UsuarioDto usuarioDto = new UsuarioDto(1L, 1L, "", "", "", "");
        SucursalDto sucursalDto = new SucursalDto();
        sucursalDto.setIdSucursal(1L);
        sucursalDto.setIdDireccion(1L);
        sucursalDto.setNombreSucursal("Sucursal Test");

        when(externalServiceClient.obtenerUsuario(any(Long.class))).thenReturn(usuarioDto);
        when(externalServiceClient.obtenerSucursal(any(Long.class))).thenReturn(sucursalDto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoTest);

        Pedido resultado = pedidoService.guardarPedido(pedidoTest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("debe eliminar un pedido por su id")
    void testEliminarPedido(){
        pedidoService.eliminarPedido(1L);

        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un pedido existente exitosamente")
    void testModificarPedido(){
        Pedido pedidoModificado = new Pedido();
        pedidoModificado.setIdPedido(1L);
        pedidoModificado.setIdUsuario(2L);
        pedidoModificado.setIdSucursal(2L);
        pedidoModificado.setOpcionEntrega("RETIRO");
        pedidoModificado.setTotal(BigDecimal.valueOf(200));

        UsuarioDto usuarioDto = new UsuarioDto(2L, 1L, "", "", "", "");
        SucursalDto sucursalDto = new SucursalDto();
        sucursalDto.setIdSucursal(2L);
        sucursalDto.setIdDireccion(1L);
        sucursalDto.setNombreSucursal("Sucursal Test");

        when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoTest));
        when(externalServiceClient.obtenerUsuario(any(Long.class))).thenReturn(usuarioDto);
        when(externalServiceClient.obtenerSucursal(any(Long.class))).thenReturn(sucursalDto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoModificado);

        Pedido resultado = pedidoService.modificarPedido(1L, pedidoModificado);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getIdUsuario());
        assertEquals(BigDecimal.valueOf(200), resultado.getTotal());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }
}
