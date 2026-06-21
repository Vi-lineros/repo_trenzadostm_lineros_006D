package com.trenzadostm.trenzadostmpedido.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmpedido.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmpedido.dto.TipoProductoDto;
import com.trenzadostm.trenzadostmpedido.model.DetallePedido;
import com.trenzadostm.trenzadostmpedido.model.Pedido;
import com.trenzadostm.trenzadostmpedido.repository.DetallePedidoRepository;
import com.trenzadostm.trenzadostmpedido.repository.PedidoRepository;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

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

        BigDecimal precioUnitario = tipoProducto.getPrecioSinIva();

        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(unDetallePedido.getCantidad()));

        detallePedidoExistente.setPedido(unDetallePedido.getPedido());
        detallePedidoExistente.setIdTipoProducto(unDetallePedido.getIdTipoProducto());
        detallePedidoExistente.setCantidad(unDetallePedido.getCantidad());

        detallePedidoExistente.setPrecioUnitario(precioUnitario);
        detallePedidoExistente.setSubtotal(subtotal);

        DetallePedido detalleActualizado = detallePedidoRepository.save(detallePedidoExistente);

        actualizarTotalesPedido(detalleActualizado.getPedido().getIdPedido());

        return detalleActualizado;
    }

    public DetallePedido guardarDetallePedido(DetallePedido unDetallePedido) {

        TipoProductoDto tipoProducto = externalServiceClient.obtenerTipoProducto(unDetallePedido.getIdTipoProducto());

        if (tipoProducto == null) {
            throw new RuntimeException("Tipo producto no encontrado");
        }

        BigDecimal precioUnitario = tipoProducto.getPrecioSinIva();

        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(unDetallePedido.getCantidad()));

        unDetallePedido.setPrecioUnitario(precioUnitario);
        unDetallePedido.setSubtotal(subtotal);

        DetallePedido detalleGuardado = detallePedidoRepository.save(unDetallePedido);

        actualizarTotalesPedido(detalleGuardado.getPedido().getIdPedido());

        return detalleGuardado;
    }

    public void eliminarDetallePedido(Long idDetallePedido) {

        DetallePedido detalle = detallePedidoRepository.findById(idDetallePedido).get();

        Long idPedido = detalle.getPedido().getIdPedido();

        detallePedidoRepository.deleteById(idDetallePedido);

        actualizarTotalesPedido(idPedido);
    }

    private void actualizarTotalesPedido(Long idPedido){

        Pedido pedido = pedidoRepository.findById(idPedido).get();

        List<DetallePedido> detalles = detallePedidoRepository.findByPedido_IdPedido(idPedido);

        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido detalle : detalles) {
            total = total.add(detalle.getSubtotal());
        }
        
        BigDecimal iva = total.multiply(BigDecimal.valueOf(0.19));

        BigDecimal totalConIva = total.add(iva);

        pedido.setTotal(total);
        pedido.setIva(iva);
        pedido.setTotalConIva(totalConIva);

        pedidoRepository.save(pedido);
    }

}