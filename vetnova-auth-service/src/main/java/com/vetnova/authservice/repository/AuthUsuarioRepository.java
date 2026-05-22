package com.vetnova.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetnova.authservice.model.AuthUsuario;

public interface AuthUsuarioRepository extends JpaRepository<AuthUsuario, Long> {

    Optional<AuthUsuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

}