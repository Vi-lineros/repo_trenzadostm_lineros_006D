package com.trenzadostm.trenzadostmseguimiento.services;

import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmseguimiento.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmseguimiento.dto.EnvioDto;
import com.trenzadostm.trenzadostmseguimiento.dto.PedidoDto;
import com.trenzadostm.trenzadostmseguimiento.dto.RepartidorDto;
import com.trenzadostm.trenzadostmseguimiento.dto.SeguimientoDto;

@Service
public class SeguimientoService {

    private final ExternalServiceClient client;

    public SeguimientoService(ExternalServiceClient client) {
        this.client = client;
    }

    public SeguimientoDto obtenerSeguimiento(Long idPedido){
        PedidoDto pedido = client.obtenerPedido(idPedido);

        if(pedido == null){
            throw new RuntimeException("el pedido no existe");
        }

        EnvioDto envio = client.obtenerEnvioPorPedido(idPedido);

        if(envio == null){
            throw new RuntimeException("el envio no existe");
        }

        SeguimientoDto seguimiento = new SeguimientoDto();

        seguimiento.setIdPedido(pedido.getIdPedido());
        seguimiento.setEstadoPedido(pedido.getEstadoPedido());
        seguimiento.setEstadoEnvio(envio.getEstadoEnvio());
        seguimiento.setEmpresaEnvio(envio.getEmpresaEnvio());
        seguimiento.setFechaEnvio(envio.getFechaEnvio());
        seguimiento.setFechaEntrega(envio.getFechaEntrega());

        if(envio.getIdRepartidor() != null){
            RepartidorDto repartidor = client.obtenerRepartidor(envio.getIdRepartidor());

            if(repartidor != null){
                seguimiento.setTipoVehiculo(repartidor.getTipoVehiculo());
                seguimiento.setPatente(repartidor.getPatente());
            }
        }
        return seguimiento;
    }
}

