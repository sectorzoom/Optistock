package org.optistock.optistock.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    private Integer tiempoEntregaDias;
}
