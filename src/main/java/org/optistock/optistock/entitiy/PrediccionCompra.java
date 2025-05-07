package org.optistock.optistock.entitiy;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "predicciones_compra")
public class PrediccionCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private LocalDate fechaRecomendada;

    private Double consumoDiarioEstimado;

    private LocalDate fechaGeneracion;

    public PrediccionCompra() {
    }

    public PrediccionCompra(Long id, Producto producto, LocalDate fechaRecomendada, Double consumoDiarioEstimado, LocalDate fechaGeneracion) {
        this.id = id;
        this.producto = producto;
        this.fechaRecomendada = fechaRecomendada;
        this.consumoDiarioEstimado = consumoDiarioEstimado;
        this.fechaGeneracion = fechaGeneracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDate getFechaRecomendada() {
        return fechaRecomendada;
    }

    public void setFechaRecomendada(LocalDate fechaRecomendada) {
        this.fechaRecomendada = fechaRecomendada;
    }

    public Double getConsumoDiarioEstimado() {
        return consumoDiarioEstimado;
    }

    public void setConsumoDiarioEstimado(Double consumoDiarioEstimado) {
        this.consumoDiarioEstimado = consumoDiarioEstimado;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}

