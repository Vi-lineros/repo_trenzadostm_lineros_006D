package com.trenzadostm.trenzadostmpago.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenzadostm.trenzadostmpago.model.Banco;
import com.trenzadostm.trenzadostmpago.repository.BancoRepository;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    public List<Banco> buscarTodos(){
        return bancoRepository.findAll();
    }

    public Banco buscarPorId(Long id){
        return bancoRepository.findById(id).get();
    }

    public Banco modificarBanco(Long idBanco, Banco unBanco){
        Banco bancoExistente = bancoRepository.findById(idBanco).get();

        bancoExistente.setNombreBanco(unBanco.getNombreBanco());
        bancoExistente.setTipoCuenta(unBanco.getTipoCuenta());

        return bancoRepository.save(bancoExistente); 
    }

    public Banco guardarBanco(Banco unBanco){
        return bancoRepository.save(unBanco);
    }

    public void eliminarBanco(Long id){
        bancoRepository.deleteById(id);
    }
}
