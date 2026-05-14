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

import com.trenzadostm.trenzadostmdireccion.model.Direccion;
import com.trenzadostm.trenzadostmdireccion.services.DireccionService;

@RestController
@RequestMapping("api/v1/direccion")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> mostrarTodas(){
        List<Direccion> direccion = direccionService.buscarTodas();
        if (direccion.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direccion);
    }

    @PostMapping
    public ResponseEntity<Direccion> guardarDireccion(@RequestBody Direccion unaDireccion){
        Direccion direccionNueva = direccionService.guardarDireccion(unaDireccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> modificarDireccion(@PathVariable Long id, @RequestBody Direccion unaDireccion) {
        try {
            Direccion direccionActualizada = direccionService.modificarDireccion(id, unaDireccion);
            return ResponseEntity.ok(direccionActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> buscarDireccion(@PathVariable Long id){
        try{
            Direccion direccion = direccionService.buscarDireccionPorId(id);
            return ResponseEntity.ok(direccion);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarDireccion(@PathVariable Long id){
        try{
            direccionService.eliminarDireccion(id);
            return "Direccion eliminada";
        } catch (Exception e){
            return "Direccion no encontrada";
        }
    }
}
