package org.optistock.optistock.service.impl;

import lombok.RequiredArgsConstructor;
import org.optistock.optistock.entitiy.PrediccionCompra;
import org.optistock.optistock.repository.PrediccionCompraRepository;
import org.optistock.optistock.service.PrediccionCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrediccionCompraServiceImpl implements PrediccionCompraService {

    private final PrediccionCompraRepository repository;

    @Autowired
    public PrediccionCompraServiceImpl(PrediccionCompraRepository repository) {
        this.repository = repository;
    }

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

