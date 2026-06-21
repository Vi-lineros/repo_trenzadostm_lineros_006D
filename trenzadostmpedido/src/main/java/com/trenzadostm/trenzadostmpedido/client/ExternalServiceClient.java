package com.trenzadostm.trenzadostmpedido.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trenzadostm.trenzadostmpedido.dto.SucursalDto;
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

    public TipoProductoDto obtenerTipoProducto(Long idTipoProducto){
        try{
            return webClient.get()
                    .uri("http://localhost:8084/api/v1/tipo-productos/{id}", idTipoProducto)
                    .retrieve()
                    .bodyToMono(TipoProductoDto.class)
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
