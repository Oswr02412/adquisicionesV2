package com.umg.adquisiciones_v2.repository;

import com.umg.adquisiciones_v2.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Oferta (ID_Orden, NIT_Proveedor, Fecha_Oferta, Monto_Total, Tiempo_Entrega_Dias, Estado) VALUES (:idOrden, :nit, :fecha, :monto, :tiempo, :estado)", nativeQuery = true)
    void guardarOfertaNativa(@Param("idOrden") Integer idOrden, 
                             @Param("nit") String nit, 
                             @Param("fecha") LocalDate fecha, 
                             @Param("monto") BigDecimal monto, 
                             @Param("tiempo") Integer tiempo, 
                             @Param("estado") String estado);
}