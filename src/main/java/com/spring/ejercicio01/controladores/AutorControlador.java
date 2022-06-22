
package com.spring.ejercicio01.controladores;

import com.spring.ejercicio01.entidades.Autor;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio as;
        
    @GetMapping("")
    public String rutaAutor(){
        return "admAutor.html";
    }
    
    @GetMapping("/volver")
    public String volver(){
        return "admEditorial.html";
    }
    
    @GetMapping("/buscarAutores")
    public String autoresPorNombre(Model modelo, @RequestParam String nombreAutor){
        try{
        List<Autor> listaAutores = as.busquedaConNombre(nombreAutor);
        modelo.addAttribute("autores", listaAutores);
        }catch(ServiciosError se){
            modelo.addAttribute("error", se.getMessage());
        }
        return "resAutores.html";
    }
    
    @PostMapping("/crearAutor")
    public String crearAutor(Model modelo, @RequestParam String nuevoAutor){
        try{
           as.cargarAutor(nuevoAutor);
           modelo.addAttribute("confirmacion", "El autor se cargó con éxito");
        }catch(ServiciosError se){
           modelo.addAttribute("error", se.getMessage());
        }
        return "admAutor.html";
    }
    
    @GetMapping("/modifAutor/{id}")
    public String modifAutor(@PathVariable("id") Long id, Model modelo){
        try{
            Autor autor = as.buscarPorId(id);
            modelo.addAttribute("autor", autor);
        }catch(ServiciosError se){
            modelo.addAttribute("error", se.getMessage());
        }       
        return "modifAutor.html";
    }
    
    @PostMapping("/guardarModif")
    public String guardarModifString(@RequestParam Long id, @RequestParam String nombreAutor, Model modelo){
        try{
            as.modificarAutor(id, nombreAutor);
            modelo.addAttribute("confModif", "El autor se modificó con éxito");
        }catch(ServiciosError se){
            modelo.addAttribute("errorModif", se.getMessage());
        }
        return "admAutor.html";
    }
    
    @GetMapping("/bajaAutor/{id}")
    public String darBajaAutor(@PathVariable("id") Long id, Model modelo){
        try{
            as.bajaAutor(id);
            modelo.addAttribute("confModif", "La baja fue exitosa");
        }catch(ServiciosError se){
            modelo.addAttribute("error", se.getMessage());
        }       
        return "admAutor.html";
    }
}
