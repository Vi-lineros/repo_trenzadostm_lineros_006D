package com.trenzadostm.trenzadostmusuario;

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

import com.trenzadostm.trenzadostmusuario.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmusuario.dto.DireccionDto;
import com.trenzadostm.trenzadostmusuario.model.Usuario;
import com.trenzadostm.trenzadostmusuario.repository.UsuarioRepository;
import com.trenzadostm.trenzadostmusuario.services.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ExternalServiceClient externalServiceClient;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioTest;

    @BeforeEach
    void setUp(){
        
        usuarioTest = new Usuario();
        
        usuarioTest.setIdUsuario(1L);
        usuarioTest.setIdDireccion(1L);
        usuarioTest.setNombre("vicente");
        usuarioTest.setRut("21.704.131-7");
        usuarioTest.setCelular("+56 9 5221 5366");
        usuarioTest.setEmail("vicentelineros@gmail.com");
    } 

    @Test
    @DisplayName("debe devolver la lista con todos los usuarios")
    void testBuscarTodos(){
        List<Usuario> listaSimulada = Arrays.asList(usuarioTest, new Usuario());
        when(usuarioRepository.findAll()).thenReturn(listaSimulada);

        List<Usuario> resultado = usuarioService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe devolver un usuario por su id exitoso")
    void testBuscarPorIdExitoso(){
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioTest));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdUsuario());
        assertEquals("vicente", resultado.getNombre());
    }
    
    @Test
    @DisplayName("debe devolver una excepcion si no existe")
    void testBuscarPorIdFallido(){
        when(usuarioRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            usuarioService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("debe crear un nuevo usuario")
    void testCrearUsuario(){
        when(externalServiceClient.obtenerDireccion(any(Long.class))).thenReturn(new DireccionDto());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        Usuario resultado = usuarioService.guardarUsuario(usuarioTest);

        assertNotNull(resultado);
        assertEquals("vicente", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("debe eliminar un usuario por su id")
    void testEliminarUsuario(){
        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un usuario existente exitosamente")
    void testModificarUsuario(){
        usuarioTest.setNombre("juan modificado");
        usuarioTest.setEmail("juan.modificado@gmail.com");
        
        when(externalServiceClient.obtenerDireccion(any(Long.class))).thenReturn(new DireccionDto());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        Usuario resultado = usuarioService.guardarUsuario(usuarioTest);

        assertNotNull(resultado);
        assertEquals("juan modificado", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
