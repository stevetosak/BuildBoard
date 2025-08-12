package com.db.finki.www.build_board.config.jwt;

import com.db.finki.www.build_board.config.jwt.dtos.TokenDTO;
import com.db.finki.www.build_board.config.jwt.dtos.UserLoginDTO;
import com.db.finki.www.build_board.dto.BBUserMinClaimSet;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {
    private final BBUserDetailsService bbUserDetailsService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public JWTController(BBUserDetailsService bbUserDetailsService, JWTUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.bbUserDetailsService = bbUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody UserLoginDTO userLoginDTO
    ) {
        BBUser user = (BBUser) bbUserDetailsService.loadUserByUsername(userLoginDTO.getUsername());
        if(!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid login");;
        }

        BBUserMinClaimSet userDTO = new BBUserMinClaimSet(user.getUsername(), user.getAuthority());
        return jwtUtils.createJWT(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String description,
            @RequestParam String sex
    ) throws JsonProcessingException {
        Authentication auth = bbUserDetailsService.registerUser(username, email, name, password, description, sex);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new TokenDTO(jwtUtils.createJWT(auth))
        );
    }
}
