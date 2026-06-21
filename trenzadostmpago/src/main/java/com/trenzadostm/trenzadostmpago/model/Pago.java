package com.trenzadostm.trenzadostmpago.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "id_banco", nullable = false)
    private Banco banco;    

    @Column(name = "numero_cuenta", nullable = false)
    private String numeroCuenta;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "correo_pagador", nullable = false)
    private String correoPagador;

    @Column(name = "mensaje", nullable = true)
    private String mensaje;
    
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago;
}
