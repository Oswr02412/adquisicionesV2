package com.umg.adquisiciones_v2.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @Column(name = "ID_Usuario")
    private Integer idUsuario;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "Password", length = 100, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "ID_Rol", nullable = false)
    private Rol rol;

    public Usuario() {}
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
