package com.trenzadostm.trenzadostmusuario.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmusuario.dto.DireccionDto;
import com.trenzadostm.trenzadostmusuario.dto.SucursalDto;

@Service
public class ExternalServiceClient {

    private final WebClient webClient;

    public ExternalServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public DireccionDto obtenerDireccion(Long idDireccion){
        try{
            return webClient.get()
                    .uri("http://localhost:8081/api/v1/direcciones/{id}", idDireccion)
                    .retrieve()
                    .bodyToMono(DireccionDto.class)
                    .block();
        } catch (Exception e){
            return null;
        }
    }

    public SucursalDto obtenerSucursal(Long idSucursal){
        try{
            return webClient.get()
                    .uri("http://localhost:8082/api/v1/sucursales/{id}", idSucursal)
                    .retrieve()
                    .bodyToMono(SucursalDto.class)
                    .block();
        } catch (Exception e){
            return null;
        }
    }
}
