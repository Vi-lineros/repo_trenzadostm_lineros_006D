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
import com.trenzadostm.trenzadostmdireccion.model.Comuna;
import com.trenzadostm.trenzadostmdireccion.model.Direccion;
import com.trenzadostm.trenzadostmdireccion.model.Region;
import com.trenzadostm.trenzadostmdireccion.repository.DireccionRepository;
import com.trenzadostm.trenzadostmdireccion.services.DireccionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Direccion Service Test")
class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionService direccionService;

    private Direccion direccionTest;

    @BeforeEach
    void setUp(){
        direccionTest = new Direccion();
        direccionTest.setIdDireccion(1L);
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1L);
        ciudad.setNombreCiudad("CiudadTest");
        Comuna comuna = new Comuna();
        comuna.setIdComuna(1L);
        comuna.setNombreComuna("ComunaTest");
        Region region = new Region();
        region.setIdRegion(1L);
        region.setNombreRegion("RegionTest");
        direccionTest.setCiudad(ciudad);
        direccionTest.setComuna(comuna);
        direccionTest.setRegion(region);
        direccionTest.setDireccion("Calle Falsa 123");
    }

    @Test
    @DisplayName("debe devolver la lista con todas las direcciones")
    void testBuscarTodos(){
        List<Direccion> listaSimulada = Arrays.asList(direccionTest, new Direccion());
        when(direccionRepository.findAll()).thenReturn(listaSimulada);

        List<Direccion> resultado = direccionService.buscarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(direccionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver una direccion por id exitoso")
    void testBuscarPorIdExitoso(){
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(direccionTest));

        Direccion resultado = direccionService.buscarDireccionPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdDireccion());
        assertEquals("Calle Falsa 123", resultado.getDireccion());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando la direccion no existe")
    void testBuscarPorIdFallido(){
        when(direccionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            direccionService.buscarDireccionPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar una direccion nueva exitosamente")
    void testCrearDireccion(){
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccionTest);

        Direccion resultado = direccionService.guardarDireccion(direccionTest);

        assertNotNull(resultado);
        assertEquals("Calle Falsa 123", resultado.getDireccion());
        verify(direccionRepository, times(1)).save(any(Direccion.class));
    }

    @Test
    @DisplayName("debe eliminar una direccion por su id")
    void testEliminarDireccion(){
        direccionService.eliminarDireccion(1L);

        verify(direccionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar una direccion existente exitosamente")
    void testModificarDireccion(){
        Direccion direccionModificada = new Direccion();
        Ciudad ciudad2 = new Ciudad();
        ciudad2.setIdCiudad(2L);
        ciudad2.setNombreCiudad("CiudadModificada");
        Comuna comuna2 = new Comuna();
        comuna2.setIdComuna(2L);
        comuna2.setNombreComuna("ComunaModificada");
        Region region2 = new Region();
        region2.setIdRegion(2L);
        region2.setNombreRegion("RegionModificada");
        direccionModificada.setCiudad(ciudad2);
        direccionModificada.setComuna(comuna2);
        direccionModificada.setRegion(region2);
        direccionModificada.setDireccion("Nueva Direccion 456");

        when(direccionRepository.findById(1L)).thenReturn(Optional.of(direccionTest));
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccionModificada);

        Direccion resultado = direccionService.modificarDireccion(1L, direccionModificada);

        assertNotNull(resultado);
        assertEquals("Nueva Direccion 456", resultado.getDireccion());
        verify(direccionRepository, times(1)).save(any(Direccion.class));
    }
}
