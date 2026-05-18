package com.trenzadostm.trenzadostminventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostminventario.model.Producto;
import com.trenzadostm.trenzadostminventario.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> buscarTodos(){
        return productoRepository.findAll();
    }

    public Producto buscarProductoPorId(Long idProducto){
        return productoRepository.findById(idProducto).get();
    }

    public Producto modificarProducto(Long idProducto, Producto unProducto){
        Producto productoExistente = productoRepository.findById(idProducto).get();

        productoExistente.setNombreProducto(unProducto.getNombreProducto());
        productoExistente.setDescripcionProducto(unProducto.getDescripcionProducto());
        productoExistente.setTipoMaterial(unProducto.getTipoMaterial());
        productoExistente.setEstadoProducto(unProducto.getEstadoProducto());
        productoExistente.setCategoria(unProducto.getCategoria());

        return productoRepository.save(productoExistente);
    }

    public Producto guardarProducto(Producto unProducto){
        return productoRepository.save(unProducto);
    }

    public void eliminarProducto(Long idProducto){
        productoRepository.deleteById(idProducto);
    }

}
