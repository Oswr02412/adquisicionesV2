package com.umg.adquisiciones_v2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Adjudicacion")
public class Adjudicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Adjudicacion")
    private Integer idAdjudicacion;

    @ManyToOne
    @JoinColumn(name = "ID_Oferta", nullable = false)
    private Oferta oferta;

    @Column(name = "Fecha_Adjudicacion", nullable = false)
    private LocalDate fechaAdjudicacion;

    @Column(name = "Justificacion", length = 255, nullable = false)
    private String justificacion;

    public Adjudicacion() {}

    public Integer getIdAdjudicacion() { return idAdjudicacion; }
    public void setIdAdjudicacion(Integer idAdjudicacion) { this.idAdjudicacion = idAdjudicacion; }

    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }

    public LocalDate getFechaAdjudicacion() { return fechaAdjudicacion; }
    public void setFechaAdjudicacion(LocalDate fechaAdjudicacion) { this.fechaAdjudicacion = fechaAdjudicacion; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
}