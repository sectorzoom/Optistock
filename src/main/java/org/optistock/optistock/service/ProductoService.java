package org.optistock.optistock.service;

import org.optistock.optistock.entitiy.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> listarTodos();
    Producto obtenerPorId(Long id);
    Producto crearProducto(Producto producto);
    Producto actualizarProducto(Long id, Producto producto);
    void eliminarProducto(Long id);
    List<Producto> listarProductosConStockBajo();

}
