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
import com.trenzadostm.trenzadostmpedido.dto.DireccionDto;

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
        DireccionDto direccion = externalServiceClient.obtenerDireccion(unPedido.getIdDireccion());
        if (direccion == null){
            throw new RuntimeException("Dirección no encontrada");
        }

        BigDecimal iva = unPedido.getTotal().multiply(BigDecimal.valueOf(0.19));
        BigDecimal totalConIva = unPedido.getTotal().add(iva);

        pedidoExistente.setIdUsuario(unPedido.getIdUsuario());
        pedidoExistente.setIdDireccion(unPedido.getIdDireccion());
        pedidoExistente.setIdSucursal(unPedido.getIdSucursal());
        pedidoExistente.setTotal(unPedido.getTotal());
        pedidoExistente.setIva(iva);
        pedidoExistente.setTotalConIva(totalConIva);

        return pedidoRepository.save(pedidoExistente);
    }

    public Pedido guardarPedido(Pedido unPedido){
        UsuarioDto usuario = externalServiceClient.obtenerUsuario(unPedido.getIdUsuario());
            if (usuario == null){
                throw new RuntimeException("Usuario no encontrado");
            }
        DireccionDto direccion = externalServiceClient.obtenerDireccion(unPedido.getIdDireccion());
            if (direccion == null){
                throw new RuntimeException("Dirección no encontrada");
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