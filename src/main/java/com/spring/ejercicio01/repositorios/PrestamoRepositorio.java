
package com.spring.ejercicio01.repositorios;

import com.spring.ejercicio01.entidades.Libro;
import com.spring.ejercicio01.entidades.Prestamo;
import com.spring.ejercicio01.entidades.Socio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository        
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {
    
    @Query ("select p from Prestamo p where p.socio like :socio and p.devuelto = false order by p.id desc")
    public List<Prestamo> buscarPorSocio(@Param("socio")Socio socio); 
    
    @Query ("select p from Prestamo p where p.libro like :libro and p.devuelto = false order by p.id desc")
    public List<Prestamo> buscarPorLibro(@Param("libro")Libro libro);
    
    @Query ("select p from Prestamo p where p.devuelto = false order by p.id desc")
    public List<Prestamo> buscarActivos();
    
    @Query ("select p from Prestamo p where p.socio like :socio and p.libro like :libro and p.devuelto = false")
    public List<Prestamo> verificaSiExisteIgual(@Param("socio") Socio socio, @Param("libro") Libro libro);
}
