package com.trenzadotsm.trenzadostmsucursal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadotsm.trenzadostmsucursal.model.Sucursal;
import com.trenzadotsm.trenzadostmsucursal.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> buscarTodos() {
        return sucursalRepository.findAll();
    }

    public Sucursal buscarPorId(Long idSucursal){
        return sucursalRepository.findById(idSucursal).get();
    }

    public Sucursal modificarSucursal(Long idSucursal, Sucursal sucursal){

        Sucursal sucursalExistente = sucursalRepository.findById(idSucursal).get();

        sucursalExistente.setIdDireccion(sucursal.getIdDireccion());
        sucursalExistente.setNombreSucursal(sucursal.getNombreSucursal());
        sucursalExistente.setTelefono(sucursal.getTelefono());
        sucursalExistente.setCorreo(sucursal.getCorreo());
        sucursalExistente.setEstadoSucursal(sucursal.getEstadoSucursal());

        return sucursalRepository.save(sucursalExistente);
    }

    public Sucursal guardarSucursal(Sucursal sucursal){
        return sucursalRepository.save(sucursal);
    }

    public void eliminarSucursal(Long idSucursal){
        sucursalRepository.deleteById(idSucursal);
    }
}
