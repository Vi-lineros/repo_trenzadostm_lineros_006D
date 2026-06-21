package com.trenzadotsm.trenzadostmsucursal;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadotsm.trenzadostmsucursal.model.Sucursal;
import com.trenzadotsm.trenzadostmsucursal.repository.SucursalRepository;
import com.trenzadotsm.trenzadostmsucursal.services.SucursalService;

@ExtendWith(MockitoExtension.class)
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursalTest;

    @BeforeEach
    void setUp(){
        sucursalTest = new Sucursal();
        sucursalTest.setIdSucursal(1L);
        sucursalTest.setIdDireccion(1L);
        sucursalTest.setNombreSucursal("Sucursal Centro");
        sucursalTest.setTelefono("+56 9 1234 5678");
        sucursalTest.setCorreo("centro@sucursal.cl");
        sucursalTest.setEstadoSucursal("Activa");
    }

    @Test
    @DisplayName("debe devolver la lista con todas las sucursales")
    void testBuscarTodos(){
        List<Sucursal> listaSimulada = Arrays.asList(sucursalTest, new Sucursal());
        when(sucursalRepository.findAll()).thenReturn(listaSimulada);

        List<Sucursal> resultado = sucursalService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver una sucursal por id exitoso")
    void testBuscarPorIdExitoso(){
        when(sucursalRepository.findById(1L)).thenReturn(java.util.Optional.of(sucursalTest));

        Sucursal resultado = sucursalService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSucursal());
        assertEquals("Sucursal Centro", resultado.getNombreSucursal());
    }

    @Test
    @DisplayName("debe lanzar una excepcion cuando el id de sucursal no existe")
    void testBuscarPorIdFallido(){
        when(sucursalRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            sucursalService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("debe guardar y retornar la sucursal creada")
    void testCrearSucursal(){
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalTest);

        Sucursal resultado = sucursalService.guardarSucursal(sucursalTest);

        assertNotNull(resultado);
        assertEquals("Sucursal Centro", resultado.getNombreSucursal());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("debe ejecutar la eliminacion de la sucursal correctamente")
    void testEliminarSucursal(){
        sucursalService.eliminarSucursal(1L);

        verify(sucursalRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar una sucursal existente exitosamente")
    void testModificarSucursal(){
        Sucursal sucursalModificada = new Sucursal();
        sucursalModificada.setIdDireccion(1L);
        sucursalModificada.setNombreSucursal("Sucursal Centro Modificada");
        sucursalModificada.setTelefono("+56 9 9999 9999");
        sucursalModificada.setCorreo("centromod@sucursal.cl");
        sucursalModificada.setEstadoSucursal("Activa");

        when(sucursalRepository.findById(1L)).thenReturn(java.util.Optional.of(sucursalTest));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalModificada);

        Sucursal resultado = sucursalService.modificarSucursal(1L, sucursalModificada);

        assertNotNull(resultado);
        assertEquals("Sucursal Centro Modificada", resultado.getNombreSucursal());
        assertEquals("+56 9 9999 9999", resultado.getTelefono());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }
}
