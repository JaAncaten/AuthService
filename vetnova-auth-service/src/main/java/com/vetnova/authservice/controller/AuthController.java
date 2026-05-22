package com.vetnova.authservice.controller;

import java.util.List;
import com.vetnova.authservice.dto.LoginResponse;

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
    public Object registrar(@RequestBody AuthUsuario usuario) {

    AuthUsuario usuarioRegistrado = authService.registrar(usuario);

    if (usuarioRegistrado == null) {
        return "El correo ya se encuentra registrado";
    }

    return usuarioRegistrado;
}

    @PostMapping("/login")
public LoginResponse login(@RequestBody AuthUsuario usuario) {
    return authService.login(usuario.getCorreo(), usuario.getPassword());
}
}