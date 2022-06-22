
package com.spring.ejercicio01.entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="id_socio")
    private Socio socio;
    @ManyToOne
    @JoinColumn(name="id_libro")
    private Libro libro;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaPrestamo;
    private Boolean devuelto;

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Calendar getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Calendar fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.socio);
        hash = 83 * hash + Objects.hashCode(this.libro);
        hash = 83 * hash + Objects.hashCode(this.fechaPrestamo);
        hash = 83 * hash + Objects.hashCode(this.devuelto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prestamo other = (Prestamo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.socio, other.socio)) {
            return false;
        }
        if (!Objects.equals(this.libro, other.libro)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrestamo, other.fechaPrestamo)) {
            return false;
        }
        if (!Objects.equals(this.devuelto, other.devuelto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", socio=" + socio + ", libro=" + libro + ", fechaPrestamo=" + fechaPrestamo + ", devuelto=" + devuelto + '}';
    }
    
}
