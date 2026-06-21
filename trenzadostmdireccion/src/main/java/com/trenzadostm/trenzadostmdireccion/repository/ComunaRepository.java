package com.trenzadostm.trenzadostmdireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmdireccion.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long>{

}
