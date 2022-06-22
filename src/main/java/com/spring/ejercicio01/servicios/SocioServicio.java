package com.spring.ejercicio01.servicios;

import com.spring.ejercicio01.entidades.Socio;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.repositorios.SocioRepositorio;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SocioServicio {

    @Autowired
    private SocioRepositorio sr;

    public List<Socio> busquedaConDni(String dni) throws ServiciosError {
        if (dni == null || dni.isEmpty()) {
            List<Socio> socios = sr.findAll();
            return socios;
        } else {
            List<Socio> socios = sr.buscarPorDni(dni);
            if (socios.size() > 0) {
                return socios;
            } else {
                throw new ServiciosError("No existe ningún socio con ese dni");
            }
        }
    }

    public Socio buscarPorId(Long id) throws ServiciosError {
        Optional<Socio> resultado = sr.findById(id);
        if (resultado.isPresent()) {
            Socio socio = resultado.get();

            return socio;
        } else {
            throw new ServiciosError("El socio no existe");
        }
    }

    public List buscarPorNombre(String nombre) throws ServiciosError {
        if (nombre == null || nombre.isEmpty()) {
            List<Socio> socios = sr.findAll();
            if (socios.isEmpty()) {
                throw new ServiciosError("No hay ningún socio cargado");
            } else {
                return socios;
            }
        } else {
            List<Socio> socios = sr.buscarPorNombre(nombre);
            if (socios.size() > 0) {
                return socios;
            } else {
                throw new ServiciosError("No existe ningún socio con los datos indicados");
            }
        }
    }

    public Boolean validaSiExiste(String dni) throws ServiciosError {

        List<Socio> socios = sr.buscarPorDni(dni);
        if (socios.isEmpty()) {
            return true;
        } else {
            throw new ServiciosError("El socio ya existe");
        }
    }

    @Transactional
    public void cargarSocio(String nombre, String email, String dni) throws ServiciosError {

        validarDatos(nombre, email, dni);

        if (sr.buscarPorDni(dni).isEmpty()) {

            Socio socio = new Socio();
            socio.setNombre(nombre);
            socio.setDni(dni);
            socio.setEmail(email);
            socio.setFechaRegistro(new GregorianCalendar());
            socio.setAlta(true);

            sr.save(socio);

        } else {
            throw new ServiciosError("El socio ya existe");
        }
    }

    @Transactional(readOnly = true)
    public List<Socio> listaSociosActivos() {
        List<Socio> desplegableSocio = sr.buscarSociosActivos();
        return desplegableSocio;
    }

    public void modificarSocio(String nombre, String email, String dni, Long id) throws ServiciosError {

        validarDatos(nombre, email, dni);

        Optional<Socio> respuesta = sr.findById(id);
        if (respuesta.isPresent()) {
            Socio socio = respuesta.get();
            if (socio.getDni().equals(dni)) {

                socio.setNombre(nombre);
                socio.setDni(dni);
                socio.setEmail(email);
                socio.setFechaRegistro(new GregorianCalendar());
                socio.setAlta(true);

                sr.save(socio);

            } else {
                throw new ServiciosError("Debe crear un socio nuevo");
            }
        } else {
            throw new ServiciosError("No se encontró el socio soliictado");
        }
    }

    private void validarDatos(String nombre, String email, String dni)
            throws ServiciosError {

        if (nombre == null || nombre.isEmpty()) {
            throw new ServiciosError("El nombre no puede estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new ServiciosError("El email no puede estar vacío");
        }
        if (dni == null || dni.isEmpty()) {
            throw new ServiciosError("El dni no puede estar vacío");
        }
    }

    @Transactional
    public void bajaSocio(Long id) throws ServiciosError {
        Optional<Socio> respuesta = sr.findById(id);
        if (respuesta.isPresent()) {
            Socio socio = respuesta.get();
            if (socio.getAlta() == true) {
                socio.setAlta(false);
            } else {
                socio.setAlta(true);
            }
            sr.save(socio);
        } else {
            throw new ServiciosError("No se encontró el socio");
        }
    }
}
