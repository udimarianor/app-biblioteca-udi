package com.spring.ejercicio01.servicios;

import com.spring.ejercicio01.entidades.Usuario;
import com.spring.ejercicio01.enums.Role;
import com.spring.ejercicio01.errores.ServiciosError;
import com.spring.ejercicio01.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario crearUsuario(String usuario, String contrasenia, String contrasenia1,
            String email) throws ServiciosError {

        if (validarDatos(usuario, contrasenia, contrasenia1, email)) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Usuario usuarioCrear = new Usuario();
            usuarioCrear.setUsuario(usuario);
            usuarioCrear.setContrasenia(encoder.encode(contrasenia));
            usuarioCrear.setEmail(email);
            usuarioCrear.setRol(Role.USER);

            usuarioRepositorio.save(usuarioCrear);

            return usuarioCrear;
        } else {
            throw new ServiciosError("Hay un problema en el servicio crearUsuario");
        }

    }

    @Transactional(readOnly = true)
    public Boolean validarDatos(String usuario, String contrasenia, String contrasenia1,
            String email)
            throws ServiciosError {
        if (usuario.isEmpty() || usuario == null) {
            throw new ServiciosError("El usuario no puede estar vacío");
        }
        if (usuario.length() < 8) {
            throw new ServiciosError("El usuario debe tener al menos 8 caracteres");
        }
        if (contrasenia.isEmpty() || contrasenia == null) {
            throw new ServiciosError("Hay un error en la contraseña ingresada");
        }
        if (!contrasenia.equals(contrasenia1)) {
            throw new ServiciosError("Las contraseñas no coinciden");
        }
        if (email.isEmpty() || email == null) {
            throw new ServiciosError("El usuario no puede estar empty");
        }
        if (usuarioRepositorio.buscarUsuario(usuario) != null) {
            throw new ServiciosError("El usuario ya existe");
        }
        return true;
    }

    public Boolean login(String usuario, String contrasenia) throws ServiciosError {
        Optional<Usuario> respuesta = Optional.of(usuarioRepositorio.buscarUsuario(usuario));
        if (respuesta.isPresent()) {
            Usuario objUsuario = respuesta.get();
            if (objUsuario.getContrasenia().equals(contrasenia)) {
                return true;
                //podría guardar los logins después...
            } else {
                throw new ServiciosError("Las credenciales no son válidas");
            }
        } else {
            throw new ServiciosError("Las credenciales no son válidas");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

        Usuario usuarioObj = usuarioRepositorio.buscarUsuario(usuario);
        if (usuarioObj != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            
            //acá se agrega un permiso
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+usuarioObj.getRol());
            permisos.add(p1);

            //acá se recupera la info de la sesión
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession" , usuarioObj);

            User user = new User(usuarioObj.getUsuario(), usuarioObj.getContrasenia(), permisos);
            return user;
        } else {
            return null;
        }

    }

}
