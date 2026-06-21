package com.trenzadostm.trenzadostmusuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmusuario.model.Repartidor;
import com.trenzadostm.trenzadostmusuario.services.RepartidorService;

@RestController
@RequestMapping("/api/v1/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @GetMapping
    public ResponseEntity<List<Repartidor>> mostrar(){
        List<Repartidor> repartidores = repartidorService.buscarTodos();
        if(repartidores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repartidores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repartidor> buscarRepartidor(@PathVariable Long id){
        try{
            Repartidor unRepartidor = repartidorService.buscarPorId(id);
            return ResponseEntity.ok(unRepartidor);

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearRepartidor(@RequestBody Repartidor unRepartidor){
        try{
            return ResponseEntity.ok(repartidorService.guardarRepartidor(unRepartidor));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repartidor> modificarRepartidor(@PathVariable Long id, @RequestBody Repartidor unRepartidor){
        try{
            Repartidor repartidorActualizado = repartidorService.modificarRepartidor(id, unRepartidor);
            return ResponseEntity.ok(repartidorActualizado);

        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarRepartidor(@PathVariable Long id){
        try{
            repartidorService.eliminarRepartidor(id);
            return "Repartidor eliminado";
        } catch (Exception e){
            return "Repartidor no encontrado";
        }
    }
}
