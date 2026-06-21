package com.trenzadostm.trenzadostmenvio.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmenvio.dto.PedidoDto;
import com.trenzadostm.trenzadostmenvio.dto.RepartidorDto;

@Service
public class ExternalServiceClient {

    private final WebClient webClient;

    public ExternalServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public PedidoDto obtenerPedido(Long idPedido){
        try{
            return webClient.get()
                    .uri("http://localhost:8085/api/v1/pedidos/{id}", idPedido)
                    .retrieve()
                    .bodyToMono(PedidoDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public RepartidorDto obtenerRepartidor(Long idRepartidor){
        try{
            return webClient.get()
                    .uri("http://localhost:8083/api/v1/repartidores/{id}", idRepartidor)
                    .retrieve()
                    .bodyToMono(RepartidorDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
