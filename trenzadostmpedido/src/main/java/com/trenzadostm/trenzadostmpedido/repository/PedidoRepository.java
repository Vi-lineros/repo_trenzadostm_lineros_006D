package com.trenzadostm.trenzadostmpedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trenzadostm.trenzadostmpedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
