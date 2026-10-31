package com.umg.adquisiciones_v2.repository;

import com.umg.adquisiciones_v2.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
}
