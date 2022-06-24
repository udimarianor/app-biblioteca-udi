package com.spring.ejercicio01.servicios;

import com.spring.ejercicio01.entidades.Autor;
import com.spring.ejercicio01.entidades.Editorial;
import com.spring.ejercicio01.entidades.Libro;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.repositorios.AutorRepositorio;
import com.spring.ejercicio01.repositorios.EditorialRepositorio;
import com.spring.ejercicio01.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public List<Libro> busquedaConNombre(String nombre) throws ServiciosError {
        if (nombre == null || nombre.isEmpty()) {
            List<Libro> libros = (List<Libro>) libroRepositorio.findAll();
            if (libros.isEmpty()) {
                throw new ServiciosError("No hay ningún libro cargado");
            } else {
                return libros;
            }
        } else {
            List<Libro> libros = libroRepositorio.buscarPorNombre(nombre);
            if (libros.size() > 0) {
                return libros;
            } else {
                throw new ServiciosError("No existe ningún libro con los datos indicados");
            }
        }
    }

    public Libro buscarPorId(Long id) throws ServiciosError {
        Optional<Libro> resultado = libroRepositorio.findById(id);
        if (resultado.isPresent()) {
            Libro libro = resultado.get();

            return libro;
        } else {
            throw new ServiciosError("El libro no existe");
        }
    }

    public Boolean validaSiExiste(String titulo, Integer anio, Autor autor, Editorial editorial) throws ServiciosError {

        List<Libro> libros = libroRepositorio.validaSiExiste(titulo, anio, autor, editorial);
        if (libros.isEmpty()) {
            return true;
        } else {
            throw new ServiciosError("El libro ya existe");
        }
    }

    public Boolean validaIsbn(Integer isbn) throws ServiciosError {
        if (libroRepositorio.validaIsbn(isbn).isEmpty()) {
            return true;
        } else {
            throw new ServiciosError("El isbn ya existe");
        }
    }

    @Transactional
    public void cargarLibro(Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Autor autor, Editorial editorial)
            throws ServiciosError {

        validarDatos(isbn, titulo, anio, ejemplares, autor, editorial);

        if (validaIsbn(isbn)) {

            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
            libro.setAlta(true);

            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);

        } else {
            throw new ServiciosError("El libro ya existe");
            //ver después como hacer para agregarlo igual por si se llaman igual
        }
    }

    public List<Autor> listarAutores() {
        List<Autor> desplegableAutores = autorRepositorio.buscarAutoresActivos();
        return desplegableAutores;
    }

    public List<Editorial> listarEditoriales() {
        List<Editorial> desplegableEditoriales = editorialRepositorio.buscarEditorialesActivas();
        return desplegableEditoriales;
    }

    public void modificarLibro(Long id, Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Autor autor, Editorial editorial)
            throws ServiciosError {

        validarDatos(isbn, titulo, anio, ejemplares, autor, editorial);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            if (libro.getIsbn().equals(isbn)) {

                if (libro.getEjemplaresPrestados() < ejemplares) {
                    libro.setIsbn(isbn);
                    libro.setTitulo(titulo);
                    libro.setAnio(anio);
                    libro.setEjemplares(ejemplares);
                    libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
                    libro.setAlta(true);

                    libro.setAutor(autor);
                    libro.setEditorial(editorial);

                    libroRepositorio.save(libro);
                } else {
                    throw new ServiciosError("La cantidad de ejemplares no puede ser menor a los prestados");
                }

            } else {
                throw new ServiciosError("Debe crear un libro nuevo");
            }
        } else {
            throw new ServiciosError("No se encontró el libro solicitado");
            //esto no va a pasar nunca si vengo de la vista de buscar!!!
        }
    }

    private void validarDatos(Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Autor autor, Editorial editorial)
            throws ServiciosError {

        if (isbn == null || isbn == 0) {
            throw new ServiciosError("El isbn no puede ser cero o inexistente");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ServiciosError("El título no puede estar vacío");
        }
        if (anio == 0 || anio.toString().isEmpty()) {
            throw new ServiciosError("El año no puede estar vacío");
        }
        if (ejemplares == 0 || ejemplares.toString().isEmpty()) {
            throw new ServiciosError("Ejemplares no puede estar vacío");
        }
        if (autor == null || autor.getNombre().isEmpty()) {
            throw new ServiciosError("El autor no puede estar vacío");
        }
        if (editorial == null || editorial.getNombre().isEmpty()) {
            throw new ServiciosError("La editorial no puede estar vacía");
        }
    }

    @Transactional
    public void bajaLibro(Long id) throws ServiciosError {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            if (libro.getAlta() == true) {
                libro.setAlta(false);
            } else {
                libro.setAlta(true);
            }
            libroRepositorio.save(libro);
        } else {
            throw new ServiciosError("No se encontró el libro");
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> librosActivos() {
        List<Libro> librosActivos = libroRepositorio.librosActivos();
        return librosActivos;
    }

    @Transactional
    public void prestarLibro(Libro libro) throws ServiciosError {
        Optional<Libro> resultado = Optional.of(libroRepositorio.buscarPorIdActivos(libro.getId()));
        if (resultado.isPresent()) {
            Libro libroPrestado = resultado.get();

            if (libroPrestado.getEjemplaresRestantes() >= 1) {
                libroPrestado.setEjemplaresPrestados(libroPrestado.getEjemplaresPrestados() + 1);
                libroPrestado.setEjemplaresRestantes(libroPrestado.getEjemplares()
                        - libroPrestado.getEjemplaresPrestados());
                libroRepositorio.save(libroPrestado);
            } else {
                throw new ServiciosError("No existen ejemplares disponibles de este libro");
            }
        } else {
            System.out.println("El libro no existe");
        }
    }

    @Transactional
    public void devolverLibro(Libro libro) throws ServiciosError {
        Optional<Libro> resultado = Optional.of(libroRepositorio.buscarPorIdActivos(libro.getId()));
        if (resultado.isPresent()) {
            Libro libroPrestado = resultado.get();

            if (libroPrestado.getEjemplaresPrestados() >= 1) {
                libroPrestado.setEjemplaresPrestados(libroPrestado.getEjemplaresPrestados() - 1);
                libroPrestado.setEjemplaresRestantes(libroPrestado.getEjemplares()
                        - libroPrestado.getEjemplaresPrestados());

                libroRepositorio.save(libroPrestado);
            } else {
                throw new ServiciosError("No existen ejemplares prestados de este libro");
            }
        } else {
            System.out.println("El libro no existe");
        }
    }
}
