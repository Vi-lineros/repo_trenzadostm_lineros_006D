package com.trenzadostm.trenzadostmpago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmpago.model.Pago;
import com.trenzadostm.trenzadostmpago.services.PagoService;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> mostrarTodos(){
        List<Pago> pagos = pagoService.buscarTodos();

        if(pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }
    
    @PostMapping
    public ResponseEntity<Pago> guardarPago(@RequestBody Pago unPago){
        Pago pagoNuevo = pagoService.guardarPago(unPago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPagoPorId(@PathVariable Long id){
        try{
            Pago unPago = pagoService.buscarPagoPorId(id);
            return ResponseEntity.ok(unPago);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> modificarEstadoPago(@PathVariable Long id, @RequestBody String nuevoEstado){
        try{
            Pago estadoActualizado = pagoService.modificarEstadoPago(id, nuevoEstado);
            return ResponseEntity.ok(estadoActualizado);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
