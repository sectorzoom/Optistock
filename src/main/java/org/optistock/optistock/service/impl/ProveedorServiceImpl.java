package org.optistock.optistock.service.impl;

import org.optistock.optistock.entitiy.Proveedor;
import org.optistock.optistock.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.optistock.optistock.service.ProveedorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    @Override
    public Proveedor crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor actualizar(Long id, Proveedor proveedorNuevo) {
        Proveedor proveedor = obtenerPorId(id);
        proveedor.setNombre(proveedorNuevo.getNombre());
        proveedor.setEmail(proveedorNuevo.getEmail());
        proveedor.setTelefono(proveedorNuevo.getTelefono());
        proveedor.setTiempoEntregaDias(proveedorNuevo.getTiempoEntregaDias());
        return proveedorRepository.save(proveedor);
    }

    @Override
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
