package com.db.finki.www.build_board.config;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler successHandler;


    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthenticationSuccessHandler successHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request ->
//                        request.requestMatchers(
//                                        "/",
//                                        "/contact",
//                                        "/about",
//                                    "/project_imgs/buildboard-logo.jpg",
//                                "*.ico",
//                                "*.jpg",
//                                "*.png",
//                                "/register",
//                                        "/css/**",
//                                        "/js/**"
//                                ).permitAll()
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/topic/*", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/project/*",HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher("/avatars/**",HttpMethod.GET.name())
//                                ).permitAll()
//                                .requestMatchers("/topic/**","/project/**").authenticated()
//
//                                .anyRequest().authenticated()
//                ).formLogin(formLogin ->
//                        formLogin.permitAll()
//                                .defaultSuccessUrl("/")
//                                .successHandler(successHandler)
//                )
//                .logout(logout ->
//                        logout.logoutSuccessUrl("/")
//                                .clearAuthentication(true)
//                                .invalidateHttpSession(true)
//                                .deleteCookies("JSESSIONID")
//                );
//
//        return http.build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
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
