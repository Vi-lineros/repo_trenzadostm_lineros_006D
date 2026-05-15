package com.trenzadostm.trenzadostmusuario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmusuario.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmusuario.model.Usuario;
import com.trenzadostm.trenzadostmusuario.repository.UsuarioRepository;
import com.trenzadostm.trenzadostmusuario.dto.DireccionDto;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ExternalServiceClient client;

    public UsuarioService(ExternalServiceClient client) {
        this.client = client;
    }
    

    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    } 

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).get();
    }

    public Usuario guardarUsuario(Usuario unUsuario){
        DireccionDto direccion = client.obtenerDireccion(unUsuario.getIdDireccion());

        if(direccion == null){
            throw new RuntimeException("la direccion no existe");
        }

        return usuarioRepository.save(unUsuario);
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
}
