package com.trenzadostm.trenzadostmpago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trenzadostm.trenzadostmpago.model.Banco;
import com.trenzadostm.trenzadostmpago.repository.BancoRepository;
import com.trenzadostm.trenzadostmpago.services.BancoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Banco Service Test")
class BancoServiceTest {

    @Mock
    private BancoRepository bancoRepository;

    @InjectMocks
    private BancoService bancoService;

    private Banco bancoTest;

    @BeforeEach
    void setUp(){
        bancoTest = new Banco();
        bancoTest.setIdBanco(1L);
        bancoTest.setNombreBanco("Banco Chile");
        bancoTest.setTipoCuenta("Corriente");
    }

    @Test
    @DisplayName("debe obtener la lista de todos los bancos")
    void testBuscarTodos(){
        List<Banco> listaSimulada = Arrays.asList(bancoTest, new Banco());
        when(bancoRepository.findAll()).thenReturn(listaSimulada);

        List<Banco> resultado = bancoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(bancoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe obtener un banco por su id exitosamente")
    void testBuscarPorIdExitoso(){
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(bancoTest));

        Banco resultado = bancoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdBanco());
        assertEquals("Banco Chile", resultado.getNombreBanco());
        verify(bancoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("debe lanzar excepción cuando el banco no existe")
    void testBuscarPorIdFallido(){
        when(bancoRepository.findById(99L)).thenReturn(Optional.empty());

        try {
            bancoService.buscarPorId(99L);
        } catch (Exception e) {
            assertNotNull(e);
        }
        verify(bancoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("debe guardar un banco nuevo exitosamente")
    void testCrearBanco(){
        when(bancoRepository.save(any(Banco.class))).thenReturn(bancoTest);

        Banco resultado = bancoService.guardarBanco(bancoTest);

        assertNotNull(resultado);
        assertEquals("Banco Chile", resultado.getNombreBanco());
        verify(bancoRepository, times(1)).save(any(Banco.class));
    }

    @Test
    @DisplayName("debe ejecutar la eliminación del banco correctamente")
    void testEliminarBanco(){
        bancoService.eliminarBanco(1L);

        verify(bancoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("debe modificar un banco existente exitosamente")
    void testModificarBanco(){
        Banco bancoModificado = new Banco();
        bancoModificado.setNombreBanco("Banco Chile Modificado");
        bancoModificado.setTipoCuenta("Ahorro");

        when(bancoRepository.findById(1L)).thenReturn(Optional.of(bancoTest));
        when(bancoRepository.save(any(Banco.class))).thenReturn(bancoModificado);

        Banco resultado = bancoService.modificarBanco(1L, bancoModificado);

        assertNotNull(resultado);
        assertEquals("Banco Chile Modificado", resultado.getNombreBanco());
        assertEquals("Ahorro", resultado.getTipoCuenta());
        verify(bancoRepository, times(1)).save(any(Banco.class));
    }
}
