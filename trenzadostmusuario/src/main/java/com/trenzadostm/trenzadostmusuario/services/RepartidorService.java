package com.trenzadostm.trenzadostmusuario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trenzadostm.trenzadostmusuario.model.Repartidor;
import com.trenzadostm.trenzadostmusuario.model.Usuario;
import com.trenzadostm.trenzadostmusuario.repository.RepartidorRepository;
import com.trenzadostm.trenzadostmusuario.repository.UsuarioRepository;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Repartidor> buscarTodos(){
        return repartidorRepository.findAll();
    }

    public Repartidor buscarPorId(Long id){
        return repartidorRepository.findById(id).get();
    }

    @Transactional
    public Repartidor guardarRepartidor(Repartidor unRepartidor){

        if(unRepartidor.getUsuario() == null || unRepartidor.getUsuario().getIdUsuario() == null){
            throw new RuntimeException("el repartidor debe tener un usuario asignado");
        }

        Usuario usuario = usuarioService.buscarPorId(unRepartidor.getUsuario().getIdUsuario());
        usuario.setRol("EMPLEADO");
        usuario.setCargo("REPARTIDOR");
        usuarioRepository.save(usuario);

        unRepartidor.setUsuario(usuario);

        if(unRepartidor.getEstadoRepartidor() == null){
            unRepartidor.setEstadoRepartidor("DISPONIBLE");
        }

        return repartidorRepository.save(unRepartidor);
    }

    @Transactional
    public Repartidor modificarRepartidor(Long id, Repartidor unRepartidor){
        Repartidor repartidorExistente = repartidorRepository.findById(id).get();

        if(unRepartidor.getUsuario() == null || unRepartidor.getUsuario().getIdUsuario() == null){
            throw new RuntimeException("el repartidor debe tener un usuario asignado");
        }

        Usuario usuario = usuarioService.buscarPorId(unRepartidor.getUsuario().getIdUsuario());
        usuario.setRol("EMPLEADO");
        usuario.setCargo("REPARTIDOR");
        usuarioRepository.save(usuario);

        repartidorExistente.setUsuario(usuario);
        repartidorExistente.setTipoVehiculo(unRepartidor.getTipoVehiculo());
        repartidorExistente.setPatente(unRepartidor.getPatente());

        if(unRepartidor.getEstadoRepartidor() == null){
            repartidorExistente.setEstadoRepartidor("DISPONIBLE");
        } else {
            repartidorExistente.setEstadoRepartidor(unRepartidor.getEstadoRepartidor());
        }

        return repartidorRepository.save(repartidorExistente);
    }

    public String eliminarRepartidor(Long id){
        repartidorRepository.deleteById(id);
        return "Repartidor eliminado";
    }
}
