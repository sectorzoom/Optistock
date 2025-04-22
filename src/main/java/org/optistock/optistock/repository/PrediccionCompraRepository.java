package org.optistock.optistock.repository;

import org.optistock.optistock.entitiy.PrediccionCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrediccionCompraRepository extends JpaRepository<PrediccionCompra, Long> {
    Optional<PrediccionCompra> findByProductoId(Long productoId);
}
