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

import com.trenzadostm.trenzadostmdireccion.model.Ciudad;
import com.trenzadostm.trenzadostmdireccion.services.CiudadService;

@RestController
@RequestMapping("/api/v1/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping
    public ResponseEntity<List<Ciudad>> mostrarTodas(){
        List<Ciudad> ciudad = ciudadService.buscarTodos();
        if (ciudad.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ciudad);
    }

    @PostMapping
    public ResponseEntity<Ciudad> guardarCiudad(@RequestBody Ciudad unaCiudad){
        Ciudad ciudadNueva = ciudadService.guardarCiudad(unaCiudad);
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> modificarCiudad(@PathVariable Long id, @RequestBody Ciudad unaCiudad) {
        try {
            Ciudad ciudadActualizada = ciudadService.modificarCiudad(id, unaCiudad);
            return ResponseEntity.ok(ciudadActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> buscarCiudad(@PathVariable Long id){
        try{
            Ciudad ciudad = ciudadService.buscarCiudadPorId(id);
            return ResponseEntity.ok(ciudad);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable Long id) {
        try {
            ciudadService.eliminarCiudad(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}