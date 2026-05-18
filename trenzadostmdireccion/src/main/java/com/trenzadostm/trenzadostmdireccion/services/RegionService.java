package com.trenzadostm.trenzadostmdireccion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmdireccion.model.Region;
import com.trenzadostm.trenzadostmdireccion.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> buscarTodas() {
        return regionRepository.findAll();
    }

    public Region buscarRegionPorId(Long id) {
        return regionRepository.findById(id).get();
    }

    public Region modificarRegion(Long id, Region unaRegion) {
        Region regionExistente = regionRepository.findById(id).get();

        regionExistente.setNombreRegion(unaRegion.getNombreRegion());

        return regionRepository.save(regionExistente);
    }

    public Region guardarRegion(Region unaRegion) {
        return regionRepository.save(unaRegion);
    }

    public void eliminarRegion(Long id) {
        regionRepository.deleteById(id);
    }
}
