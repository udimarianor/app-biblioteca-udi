package com.spring.ejercicio01.servicios;

import com.spring.ejercicio01.entidades.Autor;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    @Autowired
    private AutorRepositorio autorRepositorio;

    public List<Autor> busquedaConNombre(String nombre) throws ServiciosError {
        if (nombre == null || nombre.isEmpty()) {
            List<Autor> autores = autorRepositorio.findAll();
            if (autores.isEmpty()) {
                throw new ServiciosError("No hay ningún autor cargado");
            } else {
                return autores;
            }
        } else {
            List<Autor> autores = autorRepositorio.buscarPorNombre(nombre);
            if (autores.size() > 0) {
                return autores;
            } else {
                throw new ServiciosError("No existe ningún autor con los datos indicados");
            }
        }
    }

    public List<Autor> busquedaConNombreEstricto(String nombre) throws ServiciosError {
        List<Autor> autores = autorRepositorio.buscarPorNombreEstricto(nombre);
        if (autores.size() > 0) {
            return autores;
        } else {
            throw new ServiciosError("No existe ningún autor con los datos indicados");
        }
    }

    @Transactional
    public void cargarAutor(String nombre) throws ServiciosError {
        validarDato(nombre);
        if (autorRepositorio.buscarPorNombreEstricto(nombre).isEmpty()) {
            Autor autor = new Autor();
            autor.setNombre(nombre);
            autor.setAlta(true);

            autorRepositorio.save(autor);

        } else {
            throw new ServiciosError("El autor ya existe");
            //ver después como hacer para agregarlo igual por si se llaman igual
        }
    }

    public Autor buscarPorId(Long id) throws ServiciosError {
        Optional<Autor> resultado = autorRepositorio.findById(id);
        if (resultado.isPresent()) {
            Autor autor = resultado.get();

            return autor;
        } else {
            throw new ServiciosError("El autor no existe");
        }
    }

    @Transactional
    public void modificarAutor(Long id, String nombre) throws ServiciosError {
        validarDato(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (autorRepositorio.buscarPorNombreEstricto(nombre).isEmpty()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new ServiciosError("El autor ya existe");
        }
    }

    public void validarDato(String nombre) throws ServiciosError {
        if (nombre == null || nombre.isEmpty()) {
            throw new ServiciosError("El nombre no puede estar vacío");
        }
    }

    @Transactional
    public void bajaAutor(Long id) throws ServiciosError {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            if (autor.getAlta() == true) {
                autor.setAlta(false);
            } else {
                autor.setAlta(true);
            }
            autorRepositorio.save(autor);
        } else {
            throw new ServiciosError("No se encontró el autor");
        }
    }

}
