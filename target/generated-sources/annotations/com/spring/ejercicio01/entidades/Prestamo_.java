package com.spring.ejercicio01.entidades;

import com.spring.ejercicio01.entidades.Libro;
import com.spring.ejercicio01.entidades.Socio;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-06-16T16:21:16")
@StaticMetamodel(Prestamo.class)
public class Prestamo_ { 

    public static volatile SingularAttribute<Prestamo, Libro> libro;
    public static volatile SingularAttribute<Prestamo, Calendar> fechaPrestamo;
    public static volatile SingularAttribute<Prestamo, Socio> socio;
    public static volatile SingularAttribute<Prestamo, Boolean> devuelto;
    public static volatile SingularAttribute<Prestamo, Long> id;

}