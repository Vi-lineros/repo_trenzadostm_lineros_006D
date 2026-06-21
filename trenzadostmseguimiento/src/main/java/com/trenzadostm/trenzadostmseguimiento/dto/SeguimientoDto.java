package com.trenzadostm.trenzadostmseguimiento.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SeguimientoDto {

    private Long idPedido;
    private String estadoPedido;
    private String estadoEnvio;
    private String empresaEnvio;
    private String tipoVehiculo;
    private String patente;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntrega;

}
