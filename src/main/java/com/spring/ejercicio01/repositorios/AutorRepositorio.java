
package com.spring.ejercicio01.repositorios;

import com.spring.ejercicio01.entidades.Autor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long>{
    
    @Query ("select a from Autor a where a.nombre like %:nombre%")
    public List<Autor> buscarPorNombre(@Param("nombre")String nombre);
    
    @Query ("select a from Autor a where a.nombre like :nombre")
    public List<Autor> buscarPorNombreEstricto(@Param("nombre")String nombre);

    public Optional<Autor> findById(String id);
    
    @Query ("select a from Autor a where a.alta = true order by a.nombre asc")
    public List<Autor> buscarAutoresActivos();
    
      
}
