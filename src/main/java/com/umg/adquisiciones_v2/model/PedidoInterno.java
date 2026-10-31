package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PedidoInterno")
public class PedidoInterno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Pedido")
    private Integer idPedido;

    @ManyToOne
    @JoinColumn(name = "Codigo_Articulo", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "Codigo_Departamento", nullable = false)
    private Departamento departamento;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "Fecha_Solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "Fecha_Necesaria", nullable = false)
    private LocalDate fechaNecesaria;

    @Column(name = "Estado", length = 30)
    private String estado = "Pendiente";

    @ManyToOne
    @JoinColumn(name = "ID_Orden")
    private OrdenCompra ordenCompra;

    public PedidoInterno() {}

    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public LocalDate getFechaNecesaria() { return fechaNecesaria; }
    public void setFechaNecesaria(LocalDate fechaNecesaria) { this.fechaNecesaria = fechaNecesaria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public OrdenCompra getOrdenCompra() { return ordenCompra; }
    public void setOrdenCompra(OrdenCompra ordenCompra) { this.ordenCompra = ordenCompra; }
}