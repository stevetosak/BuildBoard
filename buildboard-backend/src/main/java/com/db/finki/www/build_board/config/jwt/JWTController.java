package com.db.finki.www.build_board.config.jwt;

import com.db.finki.www.build_board.config.jwt.dtos.TokenDTO;
import com.db.finki.www.build_board.config.jwt.dtos.UserLoginDTO;
import com.db.finki.www.build_board.dto.BBUserMinClaimSet;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class JWTController {
    private final BBUserDetailsService bbUserDetailsService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public JWTController(BBUserDetailsService bbUserDetailsService, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.bbUserDetailsService = bbUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @ExceptionHandler({JOSEException.class})
    public ProblemDetail handleJOSEException(JOSEException e) {
         return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Sorry the server cannot verify that JOSE header");
    }

    @ExceptionHandler({JsonProcessingException.class})
    public ProblemDetail handleJsonProcessingException(JsonProcessingException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,"The server failed to generate JSON");
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"The username or password provided isn't correct");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody UserLoginDTO userLoginDTO
    ) throws JsonProcessingException, JOSEException {
        BBUser user = (BBUser) bbUserDetailsService.loadUserByUsername(userLoginDTO.getUsername());
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Username or password incorrect");
        }

        BBUserMinClaimSet userDTO = new BBUserMinClaimSet(user.getUsername(), user.getAuthority());
        String token = jwtUtils.createJWT(userDTO);
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String description,
            @RequestParam String sex
    ) throws JsonProcessingException, JOSEException {
        BBUser auth = bbUserDetailsService.registerUser(username, email, name, password, description, sex);
        BBUserMinClaimSet userDTO = new BBUserMinClaimSet(username, auth.getAuthority());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new TokenDTO(jwtUtils.createJWT(userDTO))
        );
    }
}
