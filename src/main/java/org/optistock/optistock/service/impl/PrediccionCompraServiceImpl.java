package org.optistock.optistock.service.impl;

import lombok.RequiredArgsConstructor;
import org.optistock.optistock.entitiy.PrediccionCompra;
import org.optistock.optistock.repository.PrediccionCompraRepository;
import org.optistock.optistock.service.PrediccionCompraService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrediccionCompraServiceImpl implements PrediccionCompraService {

    private final PrediccionCompraRepository repository;

    @Override
    public PrediccionCompra guardar(PrediccionCompra prediccion) {
        return repository.save(prediccion);
    }

    @Override
    public List<PrediccionCompra> listarTodas() {
        return repository.findAll();
    }

    @Override
    public PrediccionCompra obtenerPorProductoId(Long productoId) {
        return repository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Predicción no encontrada"));
    }
}

