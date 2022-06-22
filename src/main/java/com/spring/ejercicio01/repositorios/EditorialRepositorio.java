
package com.spring.ejercicio01.repositorios;

import com.spring.ejercicio01.entidades.Editorial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, Long>{
    
   @Query ("select e from Editorial e where e.nombre like %:nombre%")
    public List<Editorial> buscarPorNombre(@Param("nombre")String nombre);
    
    @Query ("select e from Editorial e where e.nombre like :nombre")
    public List<Editorial> buscarPorNombreEstricto(@Param("nombre")String nombre);

    public Optional<Editorial> findById(Long id);
    
    @Query ("select e from Editorial e where e.alta = true order by e.nombre asc")
    public List<Editorial> buscarEditorialesActivas();
    
}
