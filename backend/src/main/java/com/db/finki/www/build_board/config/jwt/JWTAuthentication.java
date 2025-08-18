package com.db.finki.www.build_board.config.jwt;

import com.db.finki.www.build_board.dto.BBUserMinClaimSet;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
public class JWTAuthentication implements Authentication {
    private BBUser principal;
    private boolean isAuth;

    public JWTAuthentication(){
        this.isAuth = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Object getCredentials() {
        return "jwt";
    }

    @Override
    public Object getDetails() {
        throw new UnsupportedOperationException("Why are you trying this");
    }
    @Override
    public BBUser getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuth;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuth = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal.getUsername();
    }
}
