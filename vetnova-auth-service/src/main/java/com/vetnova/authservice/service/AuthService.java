package com.vetnova.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetnova.authservice.model.AuthUsuario;
import com.vetnova.authservice.repository.AuthUsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private AuthUsuarioRepository authUsuarioRepository;

    public List<AuthUsuario> obtenerUsuariosAuth() {
        return authUsuarioRepository.findAll();
    }

    public AuthUsuario registrar(AuthUsuario usuario) {
        return authUsuarioRepository.save(usuario);
    }

    public String login(String correo, String password) {
        Optional<AuthUsuario> usuarioEncontrado = authUsuarioRepository.findByCorreo(correo);

        if (usuarioEncontrado.isPresent()) {
            AuthUsuario usuario = usuarioEncontrado.get();

            if (usuario.getPassword().equals(password)) {
                return "Login correcto - Rol: " + usuario.getRol();
            }
        }

        return "Credenciales incorrectas";
    }
}