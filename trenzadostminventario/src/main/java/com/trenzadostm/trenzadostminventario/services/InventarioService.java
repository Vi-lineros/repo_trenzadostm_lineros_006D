package com.trenzadostm.trenzadostminventario.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostminventario.model.Inventario;
import com.trenzadostm.trenzadostminventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> buscarTodos(){
        return inventarioRepository.findAll();
    }

    public Inventario buscarInventarioPorId(Long idInventario){
        return inventarioRepository.findById(idInventario).get();
    }

    public Inventario modificarInventario(Long idInventario, Inventario unInventario){

        Inventario inventarioExistente = inventarioRepository.findById(idInventario).get();

        inventarioExistente.setIdSucursal(unInventario.getIdSucursal());
        inventarioExistente.setTipoProducto(unInventario.getTipoProducto());
        inventarioExistente.setStock(unInventario.getStock());
        inventarioExistente.setStockMinimo(unInventario.getStockMinimo());

        inventarioExistente.setFechaActualizacion(LocalDateTime.now());

        return inventarioRepository.save(inventarioExistente);
    }

    public Inventario guardarInventario(Inventario unInventario){

        unInventario.setFechaActualizacion(LocalDateTime.now());

        return inventarioRepository.save(unInventario);
    }

    public void eliminarInventario(Long idInventario){
        inventarioRepository.deleteById(idInventario);
    }
}