package com.trenzadostm.trenzadostmpago.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDto {

    private Long idPedido;
    private Long idUsuario;
    private Long idDireccion;
    private Long idSucursal;
    private LocalDateTime fechaPedido;
    private String estadoPedido;
    private BigDecimal total;
    private BigDecimal iva;
    private BigDecimal totalConIva;
}
