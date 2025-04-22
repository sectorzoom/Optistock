package org.optistock.optistock.repository;

import org.optistock.optistock.entitiy.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByProductoId(Long productoId);
}

