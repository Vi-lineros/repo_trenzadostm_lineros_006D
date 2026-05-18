package com.trenzadostm.trenzadostmpago.controller;

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

import com.trenzadostm.trenzadostmpago.model.Banco;
import com.trenzadostm.trenzadostmpago.services.BancoService;

@RestController
@RequestMapping("/api/v1/bancos")
public class BancoController {

    @Autowired
    private BancoService bancoService;
    
    @GetMapping
    public ResponseEntity<List<Banco>> mostrarTodos(){
        List<Banco> bancos = bancoService.buscarTodos();
        if(bancos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bancos);
    }

    @PostMapping
    public ResponseEntity<Banco> guardarBanco(@RequestBody Banco unBanco){
        Banco bancoNuevo = bancoService.guardarBanco(unBanco);
        return ResponseEntity.status(HttpStatus.CREATED).body(bancoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banco> modificarBanco(@PathVariable Long id, @RequestBody Banco unBanco){
        try{
            Banco bancoActualizado = bancoService.modificarBanco(id, unBanco);
            return ResponseEntity.ok(bancoActualizado);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Banco> buscarPorId(@PathVariable Long id){
        try{
            Banco unBanco = bancoService.buscarPorId(id);
            return ResponseEntity.ok(unBanco);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBanco(@PathVariable Long id){
        try{
            bancoService.eliminarBanco(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
