package com.spring.ejercicio01.controladores;

import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio us;
    
    @GetMapping("")
    public String inicio(){
        return "index.html";
    }

    @GetMapping("/login")
    public String login(Model modelo, @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout) {

        if (error != null) {
            modelo.addAttribute("error", "Las credenciales no son correctas");
        }
        if (logout != null) {
            modelo.addAttribute("logout", "El logout fue exitoso");
        }
        return "login.html";
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/menu")
    public String irMenu() {
        return "menu.html";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        
        return "redirect:/";
    }

    
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/crearUsuario")
    public String irCrearUsuario() {
        return "crearUsuario.html";
    }

    
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/guardarUsuario")
    public String botonGuardarUsuario(@RequestParam("usuario") String usuario, @RequestParam("contrasenia") String contrasenia,
            @RequestParam("contrasenia1") String contrasenia1, @RequestParam("email") String email, Model modelo) {
        try {
            us.crearUsuario(usuario, contrasenia, contrasenia1, email);
            modelo.addAttribute("confirmacion", "El usuario se creó con éxito");
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }catch (Exception e){
            modelo.addAttribute("error", "Error del controlador");
                      
        }
        return "menu.html";
    }

//    @PostMapping("/enviarLogin")
//    public String ingresar(ModelMap modelo, @RequestParam String usuario, @RequestParam String contrasenia) {
//        try {
//            us.login(usuario, contrasenia);
//            return "menu.html";
//        } catch (ServiciosError se) {
//            modelo.put("error", se.getMessage());
//            return "login.html";
//        }
//    }
}
