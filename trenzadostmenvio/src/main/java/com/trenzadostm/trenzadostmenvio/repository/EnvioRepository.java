package com.trenzadostm.trenzadostmenvio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmenvio.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

}
