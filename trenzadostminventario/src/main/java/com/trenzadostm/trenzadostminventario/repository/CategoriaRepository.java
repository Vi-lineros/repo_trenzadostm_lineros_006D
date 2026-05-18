package com.trenzadostm.trenzadostminventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trenzadostm.trenzadostminventario.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
