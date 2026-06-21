package com.trenzadostm.trenzadostmdireccion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmdireccion.model.Comuna;
import com.trenzadostm.trenzadostmdireccion.repository.ComunaRepository;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> buscarTodas() {
        return comunaRepository.findAll();
    }

    public Comuna buscarComunaPorId(Long id) {
        return comunaRepository.findById(id).get();
    }

    public Comuna modificarComuna(Long id, Comuna unaComuna) {
        Comuna comunaExistente = comunaRepository.findById(id).get();

        comunaExistente.setNombreComuna(unaComuna.getNombreComuna());

        return comunaRepository.save(comunaExistente);
    }

    public Comuna guardarComuna(Comuna unaComuna) {
        return comunaRepository.save(unaComuna);
    }

    public void eliminarComuna(Long id) {
        comunaRepository.deleteById(id);
    }
}
