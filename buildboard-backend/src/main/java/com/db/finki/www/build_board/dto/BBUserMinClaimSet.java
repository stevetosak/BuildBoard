package com.db.finki.www.build_board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class BBUserMinClaimSet {
    public  String username;
    public List<GrantedAuthority> authorities;
    public LocalDateTime expiration;

    @JsonIgnore
    public Integer expirationDurationMins = 60;

    @JsonProperty("authorities")
    public List<String> getAuthoritiesAsString() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @JsonProperty("authorities")
    public void setAuthoritiesAsString(List<String> authorities) {
        this.authorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public BBUserMinClaimSet(){}

    public BBUserMinClaimSet(String username,  List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.expiration = LocalDateTime.now().plusMinutes(this.expirationDurationMins);
    }
}
