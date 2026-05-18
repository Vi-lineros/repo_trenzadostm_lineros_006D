package com.trenzadostm.trenzadostmpedido.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TipoProductoDto {

    private Long idTipoProducto;
    private String nombreProducto;
    private String medida;
    private String color;
    private BigDecimal precioSinIva;

}
