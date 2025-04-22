package org.optistock.optistock.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "predicciones_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}

