package org.optistock.optistock.service;

import org.optistock.optistock.entitiy.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> listarTodos();
    Proveedor obtenerPorId(Long id);
    Proveedor crear(Proveedor proveedor);
    Proveedor actualizar(Long id, Proveedor proveedor);
    void eliminar(Long id);
}
