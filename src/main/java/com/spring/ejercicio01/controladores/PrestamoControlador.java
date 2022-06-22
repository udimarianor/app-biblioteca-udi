package com.spring.ejercicio01.controladores;

import com.spring.ejercicio01.entidades.Libro;
import com.spring.ejercicio01.entidades.Prestamo;
import com.spring.ejercicio01.entidades.Socio;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.servicios.LibroServicio;
import com.spring.ejercicio01.servicios.PrestamoServicio;
import com.spring.ejercicio01.servicios.SocioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping("/prestamo")
public class PrestamoControlador {

    @Autowired
    private PrestamoServicio ps;
    @Autowired
    private SocioServicio ss;
    @Autowired
    private LibroServicio ls;

    @GetMapping("")
    public String irPrestamo(Model modelo) {
        try {
            modelo.addAttribute("socios", ss.listaSociosActivos());
            modelo.addAttribute("libros", ls.librosActivos());
        } catch (Exception se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "admPrestamo.html";
    }

    @GetMapping("/volver")
    public String volver(Model modelo) {
        try {
            modelo.addAttribute("socios", ss.listaSociosActivos());
            modelo.addAttribute("libros", ls.librosActivos());
        } catch (Exception se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "admPrestamo.html";
    }

    @GetMapping("/buscarPrestamo")
    public String buscarPrestamoPorLibro(Model modelo, @RequestParam Socio socio) {
        try {
            List<Prestamo> listaPrestamos = ps.busquedaPorSocio(socio);
            modelo.addAttribute("prestamos", listaPrestamos);
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "resPrestamos.html";
    }

    @PostMapping("/guardarPrestamo")
    public String guardaPrestamo(@RequestParam("socio")Socio socio, @RequestParam("libro")Libro libro, Model modelo) throws ServiciosError {
        try {
            ps.cargarPrestamo(socio, libro);
            modelo.addAttribute("confirmacion", "El préstamo se cargó con éxito");
            modelo.addAttribute("socios", ss.listaSociosActivos());
            modelo.addAttribute("libros", ls.librosActivos());
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
            modelo.addAttribute("socios", ss.listaSociosActivos());
            modelo.addAttribute("libros", ls.librosActivos());//ver el tema del unreported sasasa
        }
        return "admPrestamo.html";
    }

    @GetMapping("/devolverPrestamo/{id}")
    public String devolverPrestamo(@PathVariable("id") Long id, Model modelo) {
        try {
            ps.devolverPrestamo(id);
            modelo.addAttribute("confirmacion", "Se ha devuelto el libro");
            modelo.addAttribute("socios", ss.listaSociosActivos());
            modelo.addAttribute("libros", ls.librosActivos());
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
            modelo.addAttribute("socios", ss.listaSociosActivos());
            modelo.addAttribute("libros", ls.librosActivos());
        }
        return "admPrestamo.html";
    }

}
