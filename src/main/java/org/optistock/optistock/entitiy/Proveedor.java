package org.optistock.optistock.entitiy;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    public Proveedor() {
    }

    public Proveedor(Long id, String nombre, String email, String telefono, Integer tiempoEntregaDias) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tiempoEntregaDias = tiempoEntregaDias;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    private Integer tiempoEntregaDias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getTiempoEntregaDias() {
        return tiempoEntregaDias;
    }

    public void setTiempoEntregaDias(Integer tiempoEntregaDias) {
        this.tiempoEntregaDias = tiempoEntregaDias;
    }
}
