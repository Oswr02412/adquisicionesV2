package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Oferta;
import com.umg.adquisiciones_v2.repository.OfertaRepository;
import com.umg.adquisiciones_v2.repository.OrdenCompraRepository;
import com.umg.adquisiciones_v2.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listarOfertas(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("ofertas", ofertaRepository.findAll());
            model.addAttribute("ordenesActivas", ordenCompraRepository.findAll());
            model.addAttribute("proveedores", proveedorRepository.findAll());
            model.addAttribute("fechaHoy", LocalDate.now());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "ofertas";
    }

    @PostMapping("/guardar")
    public String guardarOferta(@RequestParam("dbType") String dbType,
                                @RequestParam(value = "ordenCompra.idOrden", required = false) Integer idOrden,
                                @RequestParam(value = "proveedor.nitProveedor", required = false) String nitProveedor,
                                @RequestParam("montoTotal") String montoTotalStr,
                                @RequestParam("tiempoEntregaDias") Integer tiempoEntregaDias,
                                @RequestParam("fechaOferta") String fechaOfertaStr,
                                RedirectAttributes redirectAttributes) {
        
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            if (idOrden == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar una Orden de Compra.");
                return "redirect:/ofertas?db=" + dbType;
            }
            if (nitProveedor == null || nitProveedor.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar un Proveedor.");
                return "redirect:/ofertas?db=" + dbType;
            }

            if (!ordenCompraRepository.existsById(idOrden)) {
                redirectAttributes.addFlashAttribute("error", "La Orden #" + idOrden + " no existe.");
                return "redirect:/ofertas?db=" + dbType;
            }
            if (!proveedorRepository.existsById(nitProveedor)) {
                redirectAttributes.addFlashAttribute("error", "El Proveedor no existe.");
                return "redirect:/ofertas?db=" + dbType;
            }

            ofertaRepository.guardarOfertaNativa(
                    idOrden, 
                    nitProveedor, 
                    LocalDate.parse(fechaOfertaStr), 
                    new BigDecimal(montoTotalStr), 
                    tiempoEntregaDias, 
                    "En Evaluación"
            );
            
            redirectAttributes.addFlashAttribute("exito", "¡Oferta registrada exitosamente!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la oferta: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/ofertas?db=" + dbType;
    }
}