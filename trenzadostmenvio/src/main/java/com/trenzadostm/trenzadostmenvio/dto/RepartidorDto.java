package com.trenzadostm.trenzadostmenvio.dto;

import lombok.Data;

@Data
public class RepartidorDto {

    private Long idRepartidor;
    private Long idUsuario;
    private String tipoVehiculo;
    private String patente;
    private String estadoRepartidor;
}
