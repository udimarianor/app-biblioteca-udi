
package com.spring.ejercicio01.servicios;

import com.spring.ejercicio01.entidades.Editorial;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class EditorialServicio {
    
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
      
    
    public List<Editorial> busquedaConNombre(String nombre) throws ServiciosError{
        if (nombre == null || nombre.isEmpty()) {
            List<Editorial> editoriales = editorialRepositorio.findAll();
            if (editoriales.isEmpty()) {
                throw new ServiciosError("No hay ninguna editorial cargada");
            } else {
                return editoriales;
            }
        } else {
            List<Editorial> editoriales = editorialRepositorio.buscarPorNombre(nombre);
            if (editoriales.size() > 0) {
                return editoriales;
            } else {
                throw new ServiciosError("No existe ninguna editorial con los datos indicados");
            }
        }       
    }
    
    public List<Editorial> busquedaConNombreEstricto(String nombre) throws ServiciosError{
        List<Editorial> editoriales = editorialRepositorio.buscarPorNombreEstricto(nombre);
        if (editoriales.size()>0) {
            return editoriales;
        }else{
            throw new ServiciosError("No existe ninguna editorial con los datos indicados");
        }
    }
    
    @Transactional
    public void cargarEditorial(String nombre)throws ServiciosError {
        validarDato(nombre);
        if (editorialRepositorio.buscarPorNombreEstricto(nombre).isEmpty()) {
            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            editorial.setAlta(true);
            
            editorialRepositorio.save(editorial);

        }else{
            throw new ServiciosError("La editorial ya existe");
            //ver después como hacer para agregarlo igual por si se llaman igual
        }        
    }
    
    public Editorial buscarPorId(Long id) throws ServiciosError{
        Optional<Editorial> resultado = editorialRepositorio.findById(id);
        if (resultado.isPresent()) {
            Editorial editorial = resultado.get();
            
            return editorial;
        }else{
            throw new ServiciosError("La editorial no existe");
        }
    }
    
    @Transactional
    public void modificarEditorial(Long id, String nombre) throws ServiciosError{
        validarDato(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (editorialRepositorio.buscarPorNombreEstricto(nombre).isEmpty()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }else{
            throw new ServiciosError("La editorial ya existe");
        }    
    }

       
    public void validarDato(String nombre) throws ServiciosError{
        if (nombre == null || nombre.isEmpty() ) {
            throw new ServiciosError("El nombre no puede estar vacío");
        }      
    }
    
    @Transactional
    public void bajaEditorial(Long id) throws ServiciosError {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            if (editorial.getAlta() == true) {
                editorial.setAlta(false);
            }else{
                editorial.setAlta(true);
            }
            editorialRepositorio.save(editorial);
        }else{
            throw new ServiciosError("No se encontró la editorial");
        }
    }
        
}
