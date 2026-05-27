package com.vetnova.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vetnova.authservice.dto.LoginResponse;
import com.vetnova.authservice.dto.RegistroRequest;
import com.vetnova.authservice.dto.UsuarioPerfilRequest;
import com.vetnova.authservice.model.AuthUsuario;
import com.vetnova.authservice.repository.AuthUsuarioRepository;
import com.vetnova.authservice.security.JwtService;

@Service
public class AuthService {

    @Autowired
    private AuthUsuarioRepository authUsuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    public List<AuthUsuario> obtenerUsuariosAuth() {
        return authUsuarioRepository.findAll();
    }

    public AuthUsuario registrarDesdeRequest(RegistroRequest request) {

        Optional<AuthUsuario> usuarioExistente = authUsuarioRepository.findByCorreo(request.getCorreo());

        if (usuarioExistente.isPresent()) {
            return null;
        }

        AuthUsuario authUsuario = new AuthUsuario();
        authUsuario.setCorreo(request.getCorreo());
        authUsuario.setPassword(request.getPassword());
        authUsuario.setRol(request.getRol());

        AuthUsuario usuarioGuardado = authUsuarioRepository.save(authUsuario);

        crearPerfilUsuario(request);

        return usuarioGuardado;
    }

    public LoginResponse login(String correo, String password) {

        Optional<AuthUsuario> usuarioEncontrado = authUsuarioRepository.findByCorreo(correo);

        if (usuarioEncontrado.isPresent()) {
            AuthUsuario usuarioBD = usuarioEncontrado.get();

            if (usuarioBD.getPassword().equals(password)) {
                String token = jwtService.generarToken(usuarioBD.getCorreo(), usuarioBD.getRol());
                return new LoginResponse(token, "Bearer");
            }
        }

        return null;
    }

    private void crearPerfilUsuario(RegistroRequest request) {
        try {
            String url = "http://localhost:8081/api/usuarios/perfil-basico";

            UsuarioPerfilRequest perfil = new UsuarioPerfilRequest(
                    request.getNombre(),
                    request.getApellido(),
                    request.getRut(),
                    request.getCorreo(),
                    request.getPassword(),
                    request.getTelefono(),
                    request.getDireccion(),
                    request.getRol(),
                    "ACTIVO"
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<UsuarioPerfilRequest> entity = new HttpEntity<>(perfil, headers);

            restTemplate.postForObject(url, entity, String.class);

            System.out.println("Perfil completo creado correctamente en Usuario_Service");

        } catch (Exception e) {
            System.out.println("No se pudo crear el perfil en Usuario_Service: " + e.getMessage());
        }
    }
}