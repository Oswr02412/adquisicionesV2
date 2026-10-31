package com.umg.adquisiciones_v2.repository;

import com.umg.adquisiciones_v2.model.PedidoInterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoInternoRepository extends JpaRepository<PedidoInterno, Integer> {
}