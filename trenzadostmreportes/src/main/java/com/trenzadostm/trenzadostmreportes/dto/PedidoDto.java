package com.trenzadostm.trenzadostmreportes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PedidoDto {

    private Long idPedido;
    private String estadoPedido;
    private BigDecimal total;
    private BigDecimal iva;
    private BigDecimal totalConIva;
    private LocalDateTime fechaPedido;
}
