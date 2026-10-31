package com.umg.adquisiciones_v2.repository;

import com.umg.adquisiciones_v2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmailAndPassword(String email, String password);
}
