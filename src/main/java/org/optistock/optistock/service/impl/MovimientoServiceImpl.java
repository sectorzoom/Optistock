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

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            // Ejecutar el script usando directamente el Python del entorno virtual
            ProcessBuilder pb = new ProcessBuilder(
                    "/home/ubuntu/optistock-ia-env/bin/python",
                    "/home/ubuntu/optistock-ia-env/prediccion_stock.py",
                    String.valueOf(productoId)
            );

            // Establecer variable de entorno ENTORNO=produccion
            pb.environment().put("ENTORNO", "produccion");

            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                    reader.lines().forEach(System.out::println);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println("✅ Script IA ejecutado correctamente para producto " + productoId);
        } catch (Exception e) {
            System.err.println("❌ Error ejecutando script IA: " + e.getMessage());
        }
    }






}

