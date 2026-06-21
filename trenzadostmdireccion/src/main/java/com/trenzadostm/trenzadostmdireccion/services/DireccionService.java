package com.trenzadostm.trenzadostmdireccion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmdireccion.model.Direccion;
import com.trenzadostm.trenzadostmdireccion.repository.DireccionRepository;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public List<Direccion> buscarTodas() {
        return direccionRepository.findAll();
    }

    public Direccion buscarDireccionPorId(Long id) {
        return direccionRepository.findById(id).get();
    }

    public Direccion modificarDireccion(Long id, Direccion unaDireccion) {
        Direccion direccionExistente = direccionRepository.findById(id).get();

        direccionExistente.setRegion(unaDireccion.getRegion());
        direccionExistente.setCiudad(unaDireccion.getCiudad());
        direccionExistente.setComuna(unaDireccion.getComuna());
        direccionExistente.setDireccion(unaDireccion.getDireccion());

        return direccionRepository.save(direccionExistente);
    }

    public Direccion guardarDireccion(Direccion unaDireccion) {
        return direccionRepository.save(unaDireccion);
    }

    public void eliminarDireccion(Long id) {
        direccionRepository.deleteById(id);
    }
}
