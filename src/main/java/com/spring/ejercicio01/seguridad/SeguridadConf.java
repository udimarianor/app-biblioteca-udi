package com.spring.ejercicio01.seguridad;

import com.spring.ejercicio01.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConf extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioservicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioservicio)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/static/**")
                .permitAll()
        .and().formLogin()
                .loginPage("/login")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("usuario")
                    .passwordParameter("contrasenia")
                    .defaultSuccessUrl("/menu")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll();
                }
        
     

}
