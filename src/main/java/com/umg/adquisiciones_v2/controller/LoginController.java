package com.umg.adquisiciones_v2.controller;

import com.umg.adquisiciones_v2.config.DatabaseContextHolder;
import com.umg.adquisiciones_v2.model.Usuario;
import com.umg.adquisiciones_v2.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String index(HttpSession session) {
        session.invalidate(); 
        return "index";
    }

    @GetMapping("/menu")
    public String mostrarMenu(@RequestParam(value = "db", required = false) String dbType, Model model) {
        model.addAttribute("dbUsada", dbType);
        return "menu";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("dbType") String dbType,
                                HttpSession session,
                                Model model) {
        
        DatabaseContextHolder.setDatabaseType(dbType);

        try {
            Usuario usuario = usuarioRepository.findByEmailAndPassword(email, password);

            if (usuario != null && usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                session.setAttribute("usuarioLogueado", usuario);
                
                model.addAttribute("usuario", usuario);
                model.addAttribute("dbUsada", dbType);
                return "menu";
            } else {
                model.addAttribute("error", "Credenciales incorrectas.");
                return "index";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error de conexión: " + e.getMessage());
            return "index";
        } finally {
            DatabaseContextHolder.clear();
        }
    }

    @GetMapping("/seguridad")
    public String verSeguridad(@RequestParam(value = "db", required = false) String dbType, Model model) {
        model.addAttribute("dbUsada", dbType);
        return "seguridad";
    }
}