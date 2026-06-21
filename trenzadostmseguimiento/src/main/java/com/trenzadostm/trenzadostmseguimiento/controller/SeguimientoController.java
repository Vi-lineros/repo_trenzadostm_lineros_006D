package com.trenzadostm.trenzadostmseguimiento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmseguimiento.dto.SeguimientoDto;
import com.trenzadostm.trenzadostmseguimiento.services.SeguimientoService;

@RestController
@RequestMapping("/api/v1/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<?> obtenerSeguimiento(@PathVariable Long idPedido){
        try{
            SeguimientoDto seguimiento = seguimientoService.obtenerSeguimiento(idPedido);
            return ResponseEntity.ok(seguimiento);
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    
}
