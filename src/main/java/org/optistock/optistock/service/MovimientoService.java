package org.optistock.optistock.service;

import org.optistock.optistock.entitiy.Movimiento;

import java.util.List;

public interface MovimientoService {
    List<Movimiento> listarTodos();
    List<Movimiento> listarPorProducto(Long productoId);
    Movimiento registrar(Movimiento movimiento);
    void eliminar(Long id);
}
