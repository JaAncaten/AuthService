package com.vetnova.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vetnova.authservice.model.AuthUsuario;
import com.vetnova.authservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/usuarios")
    public List<AuthUsuario> obtenerUsuariosAuth() {
        return authService.obtenerUsuariosAuth();
    }

    @PostMapping("/registro")
    public AuthUsuario registrar(@RequestBody AuthUsuario usuario) {
        return authService.registrar(usuario);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthUsuario usuario) {
        return authService.login(usuario.getCorreo(), usuario.getPassword());
    }
}