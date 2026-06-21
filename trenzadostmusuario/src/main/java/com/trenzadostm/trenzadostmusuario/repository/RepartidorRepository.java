package com.trenzadostm.trenzadostmusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmusuario.model.Repartidor;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long>{

}
