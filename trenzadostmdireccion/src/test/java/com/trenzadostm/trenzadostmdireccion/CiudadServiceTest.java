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

import com.trenzadostm.trenzadostmdireccion.model.Ciudad;
import com.trenzadostm.trenzadostmdireccion.repository.CiudadRepository;
import com.trenzadostm.trenzadostmdireccion.services.CiudadService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Ciudad Service Test")
class CiudadServiceTest {

    @Mock
    private CiudadRepository ciudadRepository;

    @InjectMocks
    private CiudadService ciudadService;

    private Ciudad ciudadTest;

    @BeforeEach
    void setUp(){
        ciudadTest = new Ciudad();
        ciudadTest.setIdCiudad(1L);
        ciudadTest.setNombreCiudad("CiudadTest");
    }

    @Test
    @DisplayName("debe devolver la lista con todas las ciudades")
    void testBuscarTodos(){
        List<Ciudad> listaSimulada = Arrays.asList(ciudadTest, new Ciudad());
        when(ciudadRepository.findAll()).thenReturn(listaSimulada);

        List<Ciudad> resultado = ciudadService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ciudadRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver una ciudad por id exitoso")
    void testBuscarPorIdExitoso(){
        when(ciudadRepository.findById(1L)).thenReturn(Optional.of(ciudadTest));

        Ciudad resultado = ciudadService.buscarCiudadPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCiudad());
        assertEquals("CiudadTest", resultado.getNombreCiudad());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando la ciudad no existe")
    void testBuscarPorIdFallido(){
        when(ciudadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            ciudadService.buscarCiudadPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar una ciudad nueva exitosamente")
    void testCrearCiudad(){
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(ciudadTest);

        Ciudad resultado = ciudadService.guardarCiudad(ciudadTest);

        assertNotNull(resultado);
        assertEquals("CiudadTest", resultado.getNombreCiudad());
        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
    }

    @Test
    @DisplayName("debe eliminar una ciudad por su id")
    void testEliminarCiudad(){
        ciudadService.eliminarCiudad(1L);

        verify(ciudadRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar una ciudad existente exitosamente")
    void testModificarCiudad(){
        Ciudad ciudadModificada = new Ciudad();
        ciudadModificada.setNombreCiudad("CiudadModificada");

        when(ciudadRepository.findById(1L)).thenReturn(Optional.of(ciudadTest));
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(ciudadModificada);

        Ciudad resultado = ciudadService.modificarCiudad(1L, ciudadModificada);

        assertNotNull(resultado);
        assertEquals("CiudadModificada", resultado.getNombreCiudad());
        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
    }
}
