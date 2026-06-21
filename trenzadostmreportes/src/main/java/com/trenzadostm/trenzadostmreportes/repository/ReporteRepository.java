package com.trenzadostm.trenzadostmreportes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmreportes.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

}
