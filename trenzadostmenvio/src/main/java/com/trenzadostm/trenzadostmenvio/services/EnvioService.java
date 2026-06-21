package com.trenzadostm.trenzadostmenvio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmenvio.client.ExternalServiceClient;
import com.trenzadostm.trenzadostmenvio.dto.PedidoDto;
import com.trenzadostm.trenzadostmenvio.dto.RepartidorDto;
import com.trenzadostm.trenzadostmenvio.model.Envio;
import com.trenzadostm.trenzadostmenvio.repository.EnvioRepository;

@Service
public class EnvioService {

    private EnvioRepository envioRepository;
    private final ExternalServiceClient client;

    public EnvioService(EnvioRepository envioRepository, ExternalServiceClient client) {
        this.envioRepository = envioRepository;
        this.client = client;
    }

    public List<Envio> buscarTodos(){
        return envioRepository.findAll();
    }

    public Envio buscarPorId(Long id){
        return envioRepository.findById(id).get();
    }

    public Envio guardarEnvio(Envio unEnvio){
        PedidoDto pedido = client.obtenerPedido(unEnvio.getIdPedido());

        if(pedido == null){
            throw new RuntimeException("el pedido no existe");
        }

        if(unEnvio.getIdRepartidor() != null){
            RepartidorDto repartidor = client.obtenerRepartidor(unEnvio.getIdRepartidor()); 

            if(repartidor == null){
                throw new RuntimeException("el repartidor no existe");
            }
        }

        if(unEnvio.getEstadoEnvio() == null){
            unEnvio.setEstadoEnvio("PENDIENTE");
        }

        return envioRepository.save(unEnvio);
    }

    public Envio modificarEnvio(Long id, Envio unEnvio){
        Envio envioExistente = envioRepository.findById(id).get();

        PedidoDto pedido = client.obtenerPedido(unEnvio.getIdPedido());

        if(pedido == null){
            throw new RuntimeException("el pedido no existe");
        }

        if(unEnvio.getIdRepartidor() != null){

            RepartidorDto repartidor =
                    client.obtenerRepartidor(unEnvio.getIdRepartidor());

            if(repartidor == null){
                throw new RuntimeException("el repartidor no existe");
            }
        }

        envioExistente.setIdPedido(unEnvio.getIdPedido());
        envioExistente.setIdRepartidor(unEnvio.getIdRepartidor());
        envioExistente.setEmpresaEnvio(unEnvio.getEmpresaEnvio());
        envioExistente.setFechaEnvio(unEnvio.getFechaEnvio());
        envioExistente.setFechaEntrega(unEnvio.getFechaEntrega());
        envioExistente.setEstadoEnvio(unEnvio.getEstadoEnvio());

        return envioRepository.save(envioExistente);
    }

    public Envio buscarPorPedido(Long idPedido){
        List<Envio> envios = envioRepository.findAll();

        for(Envio envio : envios){
            if(envio.getIdPedido().equals(idPedido)){
                return envio;
            }
        }   
        return null;
    }

    public String eliminarEnvio(Long id){
        envioRepository.deleteById(id);
        return "Envio eliminado";
    }

}
