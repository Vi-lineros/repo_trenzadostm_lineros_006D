package com.trenzadostm.trenzadostmenvio.model;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envio")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @Column(name = "id_pedido", nullable = false, unique = true)
    private Long idPedido;

    @Column(name = "id_repartidor", nullable = true)
    private Long idRepartidor;
    
    @Column(name = "empresa_envio", nullable = true, length = 100)
    private String empresaEnvio;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "estado_envio", nullable = false, length = 50)
    private String estadoEnvio;

}
