package com.trenzadostm.trenzadostmreportes.model;

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
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;

    @Column(name = "nombre_reporte", nullable = false)
    private String nombreReporte;

    @Column(name = "tipo_reporte", nullable = false)
    private String tipoReporte;
    
    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;
}
