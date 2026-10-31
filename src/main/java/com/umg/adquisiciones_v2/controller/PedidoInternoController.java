package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.PedidoInterno;
import com.umg.adquisiciones_v2.repository.DepartamentoRepository;
import com.umg.adquisiciones_v2.repository.PedidoInternoRepository;
import com.umg.adquisiciones_v2.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/pedidos")
public class PedidoInternoController {

    @Autowired
    private PedidoInternoRepository pedidoInternoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping
    public String listarPedidos(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("pedidos", pedidoInternoRepository.findAll());
            model.addAttribute("productos", productoRepository.findAll());
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("fechaHoy", LocalDate.now());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "pedidos";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@RequestParam("dbType") String dbType, @ModelAttribute PedidoInterno pedido) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            if (pedido.getFechaSolicitud() == null) {
                pedido.setFechaSolicitud(LocalDate.now());
            }
            if (pedido.getEstado() == null || pedido.getEstado().isEmpty()) {
                pedido.setEstado("Pendiente");
            }
            pedidoInternoRepository.save(pedido);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/pedidos?db=" + dbType;
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable("id") Integer id, @RequestParam("db") String dbType, Model model) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            model.addAttribute("pedidoEdit", pedidoInternoRepository.findById(id).orElse(null));
            model.addAttribute("pedidos", pedidoInternoRepository.findAll());
            model.addAttribute("productos", productoRepository.findAll());
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable("id") Integer id, @RequestParam("db") String dbType) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            pedidoInternoRepository.deleteById(id);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/pedidos?db=" + dbType;
    }
}