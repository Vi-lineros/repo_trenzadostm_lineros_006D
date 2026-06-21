package com.trenzadostm.trenzadostmusuario.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmusuario.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmusuario.model.Usuario;
import com.trenzadostm.trenzadostmusuario.repository.UsuarioRepository;
import com.trenzadostm.trenzadostmusuario.dto.DireccionDto;
import com.trenzadostm.trenzadostmusuario.dto.SucursalDto;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private final ExternalServiceClient client;

    public UsuarioService(UsuarioRepository usuarioRepository, ExternalServiceClient client) {
        this.usuarioRepository = usuarioRepository;
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

        if(unUsuario.getRol() == null){
            unUsuario.setRol("CLIENTE");
        }

        if(unUsuario.getEstadoUsuario() == null){
            unUsuario.setEstadoUsuario("ACTIVO");
        }

        if(direccion == null){
            throw new RuntimeException("la direccion no existe");
        }

        if(unUsuario.getIdSucursal() != null){
            SucursalDto sucursal = client.obtenerSucursal(unUsuario.getIdSucursal());

            if(sucursal == null){
                throw new RuntimeException("la sucursal no existe");
            }
        }
 
        if(!unUsuario.getRol().equalsIgnoreCase("CLIENTE")){

            if(unUsuario.getIdSucursal() == null){
                throw new RuntimeException("el empleado debe tener una sucursal asignada");
            }
        }

        return usuarioRepository.save(unUsuario);
    }

    public Usuario modificarUsuario(Long id, Usuario unUsuario){
        Usuario usuarioExistente = usuarioRepository.findById(id).get();
        DireccionDto direccion = client.obtenerDireccion(unUsuario.getIdDireccion());

        if(direccion == null){
            throw new RuntimeException("la direccion no existe");
        }

        if(unUsuario.getIdSucursal() != null){
            SucursalDto sucursal = client.obtenerSucursal(unUsuario.getIdSucursal());

            if(sucursal == null){
                throw new RuntimeException("la sucursal no existe");
            }
        }

        if(unUsuario.getRol() != null && !unUsuario.getRol().equalsIgnoreCase("CLIENTE")){
            if(unUsuario.getIdSucursal() == null){
                throw new RuntimeException("el empleado debe tener una sucursal asignada");
            }
        }

        usuarioExistente.setIdDireccion(unUsuario.getIdDireccion());
        usuarioExistente.setIdSucursal(unUsuario.getIdSucursal());

        usuarioExistente.setNombre(unUsuario.getNombre());
        usuarioExistente.setRut(unUsuario.getRut());
        usuarioExistente.setCelular(unUsuario.getCelular());
        usuarioExistente.setEmail(unUsuario.getEmail());

        usuarioExistente.setRol(unUsuario.getRol());
        usuarioExistente.setCargo(unUsuario.getCargo());
        usuarioExistente.setEstadoUsuario(unUsuario.getEstadoUsuario());

        return usuarioRepository.save(usuarioExistente);
    }

    public String eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
        return "Usuario eliminado";
    }
}
