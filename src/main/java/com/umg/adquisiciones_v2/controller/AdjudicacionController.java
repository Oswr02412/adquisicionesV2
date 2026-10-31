package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Adjudicacion;
import com.umg.adquisiciones_v2.model.Oferta;
import com.umg.adquisiciones_v2.model.OrdenCompra;
import com.umg.adquisiciones_v2.repository.AdjudicacionRepository;
import com.umg.adquisiciones_v2.repository.OfertaRepository;
import com.umg.adquisiciones_v2.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adjudicaciones")
public class AdjudicacionController {

    @Autowired
    private AdjudicacionRepository adjudicacionRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @GetMapping
    public String listarAdjudicaciones(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("adjudicaciones", adjudicacionRepository.findAll());
            List<Oferta> ofertasPendientes = ofertaRepository.findAll().stream()
                    .filter(o -> o.getEstado().equals("En Evaluación"))
                    .collect(Collectors.toList());
            model.addAttribute("ofertas", ofertasPendientes);
            model.addAttribute("fechaHoy", LocalDate.now());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "adjudicaciones";
    }

    @PostMapping("/guardar")
    public String adjudicarOferta(@RequestParam("dbType") String dbType, 
                                  @RequestParam("oferta.idOferta") Integer idOferta,
                                  @RequestParam("justificacion") String justificacion) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            Oferta ofertaGanadora = ofertaRepository.findById(idOferta).orElse(null);
            if (ofertaGanadora != null) {
                ofertaGanadora.setEstado("Aceptada");
                ofertaRepository.save(ofertaGanadora);

                OrdenCompra orden = ofertaGanadora.getOrdenCompra();
                orden.setEstado("Adjudicada");
                ordenCompraRepository.save(orden);

                List<Oferta> otrasOfertas = ofertaRepository.findAll().stream()
                        .filter(o -> o.getOrdenCompra().getIdOrden().equals(orden.getIdOrden()) 
                                  && !o.getIdOferta().equals(ofertaGanadora.getIdOferta()))
                        .collect(Collectors.toList());
                        
                for (Oferta rechazada : otrasOfertas) {
                    rechazada.setEstado("Rechazada");
                    ofertaRepository.save(rechazada);
                }

                Adjudicacion adjudicacion = new Adjudicacion();
                adjudicacion.setOferta(ofertaGanadora);
                adjudicacion.setFechaAdjudicacion(LocalDate.now());
                adjudicacion.setJustificacion(justificacion);
                adjudicacionRepository.save(adjudicacion);
            }
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/adjudicaciones?db=" + dbType;
    }
}