package org.optistock.optistock.service.impl;

import org.optistock.optistock.entitiy.Movimiento;
import org.optistock.optistock.entitiy.Producto;
import org.optistock.optistock.entitiy.TipoMovimiento;
import org.optistock.optistock.repository.MovimientoRepository;
import org.optistock.optistock.repository.ProductoRepository;
import org.optistock.optistock.service.MovimientoService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Movimiento> listarTodos() {
        return movimientoRepository.findAll();
    }

    @Override
    public List<Movimiento> listarPorProducto(Long productoId) {
        return movimientoRepository.findByProductoId(productoId);
    }

    @Override
    public Movimiento registrar(Movimiento movimiento) {
        Producto producto = productoRepository.findById(movimiento.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (movimiento.getTipo() == TipoMovimiento.ENTRADA) {
            producto.setStockActual(producto.getStockActual() + movimiento.getCantidad());
        } else if (movimiento.getTipo() == TipoMovimiento.SALIDA) {
            producto.setStockActual(producto.getStockActual() - movimiento.getCantidad());

            ejecutarScriptIA(producto.getId());//Ejecutar script de predicción

        }

        productoRepository.save(producto);
        return movimientoRepository.save(movimiento);
    }

    @Override
    public void eliminar(Long id) {
        movimientoRepository.deleteById(id);
    }


    private void ejecutarScriptIA(Long productoId) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python3", "/var/lib/tomcat10/prediccion_stock.py", String.valueOf(productoId));

            // 🔧 Establecer entorno como "produccion"
            pb.environment().put("ENTORNO", "produccion");

            pb.redirectErrorStream(true); // Combina stdout y stderr
            Process proceso = pb.start();

            new Thread(() -> {
                try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(proceso.getInputStream()))) {
                    reader.lines().forEach(System.out::println);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println("🧠 Script de predicción ejecutado para producto " + productoId);

        } catch (Exception e) {
            System.err.println("❌ Error ejecutando script de IA: " + e.getMessage());
        }
    }




}

