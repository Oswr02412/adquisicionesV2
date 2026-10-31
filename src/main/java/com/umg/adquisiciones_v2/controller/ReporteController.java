package com.umg.adquisiciones_v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReporteController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reportes")
    public String reporteGeneral(@RequestParam(value = "db", required = false) String dbType, Model model) {
        
        try {
            String sqlHistorial = "SELECT ID_Adjudicacion AS No_Adjudicacion, Fecha_Adjudicacion AS Fecha, Justificacion " +
                                  "FROM Adjudicacion"; 
            model.addAttribute("historial", jdbcTemplate.queryForList(sqlHistorial));

            String sqlRanking = "SELECT TOP 5 prov.Nombre_Comercial AS Proveedor, COUNT(ped.ID_Pedido) AS Cantidad_Pedidos, SUM(ped.Cantidad * prod.precio) AS Total_Vendido " +
                                "FROM Proveedor prov JOIN Producto prod ON prov.NIT_Proveedor = prod.NIT_Proveedor " +
                                "JOIN PedidoInterno ped ON prod.codigo = ped.Codigo_Articulo " +
                                "GROUP BY prov.Nombre_Comercial ORDER BY Total_Vendido DESC";
            model.addAttribute("ranking", jdbcTemplate.queryForList(sqlRanking));

            String sqlGasto = "SELECT d.Nombre AS Departamento, SUM(ped.Cantidad * prod.precio) AS Gasto_Total " +
                              "FROM PedidoInterno ped JOIN Departamento d ON ped.Codigo_Departamento = d.codigo " +
                              "JOIN Producto prod ON ped.Codigo_Articulo = prod.codigo " +
                              "GROUP BY d.Nombre";
            model.addAttribute("gasto", jdbcTemplate.queryForList(sqlGasto));

            String sqlPendientes = "SELECT ID_Pedido, Codigo_Articulo, Cantidad, Fecha_Solicitud, Estado " +
                                   "FROM PedidoInterno";
            model.addAttribute("pendientes", jdbcTemplate.queryForList(sqlPendientes));

        } catch (Exception e) {
            model.addAttribute("error", "Error consultando la base de datos: " + e.getMessage());
        }
        
        model.addAttribute("dbUsada", dbType);
        return "reporte-general";
    }
}