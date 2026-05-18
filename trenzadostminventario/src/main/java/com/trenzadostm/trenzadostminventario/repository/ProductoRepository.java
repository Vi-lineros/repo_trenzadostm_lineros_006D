package com.trenzadostm.trenzadostminventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trenzadostm.trenzadostminventario.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
