package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Departamento")
public class Departamento {

    @Id
    @Column(name = "Codigo", length = 20, nullable = false)
    private String codigo;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", length = 255)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "Codigo_Sucursal", nullable = false)
    private Sucursal sucursal;

    public Departamento() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
}
