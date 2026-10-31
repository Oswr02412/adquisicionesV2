package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @Column(name = "Codigo", length = 20, nullable = false)
    private String codigo;

    @Column(name = "Nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "Precio", nullable = false)
    private Double precio;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "NIT_Proveedor", nullable = false)
    private Proveedor proveedor;

    public Producto() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
}
