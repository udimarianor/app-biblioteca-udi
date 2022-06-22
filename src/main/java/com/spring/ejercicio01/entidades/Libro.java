
package com.spring.ejercicio01.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long isbn;
    private String titulo;
    private Integer anio;
    private Integer ejemplares;
    private Integer ejemplaresPrestados;
    private Integer ejemplaresRestantes;
    private Boolean alta;
    @ManyToOne
    @JoinColumn(name="id_autor")
    private Autor autor;
    @ManyToOne
    @JoinColumn(name="id_editorial")
    private Editorial editorial;

    public Libro() {
    }

    public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.autor = autor;
        this.editorial = editorial;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public Integer getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    public void setEjemplaresRestantes(Integer ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.isbn);
        hash = 47 * hash + Objects.hashCode(this.titulo);
        hash = 47 * hash + Objects.hashCode(this.anio);
        hash = 47 * hash + Objects.hashCode(this.ejemplares);
        hash = 47 * hash + Objects.hashCode(this.ejemplaresPrestados);
        hash = 47 * hash + Objects.hashCode(this.ejemplaresRestantes);
        hash = 47 * hash + Objects.hashCode(this.alta);
        hash = 47 * hash + Objects.hashCode(this.autor);
        hash = 47 * hash + Objects.hashCode(this.editorial);
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
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        if (!Objects.equals(this.anio, other.anio)) {
            return false;
        }
        if (!Objects.equals(this.ejemplares, other.ejemplares)) {
            return false;
        }
        if (!Objects.equals(this.ejemplaresPrestados, other.ejemplaresPrestados)) {
            return false;
        }
        if (!Objects.equals(this.ejemplaresRestantes, other.ejemplaresRestantes)) {
            return false;
        }
        if (!Objects.equals(this.alta, other.alta)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.editorial, other.editorial)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", isbn=" + isbn + ", titulo=" + titulo + ", anio=" + anio + ", ejemplares=" + ejemplares + ", ejemplaresPrestados=" + ejemplaresPrestados + ", ejemplaresRestantes=" + ejemplaresRestantes + ", alta=" + alta + ", autor=" + autor + ", editorial=" + editorial + '}';
    }

    

    
    
}
