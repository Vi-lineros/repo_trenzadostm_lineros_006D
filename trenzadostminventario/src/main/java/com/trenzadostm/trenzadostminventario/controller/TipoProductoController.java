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

import com.trenzadostm.trenzadostminventario.model.TipoProducto;
import com.trenzadostm.trenzadostminventario.services.TipoProductoService;

@RestController
@RequestMapping("/api/v1/tipo-productos")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProducto>> mostrarTodos(){
        List<TipoProducto> tipoProducto = tipoProductoService.buscarTodos();
        if (tipoProducto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipoProducto);
    }

    @PostMapping
    public ResponseEntity<TipoProducto> guardarTipoProducto(@RequestBody TipoProducto unTipoProducto){
        TipoProducto tipoProductoNuevo = tipoProductoService.guardarTipoProducto(unTipoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoProductoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProducto> modificarTipoProducto(@PathVariable Long id, @RequestBody TipoProducto unTipoProducto) {
        try {
            TipoProducto tipoProductoActualizado = tipoProductoService.modificarTipoProducto(id, unTipoProducto);
            return ResponseEntity.ok(tipoProductoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProducto> buscarTipoProducto(@PathVariable Long id){
        try{
            TipoProducto tipoProducto = tipoProductoService.buscarTipoProductoPorId(id);
            return ResponseEntity.ok(tipoProducto);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoProducto(@PathVariable Long id) {
        try {
            tipoProductoService.eliminarTipoProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
