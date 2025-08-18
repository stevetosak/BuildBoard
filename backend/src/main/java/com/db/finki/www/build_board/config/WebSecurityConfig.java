package com.db.finki.www.build_board.config;

import com.db.finki.www.build_board.config.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    public WebSecurityConfig(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
                            ) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JWTAuthenticationFilter jwtFilter
                                                  ) throws Exception {
        http
                .authorizeHttpRequests(request ->
                                request
                                        .requestMatchers(new AntPathRequestMatcher("/**",
                                                HttpMethod.OPTIONS.name()))
                                        .permitAll()
                                        .requestMatchers(
                                                "/",
                                                "/contact",
                                                "/about",
                                                "/project_imgs/buildboard-logo.jpg",
                                                "*.ico",
                                                "*.jpg",
                                                "*.png",
                                                "/register",
                                                "/login",
                                                "/css/**",
                                                "/js/**"
                                                        )
                                        .permitAll()
                                        //Bez jwt, samo get
                                        .requestMatchers(
                                                new AntPathRequestMatcher("/**",HttpMethod.OPTIONS.name()),
                                                new AntPathRequestMatcher("/threads",
                                                        HttpMethod.GET.name()),
                                                new AntPathRequestMatcher("/api/**",HttpMethod.GET.name())
                                                        )
                                        .permitAll()
                                        // Sve
                                        .anyRequest()
                                        .authenticated()
                                      )
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                                );
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(new HttpSessionSecurityContextRepository());
    }
}
