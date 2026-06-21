package com.trenzadostm.trenzadostmreportes.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteInventarioDto {

    private Long idReporte;
    private String nombreReporte;
    private String tipoReporte;
    private LocalDateTime fechaGeneracion;
    private Long cantidadProductos;
    private List<InventarioDto> productos;
}
