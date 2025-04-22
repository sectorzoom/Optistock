package org.optistock.optistock.controller;

import org.optistock.optistock.entitiy.Movimiento;
import org.optistock.optistock.service.MovimientoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> listarTodos() {
        return movimientoService.listarTodos();
    }

    @GetMapping("/producto/{productoId}")
    public List<Movimiento> listarPorProducto(@PathVariable Long productoId) {
        return movimientoService.listarPorProducto(productoId);
    }

    @PostMapping
    public Movimiento registrar(@RequestBody Movimiento movimiento) {
        return movimientoService.registrar(movimiento);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
    }

    @GetMapping("/producto/{productoId}/salidas")
    public List<Movimiento> listarSalidasPorProducto(@PathVariable Long productoId) {
        return movimientoService.listarPorProducto(productoId).stream()
                .filter(m -> m.getTipo().name().equals("SALIDA"))
                .toList();
    }

}

