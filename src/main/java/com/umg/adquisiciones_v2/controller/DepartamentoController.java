package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Departamento;
import com.umg.adquisiciones_v2.repository.DepartamentoRepository;
import com.umg.adquisiciones_v2.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @GetMapping
    public String listarDepartamentos(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("sucursales", sucursalRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "departamentos";
    }

    @PostMapping("/guardar")
    public String guardarDepartamento(@RequestParam("dbType") String dbType, @ModelAttribute Departamento departamento) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            departamentoRepository.save(departamento);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/departamentos?db=" + dbType;
    }

    @GetMapping("/editar/{codigo}")
    public String editarDepartamento(@PathVariable("codigo") String codigo, @RequestParam("db") String dbType, Model model) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            model.addAttribute("departamentoEdit", departamentoRepository.findById(codigo).orElse(null));
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("sucursales", sucursalRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "departamentos";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminarDepartamento(@PathVariable("codigo") String codigo, @RequestParam("db") String dbType) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            departamentoRepository.deleteById(codigo);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/departamentos?db=" + dbType;
    }
}