package com.umg.adquisiciones_v2.repository;

import com.umg.adquisiciones_v2.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, String> {
}
