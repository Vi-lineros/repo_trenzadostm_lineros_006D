package com.trenzadostm.trenzadostmseguimiento.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnvioDto {

    private Long idEnvio;
    private Long idPedido;
    private Long idRepartidor;
    private String empresaEnvio;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntrega;
    private String estadoEnvio;
}
