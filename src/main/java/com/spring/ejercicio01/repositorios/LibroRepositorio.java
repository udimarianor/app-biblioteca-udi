
package com.spring.ejercicio01.repositorios;

import com.spring.ejercicio01.entidades.Editorial;
import com.spring.ejercicio01.entidades.Autor;
import com.spring.ejercicio01.entidades.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository        
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    
    @Query ("select l from Libro l where l.titulo like %:titulo%")
    public List<Libro> buscarPorNombre(@Param("titulo")String nombre);
    
    @Query ("select l from Libro l where l.titulo like :titulo and l.anio = :anio "
            + "and l.autor = :autor and l.editorial = :editorial")
    public List<Libro> validaSiExiste(@Param("titulo")String titulo, @Param("anio")Integer anio, 
            @Param("autor")Autor autor, @Param("editorial") Editorial editorial);
    
    @Query ("select l from Libro l where l.isbn = :isbn")
    public List<Libro> validaIsbn(@Param("isbn")Integer isbn);

    public Optional<Libro> findById(Long id); 
    
    @Query("select l from Libro l where l.id = :id and l.alta = true")
    public Libro buscarPorIdActivos(@Param("id") Long id);
    
    @Query ("select l from Libro l where l.alta = true order by titulo")
    public List<Libro> librosActivos();
   
}
