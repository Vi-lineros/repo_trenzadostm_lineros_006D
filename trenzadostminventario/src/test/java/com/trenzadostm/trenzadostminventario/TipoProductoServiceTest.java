package com.trenzadostm.trenzadostminventario;

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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadostm.trenzadostminventario.model.Producto;
import com.trenzadostm.trenzadostminventario.model.TipoProducto;
import com.trenzadostm.trenzadostminventario.repository.TipoProductoRepository;
import com.trenzadostm.trenzadostminventario.services.TipoProductoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TipoProducto Service Test")
class TipoProductoServiceTest {

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @InjectMocks
    private TipoProductoService tipoProductoService;

    private TipoProducto tipoProductoTest;

    @BeforeEach
    void setUp(){
        tipoProductoTest = new TipoProducto();
        tipoProductoTest.setIdTipoProducto(1L);
        Producto productoTemporal = new Producto();
        productoTemporal.setIdProducto(1L);
        tipoProductoTest.setProducto(productoTemporal);
        tipoProductoTest.setMedida("M");
        tipoProductoTest.setColor("rojo");
        tipoProductoTest.setPrecioSinIva(BigDecimal.valueOf(100));
    }

    @Test
    @DisplayName("debe devolver la lista con todos los tipos de producto")
    void testBuscarTodos(){
        List<TipoProducto> listaSimulada = Arrays.asList(tipoProductoTest, new TipoProducto());
        when(tipoProductoRepository.findAll()).thenReturn(listaSimulada);

        List<TipoProducto> resultado = tipoProductoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(tipoProductoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un tipo de producto por su id exitosamente")
    void testBuscarPorIdExitoso(){
        when(tipoProductoRepository.findById(1L)).thenReturn(Optional.of(tipoProductoTest));

        TipoProducto resultado = tipoProductoService.buscarTipoProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdTipoProducto());
        assertEquals("M", resultado.getMedida());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando el tipo de producto no existe")
    void testBuscarPorIdFallido(){
        when(tipoProductoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            tipoProductoService.buscarTipoProductoPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar un tipo de producto nuevo exitosamente")
    void testCrearTipoProducto(){
        when(tipoProductoRepository.save(any(TipoProducto.class))).thenReturn(tipoProductoTest);

        TipoProducto resultado = tipoProductoService.guardarTipoProducto(tipoProductoTest);

        assertNotNull(resultado);
        assertEquals("M", resultado.getMedida());
        verify(tipoProductoRepository, times(1)).save(any(TipoProducto.class));
    }

    @Test
    @DisplayName("debe eliminar un tipo de producto por su id")
    void testEliminarTipoProducto(){
        tipoProductoService.eliminarTipoProducto(1L);

        verify(tipoProductoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un tipo de producto existente exitosamente")
    void testModificarTipoProducto(){
        TipoProducto modificado = new TipoProducto();
        modificado.setMedida("L");
        modificado.setColor("Blanco");
        modificado.setPrecioSinIva(BigDecimal.valueOf(150));

        when(tipoProductoRepository.findById(1L)).thenReturn(Optional.of(tipoProductoTest));
        when(tipoProductoRepository.save(any(TipoProducto.class))).thenReturn(modificado);

        TipoProducto resultado = tipoProductoService.modificarTipoProducto(1L, modificado);

        assertNotNull(resultado);
        assertEquals("L", resultado.getMedida());
        verify(tipoProductoRepository, times(1)).save(any(TipoProducto.class));
    }
}
