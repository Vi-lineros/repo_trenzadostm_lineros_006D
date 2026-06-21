package com.trenzadostm.trenzadostmreportes.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteVentasDto {

    private Long idReporte;
    private String nombreReporte;
    private String tipoReporte;
    private Long cantidadPedidos;
    private BigDecimal totalVentas;
    private List<PedidoDto> pedidos;
}
