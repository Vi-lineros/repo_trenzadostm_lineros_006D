package com.trenzadostm.trenzadostmpago.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "banco")
public class Banco {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idBanco;

    @Column(name = "nombre_banco", nullable = false)
    private String nombreBanco;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;
}
