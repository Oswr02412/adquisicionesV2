package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Sucursal;
import com.umg.adquisiciones_v2.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;

    @GetMapping
    public String listarSucursales(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("sucursales", sucursalRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "sucursales";
    }

    @PostMapping("/guardar")
    public String guardarSucursal(@RequestParam("dbType") String dbType,
                                  @ModelAttribute Sucursal sucursal) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            sucursalRepository.save(sucursal); 
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/sucursales?db=" + dbType;
    }

    @GetMapping("/editar/{codigo}")
    public String editarSucursal(@PathVariable("codigo") String codigo, @RequestParam("db") String dbType, Model model) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            model.addAttribute("sucursalEdit", sucursalRepository.findById(codigo).orElse(null));
            model.addAttribute("sucursales", sucursalRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "sucursales";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminarSucursal(@PathVariable("codigo") String codigo, @RequestParam("db") String dbType) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            sucursalRepository.deleteById(codigo);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/sucursales?db=" + dbType;
    }
}