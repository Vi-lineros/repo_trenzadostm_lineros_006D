package com.trenzadostm.trenzadostminventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trenzadostm.trenzadostminventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

}
