package com.trenzadostm.trenzadostmdireccion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmdireccion.model.Region;
import com.trenzadostm.trenzadostmdireccion.services.RegionService;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> mostrarTodas(){
        List<Region> region = regionService.buscarTodas();
        if (region.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(region);
    }

    @PostMapping
    public ResponseEntity<Region> guardarRegion(@RequestBody Region unaRegion){
        Region regionNueva = regionService.guardarRegion(unaRegion);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> modificarRegion(@PathVariable Long id, @RequestBody Region unaRegion) {
        try {
            Region regionActualizada = regionService.modificarRegion(id, unaRegion);
            return ResponseEntity.ok(regionActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> buscarRegion(@PathVariable Long id){
        try{
            Region region = regionService.buscarRegionPorId(id);
            return ResponseEntity.ok(region);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Long id) {
        try {
            regionService.eliminarRegion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
