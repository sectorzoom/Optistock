package org.optistock.optistock.controller;

import org.optistock.optistock.entitiy.PrediccionCompra;
import org.optistock.optistock.service.PrediccionCompraService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recomendacion")
public class PrediccionCompraController {

    @GetMapping("/{productoId}")
    public ResponseEntity<?> obtenerRecomendacion(@PathVariable Long productoId) {
        try {
            ProcessBuilder builder = new ProcessBuilder("python", "prediccion_stock.py", String.valueOf(productoId));
            builder.redirectErrorStream(true);

            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return ResponseEntity.status(500).body("Error al ejecutar script:\n" + output);
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(output);

        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

