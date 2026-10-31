package com.umg.adquisiciones_v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class PedidoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/pedidos/nuevo")
    public String mostrarFormularioPedido(Model model) {
        return "nuevo-pedido";
    }

    // Ruta modificada para evitar el choque (Ambiguous mapping)
    @PostMapping("/pedidos/guardar-rapido")
    public String guardarPedido(@RequestParam("codigoArticulo") String articulo,
                                @RequestParam("codigoDepartamento") String departamento,
                                @RequestParam("cantidad") int cantidad,
                                @RequestParam("fechaNecesaria") String fechaNecesaria,
                                Model model) {
        try {
            String sql = "INSERT INTO PedidoInterno (Codigo_Articulo, Codigo_Departamento, Cantidad, Fecha_Solicitud, Fecha_Necesaria, Estado) " +
                         "VALUES (?, ?, ?, ?, ?, 'Pendiente')";

            jdbcTemplate.update(sql, articulo, departamento, cantidad, LocalDate.now().toString(), fechaNecesaria);
            
            model.addAttribute("exito", "¡Pedido guardado correctamente en la base de datos!");
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el pedido: " + e.getMessage());
        }
        
        return "nuevo-pedido";
    }
}