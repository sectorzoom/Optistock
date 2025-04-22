package org.optistock.optistock.service;

import org.optistock.optistock.entitiy.PrediccionCompra;
import java.util.List;

public interface PrediccionCompraService {
    PrediccionCompra guardar(PrediccionCompra prediccion);
    List<PrediccionCompra> listarTodas();
    PrediccionCompra obtenerPorProductoId(Long productoId);
}
