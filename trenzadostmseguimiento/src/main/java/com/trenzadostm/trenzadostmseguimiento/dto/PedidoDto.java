package com.trenzadostm.trenzadostmseguimiento.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PedidoDto {

    private Long idPedido;
    private Long idUsuario;
    private Long idSucursal;
    private String opcionEntrega;
    private String estadoPedido;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private BigDecimal iva;
    private BigDecimal totalConIva;
}
