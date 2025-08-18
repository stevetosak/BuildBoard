package com.db.finki.www.build_board.config.jwt;

import com.db.finki.www.build_board.dto.BBUserMinClaimSet;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JWTUtils {
    // openssl rand -base64 32 | tr '+/' '-_' | tr -d '='
    public String secret;
    private final JWSSigner signer;
    private final JWSVerifier verifier;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;

    public JWTUtils(ObjectMapper objectMapper, UserDetailsService userDetailsService) throws JOSEException {
        this.userDetailsService = userDetailsService;
        Dotenv dotenv = Dotenv.load();
        this.secret = dotenv.get("JWT_SECRET");

        this.objectMapper = objectMapper;
        this.signer = new MACSigner(secret);
        this.verifier = new MACVerifier(secret);
    }

    private JWSObject getJWS(String token) throws BadRequestException{
        try {
            JWSObject jws = JWSObject.parse(token);
            jws.verify(this.verifier);
            return jws;
        }catch (JOSEException e){
            throw new BadRequestException("The header is not in a format that we can process");
        }catch (ParseException e){
            throw new BadRequestException("The jwt cannot be processesd");
        }
    }

    public boolean isValid(String jwtString){
        if(jwtString==null || jwtString.isEmpty()) return false;
        try{
            PlainJWT jwt = PlainJWT.parse(jwtString);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            System.out.println(jwt);
            claims.getClaimAsString("username");
            return claims.getExpirationTime().after(new Date());
        } catch (ParseException e) {
            System.out.println("ERR occured");
           return false;
        }

    }

    private BBUserMinClaimSet extractClaimSet(JWSObject jws) throws BadRequestException{
        String jsonPayload = jws.getPayload().toString();

        try {
            BBUserMinClaimSet userData = objectMapper.readValue(jsonPayload, BBUserMinClaimSet.class);

            if (userData.getExpiration().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("The jwt has expired");
            }

            return userData;
        }catch (JsonProcessingException e){
            throw new BadRequestException("The json is not in the correct format");
        }
    }

    public JWTAuthentication getAuthentication(PlainJWT jwt){
        try{
            String username = jwt.getJWTClaimsSet().getClaimAsString("username");
            BBUser user = (BBUser) userDetailsService.loadUserByUsername(username);
            JWTAuthentication authentication = new JWTAuthentication();
            authentication.setAuthenticated(true);
            authentication.setPrincipal(user);

            return authentication;
        } catch (ParseException e) {
            return null;
        }
    }

    public PlainJWT parseJWT(String jwtString) {
        try{
            return PlainJWT.parse(jwtString);
        } catch (ParseException e) {
            return null;
        }
    }

    public String createJWT(BBUser user) {
        Date plsHr = new Date(System.currentTimeMillis() + 3600 * 1000 );
        System.out.println("plsHr: " + plsHr);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("authorities", user.getAuthorities())
                .expirationTime(plsHr)
                .issuer("buildboard.backend.com")
                .issueTime(new Date())
                .build();

        JWT jwt = new PlainJWT(claims); // zasega ke koristime plain jwt posle ke go adaptirame so potpis salam ili so rsa ili pak so hmac
        return jwt.serialize();
    }
}
