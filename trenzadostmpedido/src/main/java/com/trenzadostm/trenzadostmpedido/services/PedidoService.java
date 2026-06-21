package com.trenzadostm.trenzadostmpedido.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmpedido.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmpedido.model.Pedido;
import com.trenzadostm.trenzadostmpedido.repository.PedidoRepository;

import com.trenzadostm.trenzadostmpedido.dto.UsuarioDto;
import com.trenzadostm.trenzadostmpedido.dto.SucursalDto;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ExternalServiceClient externalServiceClient;

    public List<Pedido> buscarTodos(){
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long idPedido){
        return pedidoRepository.findById(idPedido).get();
    }

    public Pedido modificarPedido(Long idPedido, Pedido unPedido){
        Pedido pedidoExistente = pedidoRepository.findById(idPedido).get();
        UsuarioDto usuario = externalServiceClient.obtenerUsuario(unPedido.getIdUsuario());
        if (usuario == null){
            throw new RuntimeException("Usuario no encontrado");
        }
        if (usuario.getIdDireccion() == null){
            throw new RuntimeException("El usuario no tiene dirección asignada");
        }
        SucursalDto sucursal = externalServiceClient.obtenerSucursal(unPedido.getIdSucursal());
        if(sucursal == null){
            throw new RuntimeException("Sucursal no encontrada");
        }
        if (sucursal.getIdDireccion() == null){
            throw new RuntimeException("La sucursal no tiene dirección asignada");
        }

        if(unPedido.getOpcionEntrega() == null){
            throw new RuntimeException("Debe indicar una opción de entrega");
        }
        
        pedidoExistente.setIdUsuario(unPedido.getIdUsuario());
        pedidoExistente.setIdSucursal(unPedido.getIdSucursal());
        pedidoExistente.setOpcionEntrega(unPedido.getOpcionEntrega());

        return pedidoRepository.save(pedidoExistente);
    }

    public Pedido guardarPedido(Pedido unPedido){
        UsuarioDto usuario = externalServiceClient.obtenerUsuario(unPedido.getIdUsuario());
            if (usuario == null){
                throw new RuntimeException("Usuario no encontrado");
            }
            if (usuario.getIdDireccion() == null){
                throw new RuntimeException("El usuario no tiene dirección asignada");
            }
        SucursalDto sucursal = externalServiceClient.obtenerSucursal(unPedido.getIdSucursal());
            if(sucursal == null){
                throw new RuntimeException("Sucursal no encontrada");
            }
            if (sucursal.getIdDireccion() == null){
                throw new RuntimeException("La sucursal no tiene dirección asignada");
            }
        if(unPedido.getOpcionEntrega() == null){
            throw new RuntimeException("Debe indicar una opción de entrega");
        }
        unPedido.setFechaPedido(LocalDateTime.now());
        unPedido.setEstadoPedido("PENDIENTE");
        
        unPedido.setTotal(BigDecimal.ZERO);
        unPedido.setIva(BigDecimal.ZERO);
        unPedido.setTotalConIva(BigDecimal.ZERO);

        return pedidoRepository.save(unPedido);
    }

    public void eliminarPedido(Long idPedido){
        pedidoRepository.deleteById(idPedido);
    }

}