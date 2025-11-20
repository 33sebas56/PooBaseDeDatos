package com.example.conexion_base_de_datos.application.service;

import com.example.conexion_base_de_datos.application.dto.LoginRequest;
import com.example.conexion_base_de_datos.infrastucture.model.Role;
import com.example.conexion_base_de_datos.infrastucture.model.Usuario;
import com.example.conexion_base_de_datos.infrastucture.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, Object> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", usuario.getUsername());
        response.put("role", usuario.getRole().name());
        response.put("email", usuario.getEmail());
        response.put("nombre", usuario.getNombre());

        return response;
    }

    public Map<String, Object> register(String username, String password, String email,
                                        String nombre, String apellido, String roleStr) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El username ya existe");
        }

        // Convertir string a Role enum
        Role role;
        try {
            role = Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            role = Role.USER; // Por defecto USER si el rol no existe
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setRole(role);
        usuario.setActivo(true);

        Usuario savedUsuario = usuarioRepository.save(usuario);
        String token = jwtService.generateToken(savedUsuario);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", savedUsuario.getUsername());
        response.put("email", savedUsuario.getEmail());
        response.put("role", savedUsuario.getRole().name());
        response.put("message", "Usuario registrado exitosamente");

        return response;
    }
}