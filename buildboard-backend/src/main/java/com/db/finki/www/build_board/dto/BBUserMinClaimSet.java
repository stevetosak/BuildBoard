package com.db.finki.www.build_board.dto;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BBUserMinClaimSet {
    private String username;
    private List<GrantedAuthority> authorities;
    private LocalDateTime expiration;
    @JsonIgnore
    @Value("${spring.jwt-expiration-duration-mins}")
    private Integer expirationDurationMins;

    public BBUserMinClaimSet(String username,  List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.expiration = LocalDateTime.now().plusMinutes(this.expirationDurationMins);
    }
}
