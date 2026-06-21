package com.trenzadostm.trenzadostmreportes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InventarioDto {

    private Long idInventario;
    private Long idSucursal;
    private Long idTipoProducto;
    private Long stock;
    private Long stockMinimo;
    private LocalDateTime fechaActualizacion;
}
