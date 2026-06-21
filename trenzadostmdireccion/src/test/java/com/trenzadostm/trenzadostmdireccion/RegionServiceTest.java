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

import com.trenzadostm.trenzadostmdireccion.model.Region;
import com.trenzadostm.trenzadostmdireccion.repository.RegionRepository;
import com.trenzadostm.trenzadostmdireccion.services.RegionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Region Service Test")
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    private Region regionTest;

    @BeforeEach
    void setUp(){
        regionTest = new Region();
        regionTest.setIdRegion(1L);
        regionTest.setNombreRegion("RegionTest");
    }

    @Test
    @DisplayName("debe devolver la lista con todas las regiones")
    void testBuscarTodos(){
        List<Region> listaSimulada = Arrays.asList(regionTest, new Region());
        when(regionRepository.findAll()).thenReturn(listaSimulada);

        List<Region> resultado = regionService.buscarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(regionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver una region por id exitoso")
    void testBuscarPorIdExitoso(){
        when(regionRepository.findById(1L)).thenReturn(Optional.of(regionTest));

        Region resultado = regionService.buscarRegionPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdRegion());
        assertEquals("RegionTest", resultado.getNombreRegion());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando la region no existe")
    void testBuscarPorIdFallido(){
        when(regionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            regionService.buscarRegionPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar una region nueva exitosamente")
    void testCrearRegion(){
        when(regionRepository.save(any(Region.class))).thenReturn(regionTest);

        Region resultado = regionService.guardarRegion(regionTest);

        assertNotNull(resultado);
        assertEquals("RegionTest", resultado.getNombreRegion());
        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    @DisplayName("debe eliminar una region por su id")
    void testEliminarRegion(){
        regionService.eliminarRegion(1L);

        verify(regionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar una region existente exitosamente")
    void testModificarRegion(){
        Region regionModificada = new Region();
        regionModificada.setNombreRegion("RegionModificada");

        when(regionRepository.findById(1L)).thenReturn(Optional.of(regionTest));
        when(regionRepository.save(any(Region.class))).thenReturn(regionModificada);

        Region resultado = regionService.modificarRegion(1L, regionModificada);

        assertNotNull(resultado);
        assertEquals("RegionModificada", resultado.getNombreRegion());
        verify(regionRepository, times(1)).save(any(Region.class));
    }
}
