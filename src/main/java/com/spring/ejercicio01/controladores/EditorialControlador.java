
package com.spring.ejercicio01.controladores;

import com.spring.ejercicio01.entidades.Editorial;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio es;
        
    @GetMapping("")
    public String irAutor(){
        return "admEditorial.html";
    }
    
    @GetMapping("/volver")
    public String volver(){
        return "admEditorial.html";
    }
    
    @GetMapping("/buscarEditoriales")
    public String editorialesPorNombre(ModelMap modelo, @RequestParam String nombreEditorial){
        try{
        List<Editorial> listaEditoriales = es.busquedaConNombre(nombreEditorial);
        modelo.addAttribute("editoriales", listaEditoriales);
        }catch(ServiciosError se){
            modelo.put("error", se.getMessage());
        }
        return "resEditoriales.html";
    }
    
    @PostMapping("/crearEditorial")
    public String crearEditorial(ModelMap modelo, @RequestParam String nuevaEditorial){
        try{
           es.cargarEditorial(nuevaEditorial);
           modelo.put("confirmacion", "La editorial se cargó con éxito");
        }catch(ServiciosError se){
           modelo.put("error", se.getMessage());
        }
        return "admEditorial.html";
    }
    
    @GetMapping("/modifEditorial/{id}")
    public String requestModificar(@PathVariable("id") Long id, ModelMap modelo){
        try{
            Editorial editorial = es.buscarPorId(id);
            modelo.addAttribute("editorial", editorial);
        }catch(ServiciosError se){
            modelo.put("error", se.getMessage());
        }       
        return "modifEditorial.html";
    }
    
    @PostMapping("/guardarModif")
    public String guardarModifString(@RequestParam Long id, @RequestParam String nombreEditorial, Model modelo){
        try{
            es.modificarEditorial(id, nombreEditorial);
            modelo.addAttribute("confModif", "La editorial se modificó con éxito");
        }catch(ServiciosError se){
            modelo.addAttribute("errorModif", se.getMessage());
        }
        return "admEditorial.html";
    }
    
    @GetMapping("/bajaEditorial/{id}")
    public String darBajaEditorial(@PathVariable("id") Long id, Model modelo){
        try{
            es.bajaEditorial(id);
            modelo.addAttribute("confModif", "La baja fue exitosa");
        }catch(ServiciosError se){
            modelo.addAttribute("error", se.getMessage());
        }       
        return "admEditorial.html";
    }
}
