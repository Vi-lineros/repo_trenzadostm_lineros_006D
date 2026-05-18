package com.trenzadostm.trenzadostmpedido.controller;

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

import com.trenzadostm.trenzadostmpedido.model.DetallePedido;
import com.trenzadostm.trenzadostmpedido.services.DetallePedidoService;

@RestController
@RequestMapping("/api/v1/detallepedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedido>> mostrarTodos(){

        List<DetallePedido> detallePedidos = detallePedidoService.buscarTodos();

        if (detallePedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detallePedidos);
    }

    @PostMapping
    public ResponseEntity<DetallePedido> guardarDetallePedido(@RequestBody DetallePedido unDetallePedido) {
        DetallePedido detallePedidoNuevo = detallePedidoService.guardarDetallePedido(unDetallePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(detallePedidoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> modificarDetallePedido(@PathVariable Long id, @RequestBody DetallePedido unDetallePedido) {
        try {
            DetallePedido detallePedidoActualizado = detallePedidoService.modificarDetallePedido(id, unDetallePedido);
            return ResponseEntity.ok(detallePedidoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> buscarDetallePedido(@PathVariable Long id) {
        try {
            DetallePedido detallePedido = detallePedidoService.buscarDetallePedidoPorId(id);
            return ResponseEntity.ok(detallePedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetallePedido(@PathVariable Long id) {
        try {
            detallePedidoService.eliminarDetallePedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}