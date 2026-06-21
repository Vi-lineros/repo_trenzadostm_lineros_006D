package com.trenzadostm.trenzadostminventario;

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

import com.trenzadostm.trenzadostminventario.model.Inventario;
import com.trenzadostm.trenzadostminventario.model.TipoProducto;
import com.trenzadostm.trenzadostminventario.repository.InventarioRepository;
import com.trenzadostm.trenzadostminventario.services.InventarioService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Inventario Service Test")
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventarioTest;

    @BeforeEach
    void setUp(){
        inventarioTest = new Inventario();
        inventarioTest.setIdInventario(1L);
        inventarioTest.setIdSucursal(1L);
        TipoProducto tipoProductoTemporal = new TipoProducto();
        tipoProductoTemporal.setIdTipoProducto(1L);
        inventarioTest.setTipoProducto(tipoProductoTemporal);
        inventarioTest.setStock(10);
        inventarioTest.setStockMinimo(2);
        inventarioTest.setFechaActualizacion(LocalDateTime.now());
    }

    @Test
    @DisplayName("debe devolver la lista con todos los inventarios")
    void testBuscarTodos(){
        List<Inventario> listaSimulada = Arrays.asList(inventarioTest, new Inventario());
        when(inventarioRepository.findAll()).thenReturn(listaSimulada);

        List<Inventario> resultado = inventarioService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un inventario por id exitoso")
    void testBuscarPorIdExitoso(){
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioTest));

        Inventario resultado = inventarioService.buscarInventarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdInventario());
        assertEquals(10, resultado.getStock());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando el inventario no existe")
    void testBuscarPorIdFallido(){
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            inventarioService.buscarInventarioPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar un inventario nuevo exitosamente")
    void testCrearInventario(){
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioTest);

        Inventario resultado = inventarioService.guardarInventario(inventarioTest);

        assertNotNull(resultado);
        assertEquals(10, resultado.getStock());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("debe eliminar un inventario por su id")
    void testEliminarInventario(){
        inventarioService.eliminarInventario(1L);

        verify(inventarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un inventario existente exitosamente")
    void testModificarInventario(){
        Inventario inventarioModificado = new Inventario();
        inventarioModificado.setIdInventario(1L);
        inventarioModificado.setIdSucursal(2L);
        TipoProducto tipo = new TipoProducto();
        tipo.setIdTipoProducto(2L);
        inventarioModificado.setTipoProducto(tipo);
        inventarioModificado.setStock(20);
        inventarioModificado.setStockMinimo(5);

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioTest));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioModificado);

        Inventario resultado = inventarioService.modificarInventario(1L, inventarioModificado);

        assertNotNull(resultado);
        assertEquals(20, resultado.getStock());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }
}
