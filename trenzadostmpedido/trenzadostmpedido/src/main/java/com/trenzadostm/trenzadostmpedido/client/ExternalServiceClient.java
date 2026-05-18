package com.trenzadostm.trenzadostmpedido.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmpedido.dto.DireccionDto;
import com.trenzadostm.trenzadostmpedido.dto.TipoProductoDto;
import com.trenzadostm.trenzadostmpedido.dto.UsuarioDto;

@Service
public class ExternalServiceClient {

    private final WebClient webClient;

    public ExternalServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UsuarioDto obtenerUsuario(Long idUsuario){
        try{
            return webClient.get()
                    .uri("http://localhost:8083/api/v1/usuarios/{id}", idUsuario)
                    .retrieve()
                    .bodyToMono(UsuarioDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public DireccionDto obtenerDireccion(Long idDireccion){
        try{
            return webClient.get()
                    .uri("http://localhost:8081/api/v1/direccion/{id}", idDireccion)
                    .retrieve()
                    .bodyToMono(DireccionDto.class)
                    .block();
        } catch (Exception e){
            return null;
        }
    }

    public TipoProductoDto obtenerTipoProducto(Long idTipoProducto){
        try{
            return webClient.get()
                    .uri("http://localhost:8084/api/v1/tipo-producto/{id}", idTipoProducto)
                    .retrieve()
                    .bodyToMono(TipoProductoDto.class)
                    .block();
        } catch (Exception e){
            return null;
        }
    }
}
