package com.trenzadostm.trenzadostminventario.controller;

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

import com.trenzadostm.trenzadostminventario.model.Inventario;
import com.trenzadostm.trenzadostminventario.services.InventarioService;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> mostrarTodos(){

        List<Inventario> inventario = inventarioService.buscarTodos();

        if (inventario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    public ResponseEntity<Inventario> guardarInventario(@RequestBody Inventario unInventario){

        Inventario inventarioNuevo = inventarioService.guardarInventario(unInventario);

        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> modificarInventario(@PathVariable Long id,
            @RequestBody Inventario unInventario){

        try{

            Inventario inventarioActualizado =
                    inventarioService.modificarInventario(id, unInventario);

            return ResponseEntity.ok(inventarioActualizado);

        } catch (Exception e){

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscarInventario(@PathVariable Long id){

        try{

            Inventario inventario =
                    inventarioService.buscarInventarioPorId(id);

            return ResponseEntity.ok(inventario);

        } catch (Exception e){

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id){

        try{

            inventarioService.eliminarInventario(id);

            return ResponseEntity.noContent().build();

        } catch (Exception e){

            return ResponseEntity.notFound().build();
        }
    }
}
