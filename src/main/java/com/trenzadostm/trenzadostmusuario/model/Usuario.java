package com.trenzadostm.trenzadostmusuario.model;

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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    private Long idDireccion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 13, unique = true)
    private String rut;

    @Column(length = 20, nullable = true)
    private String celular;

    @Column(nullable = false, unique = true)
    private String email;
}
