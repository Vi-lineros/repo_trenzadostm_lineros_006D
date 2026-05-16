package com.trenzadostm.trenzadostmpedido.services;

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

    public Pedido modificarPedido(Long idPedido, Pedido unPedido) {
        Pedido pedidoExistente = pedidoRepository.findById(idPedido).get();
        UsuarioDto usuario = externalServiceClient.obtenerUsuario(unPedido.getIdUsuario());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        DireccionDto direccion = externalServiceClient.obtenerDireccion(unPedido.getIdDireccion());
        if (direccion == null) {
            throw new RuntimeException("Dirección no encontrada");
        }

        pedidoExistente.setIdUsuario(unPedido.getIdUsuario());
        pedidoExistente.setIdDireccion(unPedido.getIdDireccion());
        pedidoExistente.setIdSucursal(unPedido.getIdSucursal());
        pedidoExistente.setTotal(unPedido.getTotal());
        pedidoExistente.setIva(unPedido.getIva());
        pedidoExistente.setTotalConIva(unPedido.getTotalConIva());

        return pedidoRepository.save(pedidoExistente);
    }

    public Pedido guardarPedido(Pedido pedido) {
        UsuarioDto usuario = externalServiceClient.obtenerUsuario(pedido.getIdUsuario());
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
        DireccionDto direccion = externalServiceClient.obtenerDireccion(pedido.getIdDireccion());
            if (direccion == null) {
                throw new RuntimeException("Dirección no encontrada");
            }
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido("PENDIENTE");

        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

}