package com.trenzadostm.trenzadostmpedido.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmpedido.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmpedido.dto.TipoProductoDto;
import com.trenzadostm.trenzadostmpedido.model.DetallePedido;
import com.trenzadostm.trenzadostmpedido.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ExternalServiceClient externalServiceClient;

    public List<DetallePedido> buscarTodos(){
        return detallePedidoRepository.findAll();
    }

    public DetallePedido buscarDetallePedidoPorId(Long idDetallePedido){
        return detallePedidoRepository.findById(idDetallePedido).get();
    }

    public DetallePedido modificarDetallePedido(Long idDetallePedido, DetallePedido unDetallePedido){
        DetallePedido detallePedidoExistente = detallePedidoRepository.findById(idDetallePedido).get();
        TipoProductoDto tipoProducto = externalServiceClient.obtenerTipoProducto(unDetallePedido.getIdTipoProducto());
        if (tipoProducto == null) {
            throw new RuntimeException("Tipo producto no encontrado");
        }

        detallePedidoExistente.setPedido(unDetallePedido.getPedido());
        detallePedidoExistente.setIdTipoProducto(unDetallePedido.getIdTipoProducto());
        detallePedidoExistente.setCantidad(unDetallePedido.getCantidad());
        detallePedidoExistente.setPrecioUnitario(unDetallePedido.getPrecioUnitario());
        detallePedidoExistente.setSubtotal(unDetallePedido.getSubtotal());

        return detallePedidoRepository.save(detallePedidoExistente);
    }

    public DetallePedido guardarDetallePedido(DetallePedido unDetallePedido) {
        TipoProductoDto tipoProducto = externalServiceClient.obtenerTipoProducto(unDetallePedido.getIdTipoProducto());
        if (tipoProducto == null) {
            throw new RuntimeException("Tipo producto no encontrado");
        }
        return detallePedidoRepository.save(unDetallePedido);
    }

    public void eliminarDetallePedido(Long idDetallePedido) {
        detallePedidoRepository.deleteById(idDetallePedido);
    }

}