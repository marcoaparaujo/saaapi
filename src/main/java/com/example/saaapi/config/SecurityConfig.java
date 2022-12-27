package com.example.saaapi.config;

import com.example.saaapi.security.JwtAuthFilter;
import com.example.saaapi.security.JwtService;
import com.example.saaapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/v1/alunos/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/atividadescomplementares/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/concedentes/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/convenios/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/coordenadores/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/cursos/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/estagios/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/supervisoresatividadescomplementares/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/supervisoresestagio/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/vagas/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**")
                    .permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
