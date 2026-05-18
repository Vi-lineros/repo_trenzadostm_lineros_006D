package com.trenzadostm.trenzadostmpedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {

    private Long idUsuario;
    private Long idDireccion;
    private String nombre;
    private String rut;
    private String celular;
    private String email;

}