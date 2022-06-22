
package com.spring.ejercicio01.repositorios;


import com.spring.ejercicio01.entidades.Socio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository        
public interface SocioRepositorio extends JpaRepository<Socio, Long> {
    
    @Query ("select s from Socio s where s.dni like :dni")
    public List<Socio> buscarPorDni(@Param("dni")String dni); 
    
    @Query ("select s from Socio s where s.alta = true order by nombre")
    public List<Socio> buscarSociosActivos();
    
    @Query ("select s from Socio s where s.nombre like %:nombre% order by nombre")
    public List<Socio> buscarPorNombre(String nombre);
    
}
