package com.umg.adquisiciones_v2.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Rol")
public class Rol {
    @Id
    @Column(name = "ID_Rol")
    private Integer idRol;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;

    public Rol() {}
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}