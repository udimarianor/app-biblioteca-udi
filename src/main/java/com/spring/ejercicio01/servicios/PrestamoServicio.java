package com.spring.ejercicio01.servicios;

import com.spring.ejercicio01.entidades.Libro;
import com.spring.ejercicio01.entidades.Prestamo;
import com.spring.ejercicio01.entidades.Socio;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.repositorios.PrestamoRepositorio;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio pr;
    @Autowired
    private LibroServicio ls;

    @Transactional(readOnly = true)
    public List<Prestamo> busquedaPorSocio(Socio socio) throws ServiciosError {
        List<Prestamo> prestamosPorSocio = pr.buscarPorSocio(socio);
        if (prestamosPorSocio.isEmpty()) {
            throw new ServiciosError("No existen préstamos activos para ese socio");
        } else {
            return prestamosPorSocio;
        }
    }

    @Transactional(readOnly = true)
    public List<Prestamo> busquedaPorLibro(Libro libro) throws ServiciosError {
        List<Prestamo> prestamosPorLibro = pr.buscarPorLibro(libro);
        if (prestamosPorLibro.isEmpty()) {
            throw new ServiciosError("No hay préstamos asociados a ese libro");
        } else {
            return prestamosPorLibro;
        }
    }

    @Transactional(readOnly = true)
    public List buscarPrestamosActivos() throws ServiciosError {
        List<Prestamo> prestamosActivos = pr.buscarActivos();
        if (prestamosActivos.isEmpty()) {
            throw new ServiciosError("El socio no existe");
        } else {
            return prestamosActivos;
        }
    }

    @Transactional
    public void cargarPrestamo(Socio socio, Libro libro) throws ServiciosError {
        validarDatos(socio, libro);
        if (pr.verificaSiExisteIgual(socio, libro).isEmpty()) {
            Prestamo prestamo = new Prestamo();
            prestamo.setSocio(socio);
            prestamo.setLibro(libro);
            prestamo.setDevuelto(false);
            prestamo.setFechaPrestamo(new GregorianCalendar());
            
            ls.prestarLibro(libro);
            
            pr.save(prestamo);
        }else{
            throw new ServiciosError("Ya existe un préstamo igual no devuelto");
        }    
    }

    @Transactional(readOnly=true)
    private void validarDatos(Socio socio, Libro libro)
            throws ServiciosError {

        if (socio == null ) {
            throw new ServiciosError("El socio no puede estar vacío");
        }
        if (libro == null ) {
            throw new ServiciosError("El libro no puede estar vacío");
        }
    }

    @Transactional
    public void devolverPrestamo(Long id) throws ServiciosError {
        Optional<Prestamo> respuesta = pr.findById(id);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            if (prestamo.getDevuelto()==false) {
                prestamo.setDevuelto(true);
                ls.devolverLibro(prestamo.getLibro());
                
                pr.save(prestamo);
                
            }else{
                throw new ServiciosError("El préstamo ya fue devuelto");
            }
        } else {
            throw new ServiciosError("No se encontró el préstamo");
        }
    }
}
