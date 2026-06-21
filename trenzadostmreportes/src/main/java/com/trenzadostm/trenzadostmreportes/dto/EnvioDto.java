package com.trenzadostm.trenzadostmreportes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnvioDto {

    private Long idEnvio;
    private Long idPedido;
    private String estadoEnvio;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntrega;
}
