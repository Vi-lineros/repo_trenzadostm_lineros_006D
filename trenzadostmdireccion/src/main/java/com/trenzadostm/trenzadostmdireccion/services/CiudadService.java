package com.trenzadostm.trenzadostmdireccion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmdireccion.model.Ciudad;
import com.trenzadostm.trenzadostmdireccion.repository.CiudadRepository;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public List<Ciudad> buscarTodos() {
        return ciudadRepository.findAll();
    }

    public Ciudad buscarCiudadPorId(Long id) {
        return ciudadRepository.findById(id).get();
    }

    public Ciudad modificarCiudad(Long id, Ciudad unaCiudad) {
        Ciudad ciudadExistente = ciudadRepository.findById(id).get();

        ciudadExistente.setNombreCiudad(unaCiudad.getNombreCiudad());

        return ciudadRepository.save(ciudadExistente);
    }

    public Ciudad guardarCiudad(Ciudad unaCiudad) {
        return ciudadRepository.save(unaCiudad);
    }

    public void eliminarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }
}
