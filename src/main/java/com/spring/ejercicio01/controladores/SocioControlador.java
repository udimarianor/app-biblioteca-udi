package com.spring.ejercicio01.controladores;


import com.spring.ejercicio01.entidades.Socio;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.servicios.SocioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping("/socio")
public class SocioControlador {

    @Autowired
    private SocioServicio ss;

    @GetMapping("")
    public String irAdmSocios() {
        return "admSocio.html";
    }

    @GetMapping("/volver")
    public String volver() {
        return "admSocio.html";
    }

    @GetMapping("/buscarSocio")
    public String buscarPorNombre(Model modelo, @RequestParam String nombre) {
        try {
            List<Socio> socios = ss.buscarPorNombre(nombre);
            modelo.addAttribute("socios", socios);
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "resSocios.html";
    }

    @PostMapping("/guardarSocio")
    public String guardadoDeSocio(@RequestParam String nombre, @RequestParam String email, 
            @RequestParam String dni, Model modelo){
        try {
            ss.cargarSocio(nombre, email, dni);
            modelo.addAttribute("confirmacion", "El socio se guardó con éxito");
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }

        return "admSocio.html";
    }

    @GetMapping("/modifSocio/{id}")
    public String requestModificar(@PathVariable("id") Long id, ModelMap modelo) {
        try {
            Socio socio = ss.buscarPorId(id);
            modelo.addAttribute("socio", socio);    
        } catch (ServiciosError se) {
            modelo.put("error", se.getMessage());
        }
        return "modifSocio.html";
    }

    @PostMapping("/guardarModif")
    public String guardarModifString(@RequestParam Long id, @RequestParam String nombre, @RequestParam String email, 
            @RequestParam String dni, Model modelo) {
        try {
            ss.modificarSocio(nombre, email, dni, id);
            modelo.addAttribute("confirmacion", "El socio se modificó con éxito");
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "admSocio.html";
    }

    @GetMapping("/bajaSocio/{id}")
    public String darBajaSocio(@PathVariable("id") Long id, Model modelo) {
        try {
            ss.bajaSocio(id);
            modelo.addAttribute("confirmacion", "La baja del socio fue exitosa");
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "admSocio.html";
    }

}
