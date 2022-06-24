
package com.spring.ejercicio01.repositorios;

import com.spring.ejercicio01.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    
    @Query ("select u from Usuario u where u.usuario like :nombre")
    public List<Usuario> buscarPorUsuario(@Param("nombre") String usuario);
    
    @Query ("select u from Usuario u where u.usuario like :nombre")
    public Usuario buscarUsuario(@Param("nombre") String usuario);
    
}
