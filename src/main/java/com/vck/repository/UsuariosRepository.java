package com.vck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vck.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
