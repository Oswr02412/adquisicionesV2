package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.OrdenCompra;
import com.umg.adquisiciones_v2.model.PedidoInterno;
import com.umg.adquisiciones_v2.repository.OrdenCompraRepository;
import com.umg.adquisiciones_v2.repository.PedidoInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ordenes")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private PedidoInternoRepository pedidoInternoRepository;

    @GetMapping
    public String listarOrdenes(@RequestParam(value = "db", required = false) String dbType, Model model) {
        if (dbType != null && !dbType.isEmpty()) {
            DatabaseContextHolder.setDatabaseType(dbType);
        }
        try {
            model.addAttribute("ordenes", ordenCompraRepository.findAll());
            
            // Filtramos solo los pedidos que aún no tienen orden asignada
            List<PedidoInterno> pendientes = pedidoInternoRepository.findAll().stream()
                    .filter(p -> p.getOrdenCompra() == null)
                    .collect(Collectors.toList());
                    
            model.addAttribute("pedidosPendientes", pendientes);
            model.addAttribute("dbUsada", dbType);
        } finally {
            DatabaseContextHolder.clear();
        }
        return "ordenes";
    }

    @PostMapping("/guardar")
    public String crearOrden(@RequestParam("dbType") String dbType,
                             @RequestParam("prioridad") String prioridad,
                             @RequestParam("tamano") String tamano,
                             @RequestParam(value = "pedidoIds", required = false) List<Integer> pedidoIds) {
        DatabaseContextHolder.setDatabaseType(dbType);
        try {
            if (pedidoIds != null && !pedidoIds.isEmpty()) {
                OrdenCompra orden = new OrdenCompra();
                orden.setFechaEmision(LocalDate.now());
                orden.setPrioridad(prioridad);
                orden.setTamano(tamano);
                orden.setEstado("Activa");
                
                orden = ordenCompraRepository.save(orden); 

                for (Integer id : pedidoIds) {
                    PedidoInterno pedido = pedidoInternoRepository.findById(id).orElse(null);
                    if (pedido != null) {
                        pedido.setOrdenCompra(orden);
                        pedido.setEstado("En Orden");
                        pedidoInternoRepository.save(pedido);
                    }
                }
            }
        } finally {
            DatabaseContextHolder.clear();
        }
        return "redirect:/ordenes?db=" + dbType;
    }
}