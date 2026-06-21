package com.trenzadostm.trenzadostminventario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.trenzadostm.trenzadostminventario.model.Categoria;
import com.trenzadostm.trenzadostminventario.model.Producto;
import com.trenzadostm.trenzadostminventario.repository.ProductoRepository;
import com.trenzadostm.trenzadostminventario.services.ProductoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Producto Service Test")
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto productoTest;

    @BeforeEach
    void setUp(){
        productoTest = new Producto();
        productoTest.setIdProducto(1L);
        Categoria categoriaTemporal = new Categoria();
        categoriaTemporal.setIdCategoria(1L);
        productoTest.setCategoria(categoriaTemporal);
        productoTest.setNombreProducto("jakima");
        productoTest.setDescripcionProducto("jakima delgada");
        productoTest.setTipoMaterial("hilo");
        productoTest.setEstadoProducto("Disponible");
    }

    @Test
    @DisplayName("debe devolver la lista con todos los productos")
    void testBuscarTodos(){
        List<Producto> listaSimulada = Arrays.asList(productoTest, new Producto());
        when(productoRepository.findAll()).thenReturn(listaSimulada);

        List<Producto> resultado = productoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un producto por su id exitosamente")
    void testBuscarPorIdExitoso(){
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoTest));

        Producto resultado = productoService.buscarProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdProducto());
        assertEquals("jakima", resultado.getNombreProducto());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando el producto no existe")
    void testBuscarPorIdFallido(){
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productoService.buscarProductoPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar un producto nuevo exitosamente")
    void testCrearProducto(){
        when(productoRepository.save(any(Producto.class))).thenReturn(productoTest);

        Producto resultado = productoService.guardarProducto(productoTest);

        assertNotNull(resultado);
        assertEquals("jakima", resultado.getNombreProducto());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("debe eliminar un producto por su id")
    void testEliminarProducto(){
        productoService.eliminarProducto(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un producto existente exitosamente")
    void testModificarProducto(){
        Producto productoModificado = new Producto();
        productoModificado.setNombreProducto("jakima Modificada");
        productoModificado.setDescripcionProducto("Descripcion modificada");
        productoModificado.setTipoMaterial("hilo modificado");
        productoModificado.setEstadoProducto("Agotado");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoTest));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoModificado);

        Producto resultado = productoService.modificarProducto(1L, productoModificado);

        assertNotNull(resultado);
        assertEquals("jakima Modificada", resultado.getNombreProducto());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }
}
