package com.trenzadotsm.trenzadostmsucursal.controller;

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

import com.trenzadotsm.trenzadostmsucursal.model.Sucursal;
import com.trenzadotsm.trenzadostmsucursal.services.SucursalService;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {
 
    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> mostrarTodos(){
        
        List<Sucursal> sucursal = sucursalService.buscarTodos();

        if(sucursal.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscarSucursal(@PathVariable Long id){
        try{
            Sucursal unaSucursal = sucursalService.buscarPorId(id);
            return ResponseEntity.ok(unaSucursal);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sucursal> guardarSucursal(@RequestBody Sucursal unSucursal){
        Sucursal sucursalNuevo = sucursalService.guardarSucursal(unSucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> modificarSucursal(@PathVariable Long id, @RequestBody Sucursal unSucursal){
        try{
            Sucursal sucursalActualizado = sucursalService.modificarSucursal(id, unSucursal);
            return ResponseEntity.ok(sucursalActualizado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarSucursal(@PathVariable Long id){
        try{
            sucursalService.eliminarSucursal(id);
            return "Sucursal Eliminada";
        } catch (Exception e){
            return "Sucursal no encontrada";
        }
    }
}
