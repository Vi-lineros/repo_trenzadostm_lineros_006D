package com.trenzadostm.trenzadostmdireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmdireccion.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>{
     
}
 