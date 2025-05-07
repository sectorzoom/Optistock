package org.optistock.optistock.controller;

import org.optistock.optistock.dto.LoginRequest;
import org.optistock.optistock.dto.RegistroRequest;
import org.optistock.optistock.entitiy.Usuario;
import org.optistock.optistock.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias manual (sin @RequiredArgsConstructor)
    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        // Crear usuario con constructor
        Usuario usuario = new Usuario(
                null, // ID se autogenera
                request.getNombre(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                "USER"
        );

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login correcto");
        response.put("nombre", usuario.getNombre());
        response.put("email", usuario.getEmail());
        response.put("rol", usuario.getRol());

        return ResponseEntity.ok(response);
    }
}