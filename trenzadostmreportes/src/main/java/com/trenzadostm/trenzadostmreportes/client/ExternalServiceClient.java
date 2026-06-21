package com.trenzadostm.trenzadostmreportes.client;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmreportes.dto.EnvioDto;
import com.trenzadostm.trenzadostmreportes.dto.InventarioDto;
import com.trenzadostm.trenzadostmreportes.dto.PedidoDto;

@Service
public class ExternalServiceClient {

    private final WebClient webClient;

    public ExternalServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public EnvioDto obtenerEnvio(Long idEnvio){
        try{
            return webClient.get()
                    .uri("http://localhost:8087/api/v1/envios/{id}", idEnvio)
                    .retrieve()
                    .bodyToMono(EnvioDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<EnvioDto> obtenerEnvios() {
        return webClient.get()
                .uri("http://localhost:8087/api/v1/envios")
                .retrieve()
                .bodyToFlux(EnvioDto.class)
                .collectList()
                .block();
    }

    public InventarioDto obtenerInventario(Long idInventario){
        try{
            return webClient.get()
                    .uri("http://localhost:8084/api/v1/inventario/{id}", idInventario)
                    .retrieve()
                    .bodyToMono(InventarioDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public List<InventarioDto> obtenerInventario() {
        return webClient.get()
                .uri("http://localhost:8084/api/v1/inventario")
                .retrieve()
                .bodyToFlux(InventarioDto.class)
                .collectList()
                .block();
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

    public List<PedidoDto> obtenerPedidos() {
        return webClient.get()
                .uri("http://localhost:8085/api/v1/pedidos")
                .retrieve()
                .bodyToFlux(PedidoDto.class)
                .collectList()
                .block();
    }
}
