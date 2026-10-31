package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Sucursal")
public class Sucursal {

    @Id
    @Column(name = "Codigo", length = 20, nullable = false)
    private String codigo;

    @Column(name = "Direccion", length = 255, nullable = false)
    private String direccion;

    @Column(name = "Ciudad", length = 100, nullable = false)
    private String ciudad;

    @Column(name = "Departamento_Geo", length = 100, nullable = false)
    private String departamentoGeo;

    @Column(name = "Telefonos", length = 100, nullable = false)
    private String telefonos;

    public Sucursal() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDepartamentoGeo() { return departamentoGeo; }
    public void setDepartamentoGeo(String departamentoGeo) { this.departamentoGeo = departamentoGeo; }
    public String getTelefonos() { return telefonos; }
    public void setTelefonos(String telefonos) { this.telefonos = telefonos; }
}
