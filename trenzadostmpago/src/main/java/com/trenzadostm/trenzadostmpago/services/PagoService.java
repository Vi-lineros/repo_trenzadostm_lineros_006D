package com.trenzadostm.trenzadostmpago.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmpago.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmpago.dto.PedidoDto;
import com.trenzadostm.trenzadostmpago.model.Pago;
import com.trenzadostm.trenzadostmpago.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ExternalServiceClient externalServiceClient;

    public List<Pago> buscarTodos(){
        return pagoRepository.findAll();
    }

    public Pago buscarPagoPorId(Long idPago){
        return pagoRepository.findById(idPago).get();
    }

    public Pago modificarEstadoPago(Long idPago, String nuevoEstado){
        Pago pagoExistente = pagoRepository.findById(idPago).get();
        pagoExistente.setEstadoPago(nuevoEstado);
        return pagoRepository.save(pagoExistente);
    }

    public Pago guardarPago(Pago unPago){
        PedidoDto pedidoDto = externalServiceClient.obtenerPedido(unPago.getIdPedido());

        if (pedidoDto == null) {
            throw new RuntimeException("No se pudo obtener el pedido con ID: " + unPago.getIdPedido());
        }

        unPago.setMonto(pedidoDto.getTotalConIva());

        unPago.setFechaPago(LocalDateTime.now());

        unPago.setEstadoPago("PENDIENTE");

        return pagoRepository.save(unPago);
    }
}