package com.umg.adquisiciones_v2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Proveedor")
public class Proveedor {

    @Id
    @Column(name = "NIT_Proveedor", length = 20, nullable = false)
    private String nitProveedor;

    @Column(name = "Nombre_Comercial", length = 150, nullable = false)
    private String nombreComercial;

    @Column(name = "Direccion", length = 255, nullable = false)
    private String direccion;

    @Column(name = "Telefono", length = 50, nullable = false)
    private String telefono;

    @Column(name = "Rubros", length = 255, nullable = false)
    private String rubros;

    public Proveedor() {}

    public Proveedor(String nitProveedor, String nombreComercial, String direccion, String telefono, String rubros) {
        this.nitProveedor = nitProveedor;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rubros = rubros;
    }

    public String getNitProveedor() { return nitProveedor; }
    public void setNitProveedor(String nitProveedor) { this.nitProveedor = nitProveedor; }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getRubros() { return rubros; }
    public void setRubros(String rubros) { this.rubros = rubros; }
}
