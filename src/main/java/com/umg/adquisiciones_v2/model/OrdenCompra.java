package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "OrdenCompra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Orden")
    private Integer idOrden;

    @Column(name = "Fecha_Emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "Prioridad", length = 20, nullable = false)
    private String prioridad;

    @Column(name = "Tamano", length = 20, nullable = false)
    private String tamano;

    @Column(name = "Estado", length = 30)
    private String estado = "Activa";

    @OneToMany(mappedBy = "ordenCompra", fetch = FetchType.EAGER)
    private List<PedidoInterno> pedidos;

    public OrdenCompra() {}

    public Integer getIdOrden() { return idOrden; }
    public void setIdOrden(Integer idOrden) { this.idOrden = idOrden; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<PedidoInterno> getPedidos() { return pedidos; }
    public void setPedidos(List<PedidoInterno> pedidos) { this.pedidos = pedidos; }
}