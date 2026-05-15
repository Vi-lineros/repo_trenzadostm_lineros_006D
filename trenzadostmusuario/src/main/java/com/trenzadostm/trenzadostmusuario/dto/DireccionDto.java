package com.trenzadostm.trenzadostmusuario.dto;

import lombok.Data;

@Data
public class DireccionDto {

    private Long idDireccion;

    private Object ciudad;

    private Object comuna;

    private Object region;

    private String direccion;
}