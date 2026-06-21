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
import com.trenzadostm.trenzadostminventario.repository.CategoriaRepository;
import com.trenzadostm.trenzadostminventario.services.CategoriaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Categoria Service Test")
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoriaTest;

    @BeforeEach
    void setUp(){
        categoriaTest = new Categoria();
        categoriaTest.setIdCategoria(1L);
        categoriaTest.setNombreCategoria("cabeza");
        categoriaTest.setDescripcionCategoria("Productos para la cabeza del caballo");
    }

    @Test
    @DisplayName("debe devolver la lista con todas las categorias")
    void testBuscarTodos(){
        List<Categoria> listaSimulada = Arrays.asList(categoriaTest, new Categoria());
        when(categoriaRepository.findAll()).thenReturn(listaSimulada);

        List<Categoria> resultado = categoriaService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver una categoria por id exitoso")
    void testBuscarPorIdExitoso(){
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaTest));

        Categoria resultado = categoriaService.buscarCategoriaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCategoria());
        assertEquals("cabeza", resultado.getNombreCategoria());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando la categoria no existe")
    void testBuscarPorIdFallido(){
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            categoriaService.buscarCategoriaPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar una categoria nueva exitosamente")
    void testCrearCategoria(){
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaTest);

        Categoria resultado = categoriaService.guardarCategoria(categoriaTest);

        assertNotNull(resultado);
        assertEquals("cabeza", resultado.getNombreCategoria());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    @DisplayName("debe eliminar una categoria por su id")
    void testEliminarCategoria(){
        categoriaService.eliminarCategoria(1L);

        verify(categoriaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar una categoria existente exitosamente")
    void testModificarCategoria(){
        Categoria categoriaModificada = new Categoria();
        categoriaModificada.setNombreCategoria("cabeza Modificada");
        categoriaModificada.setDescripcionCategoria("Descripcion modificada");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaTest));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaModificada);

        Categoria resultado = categoriaService.modificarCategoria(1L, categoriaModificada);

        assertNotNull(resultado);
        assertEquals("cabeza Modificada", resultado.getNombreCategoria());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
}
