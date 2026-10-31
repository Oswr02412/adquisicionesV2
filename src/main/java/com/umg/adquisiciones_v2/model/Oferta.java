package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Oferta")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Oferta")
    private Integer idOferta;

    @ManyToOne
    @JoinColumn(name = "ID_Orden", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "NIT_Proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "Fecha_Oferta", nullable = false)
    private LocalDate fechaOferta;

    @Column(name = "Monto_Total", nullable = false, precision = 18, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "Tiempo_Entrega_Dias", nullable = false)
    private Integer tiempoEntregaDias;

    @Column(name = "Estado", length = 30)
    private String estado = "En Evaluación";

    public Oferta() {}

    public Integer getIdOferta() { return idOferta; }
    public void setIdOferta(Integer idOferta) { this.idOferta = idOferta; }

    public OrdenCompra getOrdenCompra() { return ordenCompra; }
    public void setOrdenCompra(OrdenCompra ordenCompra) { this.ordenCompra = ordenCompra; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public LocalDate getFechaOferta() { return fechaOferta; }
    public void setFechaOferta(LocalDate fechaOferta) { this.fechaOferta = fechaOferta; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public Integer getTiempoEntregaDias() { return tiempoEntregaDias; }
    public void setTiempoEntregaDias(Integer tiempoEntregaDias) { this.tiempoEntregaDias = tiempoEntregaDias; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}