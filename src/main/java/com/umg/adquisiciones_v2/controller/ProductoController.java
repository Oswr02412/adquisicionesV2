package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Producto;
import com.umg.adquisiciones_v2.repository.ProductoRepository;
import com.umg.adquisiciones_v2.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listarProductos(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("productos", productoRepository.findAll());
            model.addAttribute("proveedores", proveedorRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "productos";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@RequestParam("dbType") String dbType, @ModelAttribute Producto producto) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            productoRepository.save(producto);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/productos?db=" + dbType;
    }

    @GetMapping("/editar/{codigo}")
    public String editarProducto(@PathVariable("codigo") String codigo, @RequestParam("db") String dbType, Model model) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            model.addAttribute("productoEdit", productoRepository.findById(codigo).orElse(null));
            model.addAttribute("productos", productoRepository.findAll());
            model.addAttribute("proveedores", proveedorRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "productos";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminarProducto(@PathVariable("codigo") String codigo, @RequestParam("db") String dbType) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            productoRepository.deleteById(codigo);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/productos?db=" + dbType;
    }
}