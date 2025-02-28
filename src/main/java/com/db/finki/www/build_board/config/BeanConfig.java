package com.db.finki.www.build_board.config;

import com.db.finki.www.build_board.service.user.AuthenticationSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class BeanConfig {
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }
}
