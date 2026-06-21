package com.trenzadostm.trenzadostmdireccion;

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

import com.trenzadostm.trenzadostmdireccion.model.Comuna;
import com.trenzadostm.trenzadostmdireccion.repository.ComunaRepository;
import com.trenzadostm.trenzadostmdireccion.services.ComunaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Comuna Service Test")
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private ComunaService comunaService;

    private Comuna comunaTest;

    @BeforeEach
    void setUp(){
        comunaTest = new Comuna();
        comunaTest.setIdComuna(1L);
        comunaTest.setNombreComuna("ComunaTest");
    }

    @Test
    @DisplayName("debe devolver la lista con todas las comunas")
    void testBuscarTodos(){
        List<Comuna> listaSimulada = Arrays.asList(comunaTest, new Comuna());
        when(comunaRepository.findAll()).thenReturn(listaSimulada);

        List<Comuna> resultado = comunaService.buscarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(comunaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver una comuna por id exitoso")
    void testBuscarPorIdExitoso(){
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(comunaTest));

        Comuna resultado = comunaService.buscarComunaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdComuna());
        assertEquals("ComunaTest", resultado.getNombreComuna());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando la comuna no existe")
    void testBuscarPorIdFallido(){
        when(comunaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            comunaService.buscarComunaPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar una comuna nueva exitosamente")
    void testCrearComuna(){
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comunaTest);

        Comuna resultado = comunaService.guardarComuna(comunaTest);

        assertNotNull(resultado);
        assertEquals("ComunaTest", resultado.getNombreComuna());
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }

    @Test
    @DisplayName("debe eliminar una comuna por su id")
    void testEliminarComuna(){
        comunaService.eliminarComuna(1L);

        verify(comunaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar una comuna existente exitosamente")
    void testModificarComuna(){
        Comuna comunaModificada = new Comuna();
        comunaModificada.setNombreComuna("ComunaModificada");

        when(comunaRepository.findById(1L)).thenReturn(Optional.of(comunaTest));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comunaModificada);

        Comuna resultado = comunaService.modificarComuna(1L, comunaModificada);

        assertNotNull(resultado);
        assertEquals("ComunaModificada", resultado.getNombreComuna());
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }
}
