package com.spring.ejercicio01.controladores;

import com.spring.ejercicio01.entidades.Autor;
import com.spring.ejercicio01.entidades.Editorial;
import com.spring.ejercicio01.entidades.Libro;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio ls;

    @GetMapping("")
    public String irAdmLibros(ModelMap autores, ModelMap editoriales) {
        autores.addAttribute("autores", ls.listarAutores());
        editoriales.addAttribute("editoriales", ls.listarEditoriales());
        return "admLibro.html";
    }

    @GetMapping("/volver")
    public String volver(ModelMap modelo) {
        modelo.addAttribute("autores", ls.listarAutores());
        modelo.addAttribute("editoriales", ls.listarEditoriales());
        return "admLibro.html";
    }

    @GetMapping("/buscarLibro")
    public String buscarLibro(ModelMap modelo, @RequestParam String titulo) {
        try {
            List<Libro> listaLibros = ls.busquedaConNombre(titulo);
            modelo.addAttribute("libros", listaLibros);
        } catch (ServiciosError se) {
            modelo.put("error", se.getMessage());
        }
        return "resLibros.html";
    }

    @PostMapping("/guardarLibro")
    public String guardadoDeLibro(@RequestParam Long isbn, @RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam Autor autor, @RequestParam Editorial editorial,
            ModelMap modelo) {
        try {
            ls.cargarLibro(isbn, titulo, anio, ejemplares, autor, editorial);
            modelo.put("confirmacion", "El libro se cargó con éxito");
        } catch (ServiciosError se) {
            modelo.put("error", se.getMessage());
        }

        modelo.addAttribute("autores", ls.listarAutores());
        modelo.addAttribute("editoriales", ls.listarEditoriales());

        return "admLibro.html";
    }

    @GetMapping("/modifLibro/{id}")
    public String requestModificar(@PathVariable("id") Long id, ModelMap modelo) {
        try {
            Libro libro = ls.buscarPorId(id);
            modelo.addAttribute("libroElegido", libro);
            modelo.addAttribute("autores", ls.listarAutores());
            modelo.addAttribute("editoriales", ls.listarEditoriales());
        } catch (ServiciosError se) {
            modelo.put("error", se.getMessage());
        }
        return "modifLibro.html";
    }

    @PostMapping("/guardarModif")
    public String guardarModifString(@RequestParam Long id, @RequestParam Long isbn, @RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam Autor autor, @RequestParam Editorial editorial,
            ModelMap modelo) {
        try {
            ls.modificarLibro(id, isbn, titulo, anio, ejemplares, autor, editorial);
            modelo.addAttribute("confirmacion", "El libro se modificó con éxito");
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
        }
        return "admLibro.html";
    }

    @GetMapping("/bajaLibro/{id}")
    public String darBajaLibro(@PathVariable("id") Long id, Model modelo) {
        try {
            ls.bajaLibro(id);
            modelo.addAttribute("confirmacion", "La baja del libro fue exitosa");
            modelo.addAttribute("autores", ls.listarAutores());
            modelo.addAttribute("editoriales", ls.listarEditoriales());
        } catch (ServiciosError se) {
            modelo.addAttribute("error", se.getMessage());
            modelo.addAttribute("autores", ls.listarAutores());
            modelo.addAttribute("editoriales", ls.listarEditoriales());
        }
        return "admLibro.html";
    }
}
