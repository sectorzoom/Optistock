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
            // Rutas absolutas
            String pythonPath = "/home/ubuntu/optistock-ia-env/bin/python3";
            String scriptPath = "/home/ubuntu/optistock-ia-env/prediccion_stock.py";

            ProcessBuilder builder = new ProcessBuilder(
                    pythonPath,
                    scriptPath,
                    String.valueOf(productoId)
            );
            // Para que el script use EC2:8080 en lugar de localhost
            builder.environment().put("ENTORNO", "produccion");

            // Opcional: para depurar, imprime la línea de comando que vas a ejecutar
            System.out.println("Ejecutando script: " + String.join(" ", builder.command()));

            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining("\n"));
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    return ResponseEntity.status(500).body("Error al ejecutar script:\n" + output);
                }

                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(output);
            }

        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }


}

