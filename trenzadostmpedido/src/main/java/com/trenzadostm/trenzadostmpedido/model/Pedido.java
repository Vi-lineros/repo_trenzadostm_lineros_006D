package com.trenzadostm.trenzadostmpedido.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_sucursal", nullable = false)
    private Long idSucursal;

    @Column(name = "opcion_entrega", nullable = false)
    private String opcionEntrega;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "estado_pedido", nullable = false)
    private String estadoPedido;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "iva", nullable = false)
    private BigDecimal iva;

    @Column(name = "total_con_iva", nullable = false)
    private BigDecimal totalConIva;

}
