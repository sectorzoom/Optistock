package org.optistock.optistock.service.impl;

import lombok.RequiredArgsConstructor;
import org.optistock.optistock.entitiy.Producto;
import org.optistock.optistock.repository.ProductoRepository;
import org.optistock.optistock.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto nuevoProducto) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(nuevoProducto.getNombre());
        producto.setStockActual(nuevoProducto.getStockActual());
        producto.setStockMinimo(nuevoProducto.getStockMinimo());
        producto.setFechaUltimaCompra(nuevoProducto.getFechaUltimaCompra());
        producto.setProveedor(nuevoProducto.getProveedor());
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> listarProductosConStockBajo() {
        return productoRepository.findAll()
                .stream()
                .filter(p -> p.getStockActual() < p.getStockMinimo())
                .toList();
    }

}

