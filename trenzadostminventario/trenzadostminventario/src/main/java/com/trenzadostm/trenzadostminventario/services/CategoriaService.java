package com.trenzadostm.trenzadostminventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostminventario.model.Categoria;
import com.trenzadostm.trenzadostminventario.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> buscarTodos(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Long idCategoria){
        return categoriaRepository.findById(idCategoria).get();
    }

    public Categoria modificarCategoria(Long idCategoria, Categoria unaCategoria){
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria).get();

        categoriaExistente.setNombreCategoria(unaCategoria.getNombreCategoria());
        categoriaExistente.setDescripcionCategoria(unaCategoria.getDescripcionCategoria());
        
        return categoriaRepository.save(categoriaExistente);
    }

    public Categoria guardarCategoria(Categoria unaCategoria){
        return categoriaRepository.save(unaCategoria);
    }

    public void eliminarCategoria(Long idCategoria){
        categoriaRepository.deleteById(idCategoria);
    }

}

