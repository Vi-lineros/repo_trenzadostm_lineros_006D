package com.trenzadostm.trenzadostmpago.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmpago.dto.PedidoDto;

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
}
