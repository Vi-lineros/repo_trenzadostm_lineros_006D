package com.trenzadostm.trenzadostmpago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmpago.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

}
