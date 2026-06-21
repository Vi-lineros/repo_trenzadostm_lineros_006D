package com.trenzadostm.trenzadostmseguimiento.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmseguimiento.dto.EnvioDto;
import com.trenzadostm.trenzadostmseguimiento.dto.PedidoDto;
import com.trenzadostm.trenzadostmseguimiento.dto.RepartidorDto;

@Service
public class ExternalServiceClient {

    private final WebClient webClient;

    public ExternalServiceClient(WebClient webClient){
        this.webClient = webClient;
    }

    public PedidoDto obtenerPedido(Long idPedido){

        return webClient.get()
                .uri("http://localhost:8085/api/v1/pedidos/" + idPedido)
                .retrieve()
                .bodyToMono(PedidoDto.class)
                .block();
    }

    public EnvioDto obtenerEnvioPorPedido(Long idPedido){

        return webClient.get()
                .uri("http://localhost:8087/api/v1/envios/pedido/" + idPedido)
                .retrieve()
                .bodyToMono(EnvioDto.class)
                .block();
    }

    public RepartidorDto obtenerRepartidor(Long idRepartidor){

        return webClient.get()
                .uri("http://localhost:8083/api/v1/repartidores/" + idRepartidor)
                .retrieve()
                .bodyToMono(RepartidorDto.class)
                .block();
    }
}
