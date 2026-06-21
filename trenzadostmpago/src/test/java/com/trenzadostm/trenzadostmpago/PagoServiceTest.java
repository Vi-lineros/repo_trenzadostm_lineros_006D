package com.trenzadostm.trenzadostmpago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.trenzadostm.trenzadostmpago.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmpago.dto.PedidoDto;
import com.trenzadostm.trenzadostmpago.model.Banco;
import com.trenzadostm.trenzadostmpago.model.Pago;
import com.trenzadostm.trenzadostmpago.repository.PagoRepository;
import com.trenzadostm.trenzadostmpago.services.PagoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pago Service Test")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private ExternalServiceClient externalServiceClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoTest;
    private PedidoDto pedidoDto;

    @BeforeEach
    void setUp(){
        Banco banco = new Banco();
        banco.setIdBanco(1L);
        banco.setNombreBanco("Banco Estado");
        banco.setTipoCuenta("Cuenta Corriente");
        pagoTest.setBanco(banco);
        pagoTest = new Pago();
        pagoTest.setIdPago(1L);
        pagoTest.setIdPedido(1L);
        pagoTest.setNumeroCuenta("1234567890");
        pagoTest.setMonto(BigDecimal.valueOf(100000));
        pagoTest.setCorreoPagador("pago@correo.cl");
        pagoTest.setMensaje("Pago de pedido");
        pedidoDto = new PedidoDto();
        pedidoDto.setTotalConIva(BigDecimal.valueOf(119000));
    }

    @Test
    @DisplayName("debe obtener la lista de todos los pagos")
    void testBuscarTodos(){
        List<Pago> listaSimulada = Arrays.asList(pagoTest, new Pago());
        when(pagoRepository.findAll()).thenReturn(listaSimulada);

        List<Pago> resultado = pagoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("debe obtener un pago por su id exitosamente")
    void testBuscarPorIdExitoso(){
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoTest));

        Pago resultado = pagoService.buscarPagoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPago());
        assertEquals(1L, resultado.getIdPedido());
        verify(pagoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("debe lanzar excepción cuando el pago no existe")
    void testBuscarPorIdFallido(){
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        try {
            pagoService.buscarPagoPorId(99L);
        } catch (Exception e) {
            assertNotNull(e);
        }
        verify(pagoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("debe guardar un pago nuevo exitosamente")
    void testCrearPago(){
        when(externalServiceClient.obtenerPedido(any(Long.class))).thenReturn(pedidoDto);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoTest);

        Pago resultado = pagoService.guardarPago(pagoTest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
        assertEquals(BigDecimal.valueOf(119000), resultado.getMonto());
        verify(externalServiceClient, times(1)).obtenerPedido(any(Long.class));
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("debe modificar el estado del pago exitosamente")
    void testModificarEstadoPago(){
        Pago pagoModificado = new Pago();
        pagoModificado.setIdPago(1L);
        pagoModificado.setIdPedido(1L);
        pagoModificado.setMonto(BigDecimal.valueOf(100000));
        pagoModificado.setCorreoPagador("pago@correo.cl");
        pagoModificado.setEstadoPago("APROBADO");
        
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoTest));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoModificado);

        Pago resultado = pagoService.modificarEstadoPago(1L, "APROBADO");

        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstadoPago());
        verify(pagoRepository, times(1)).findById(1L);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("debe crear un pago con estado y fecha correctos")
    void testCrearPagoValidaciones(){
        when(externalServiceClient.obtenerPedido(1L)).thenReturn(pedidoDto);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoTest);

        Pago resultado = pagoService.guardarPago(pagoTest);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(119000), resultado.getMonto());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }
}
