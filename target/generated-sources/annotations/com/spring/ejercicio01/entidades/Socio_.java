package com.spring.ejercicio01.entidades;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-06-22T19:34:36")
@StaticMetamodel(Socio.class)
public class Socio_ { 

    public static volatile SingularAttribute<Socio, Boolean> alta;
    public static volatile SingularAttribute<Socio, Calendar> fechaRegistro;
    public static volatile SingularAttribute<Socio, Long> id;
    public static volatile SingularAttribute<Socio, String> nombre;
    public static volatile SingularAttribute<Socio, String> email;
    public static volatile SingularAttribute<Socio, String> dni;

}