package org.optistock.optistock.controller;

import org.optistock.optistock.entitiy.Proveedor;
import org.optistock.optistock.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<Proveedor> listar() {
        return proveedorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Proveedor obtener(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id);
    }

    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorService.crear(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizar(id, proveedor);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
    }
}
