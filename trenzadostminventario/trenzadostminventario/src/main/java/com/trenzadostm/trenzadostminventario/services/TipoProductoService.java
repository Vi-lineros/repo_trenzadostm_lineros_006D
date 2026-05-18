package com.trenzadostm.trenzadostminventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostminventario.model.TipoProducto;
import com.trenzadostm.trenzadostminventario.repository.TipoProductoRepository;

@Service
public class TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    public List<TipoProducto> buscarTodos(){
        return tipoProductoRepository.findAll();
    }

    public TipoProducto buscarTipoProductoPorId(Long idTipoProducto){
        return tipoProductoRepository.findById(idTipoProducto).get();
    }

    public TipoProducto modificarTipoProducto(Long idTipoProducto, TipoProducto unTipoProducto){
        TipoProducto tipoProductoExistente = tipoProductoRepository.findById(idTipoProducto).get();

        tipoProductoExistente.setProducto(unTipoProducto.getProducto());
        tipoProductoExistente.setMedida(unTipoProducto.getMedida());
        tipoProductoExistente.setColor(unTipoProducto.getColor());
        tipoProductoExistente.setPrecioSinIva(unTipoProducto.getPrecioSinIva());

        return tipoProductoRepository.save(tipoProductoExistente);
    }

    public TipoProducto guardarTipoProducto(TipoProducto unTipoProducto){
        return tipoProductoRepository.save(unTipoProducto);
    }

    public void eliminarTipoProducto(Long idTipoProducto){
        tipoProductoRepository.deleteById(idTipoProducto);
    }
}
