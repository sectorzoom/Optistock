package org.optistock.optistock.service.impl;

import lombok.RequiredArgsConstructor;
import org.optistock.optistock.dto.LoginRequest;
import org.optistock.optistock.dto.RegistroRequest;
import org.optistock.optistock.entitiy.Usuario;
import org.optistock.optistock.repository.UsuarioRepository;
import org.optistock.optistock.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setEmail(request.getEmail());
        nuevo.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRepository.save(nuevo);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElse(null);
        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        return ResponseEntity.ok("Login correcto"); // Aquí podrías devolver un token si lo deseas
    }
}
