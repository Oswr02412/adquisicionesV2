package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Proveedor;
import com.umg.adquisiciones_v2.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listarProveedores(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("proveedores", proveedorRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "proveedores";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@RequestParam("dbType") String dbType, @ModelAttribute Proveedor proveedor) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            proveedorRepository.save(proveedor);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/proveedores?db=" + dbType;
    }

    @GetMapping("/editar/{nit}")
    public String editarProveedor(@PathVariable("nit") String nit, @RequestParam("db") String dbType, Model model) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            model.addAttribute("proveedorEdit", proveedorRepository.findById(nit).orElse(null));
            model.addAttribute("proveedores", proveedorRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "proveedores";
    }

    @GetMapping("/eliminar/{nit}")
    public String eliminarProveedor(@PathVariable("nit") String nit, @RequestParam("db") String dbType) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            proveedorRepository.deleteById(nit);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/proveedores?db=" + dbType;
    }
}