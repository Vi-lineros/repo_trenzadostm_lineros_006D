package com.trenzadostm.trenzadostmenvio.controller;

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

import com.trenzadostm.trenzadostmenvio.model.Envio;
import com.trenzadostm.trenzadostmenvio.services.EnvioService;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> mostrar(){
        List<Envio> envios = envioService.buscarTodos();
        if(envios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarEnvio(@PathVariable Long id){
        try{
            Envio unEnvio = envioService.buscarPorId(id);
            return ResponseEntity.ok(unEnvio);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Envio> buscarPorPedido(@PathVariable Long idPedido){
        try{
            Envio envio = envioService.buscarPorPedido(idPedido);
            if(envio == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(envio);
            
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarEnvio(@RequestBody Envio unEnvio){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(envioService.guardarEnvio(unEnvio));
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> modificarEnvio(@PathVariable Long id, @RequestBody Envio unEnvio){
        try{
            Envio envioActualizado = envioService.modificarEnvio(id, unEnvio);
            return ResponseEntity.ok(envioActualizado);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarEnvio(@PathVariable Long id) {
        try{
            envioService.eliminarEnvio(id);
            return "Envio eliminado";

        } catch(Exception e){
            return "Envio no encontrado";
        }
    }
}
