package com.trenzadostm.trenzadostmusuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trenzadostm.trenzadostmusuario.model.Usuario;
import com.trenzadostm.trenzadostmusuario.services.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> mostrar(){
        List<Usuario> usuarios = usuarioService.buscarTodos();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id){
        try{
            Usuario unUsuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(unUsuario);

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario unUsuario){
        try{
            return ResponseEntity.ok(usuarioService.guardarUsuario(unUsuario));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> modificarUsuario(@PathVariable Long id, @RequestBody Usuario unUsuario){
        try{
            Usuario us = usuarioService.buscarPorId(id);
            us.setIdUsuario(id);
            us.setIdDireccion(unUsuario.getIdDireccion());
            us.setNombre(unUsuario.getNombre());
            us.setRut(unUsuario.getRut());
            us.setCelular(unUsuario.getCelular());
            us.setEmail(unUsuario.getEmail());
            return ResponseEntity.ok(unUsuario);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id){
        try{
            usuarioService.eliminarUsuario(id);
            return "Usuario eliminado";
        } catch (Exception e){
            return "Usuario no encontrado";
        }
    }
}
