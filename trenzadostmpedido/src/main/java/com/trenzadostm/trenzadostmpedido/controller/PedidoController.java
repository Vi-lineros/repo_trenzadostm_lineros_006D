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

import com.trenzadostm.trenzadostmpedido.model.Pedido;
import com.trenzadostm.trenzadostmpedido.services.PedidoService;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> mostrarTodos(){
        List<Pedido> pedidos = pedidoService.buscarTodos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> guardarPedido(@RequestBody Pedido unPedido){
        Pedido pedidoNuevo = pedidoService.guardarPedido(unPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> modificarPedido(@PathVariable Long id, @RequestBody Pedido unPedido){
        try {
            Pedido pedidoActualizado = pedidoService.modificarPedido(id, unPedido);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id){
        try {
            Pedido pedido = pedidoService.buscarPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id){
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}