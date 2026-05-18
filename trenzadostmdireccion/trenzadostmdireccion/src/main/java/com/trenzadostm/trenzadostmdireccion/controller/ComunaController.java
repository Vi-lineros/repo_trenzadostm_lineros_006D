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

import com.trenzadostm.trenzadostmdireccion.model.Comuna;
import com.trenzadostm.trenzadostmdireccion.services.ComunaService;

@RestController
@RequestMapping("/api/v1/comuna")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> mostrarTodas(){
        List<Comuna> comuna = comunaService.buscarTodas();
        if (comuna.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comuna);
    }

    @PostMapping
    public ResponseEntity<Comuna> guardarComuna(@RequestBody Comuna unaComuna){
        Comuna comunaNueva = comunaService.guardarComuna(unaComuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> modificarComuna(@PathVariable Long id, @RequestBody Comuna unaComuna) {
        try {
            Comuna comunaActualizada = comunaService.modificarComuna(id, unaComuna);
            return ResponseEntity.ok(comunaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> buscarComuna(@PathVariable Long id){
        try{
            Comuna comuna = comunaService.buscarComunaPorId(id);
            return ResponseEntity.ok(comuna);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComuna(@PathVariable Long id) {
        try {
            comunaService.eliminarComuna(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}