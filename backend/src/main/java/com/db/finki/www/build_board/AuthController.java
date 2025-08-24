package com.db.finki.www.build_board;

import com.db.finki.www.build_board.config.jwt.JWTUtils;
import com.db.finki.www.build_board.config.jwt.dtos.BBUserRegisterDTO;
import com.db.finki.www.build_board.config.jwt.dtos.TokenDTO;
import com.db.finki.www.build_board.config.jwt.dtos.UserLoginDTO;
import com.db.finki.www.build_board.bb_users.BBUser;
import com.db.finki.www.build_board.bb_users.BBUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class AuthController {
    private final BBUserDetailsService bbUserDetailsService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public AuthController(BBUserDetailsService bbUserDetailsService, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.bbUserDetailsService = bbUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(
            @RequestBody UserLoginDTO userLoginDTO
    ) throws JsonProcessingException, JOSEException {
        BBUser user = (BBUser) bbUserDetailsService.loadUserByUsername(userLoginDTO.getUsername());
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Username or password incorrect");
        }
        String token = jwtUtils.createJWT(user);
        return new ResponseEntity<>(this.objectMapper.writeValueAsString(new TokenDTO(token)), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<TokenDTO> register(
            @RequestBody BBUserRegisterDTO bbUserRegisterDTO
    ) throws JsonProcessingException, JOSEException {
        BBUser createdUser = bbUserDetailsService.registerUser(
                bbUserRegisterDTO.getUsername(),
                bbUserRegisterDTO.getEmail(),
                bbUserRegisterDTO.getName(),
                bbUserRegisterDTO.getPassword(),
                bbUserRegisterDTO.getDescription(),
                bbUserRegisterDTO.getSex());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new TokenDTO(jwtUtils.createJWT(createdUser))
        );
    }
}
