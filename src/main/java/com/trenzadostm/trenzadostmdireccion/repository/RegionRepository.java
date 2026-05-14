package com.trenzadostm.trenzadostmdireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trenzadostm.trenzadostmdireccion.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

}
