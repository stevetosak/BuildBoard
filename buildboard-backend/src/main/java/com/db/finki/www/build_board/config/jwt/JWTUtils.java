package com.db.finki.www.build_board.config.jwt;

import com.db.finki.www.build_board.dto.BBUserMinClaimSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;

@Service
public class JWTUtils {
    // openssl rand -base64 32 | tr '+/' '-_' | tr -d '='
    @Value("${JWT_SECRET}")
    private String secret;
    private final JWSSigner signer;
    private final JWSVerifier verifier;
    private final ObjectMapper objectMapper;

    public JWTUtils(ObjectMapper objectMapper) throws JOSEException {
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

    public JWTAuthentication extractFromRequest(String jwt) throws BadRequestException {
        BBUserMinClaimSet jws = extractClaimSet(getJWS(jwt));

        JWTAuthentication jwtAuth = new JWTAuthentication();

        jwtAuth.setAuthenticated(true);
        jwtAuth.setPrincipal(jws);

        return jwtAuth;
    }

    public String createJWT(Object object) throws JsonProcessingException, JOSEException {
        JWSObject jws = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(this.objectMapper.writeValueAsString(object)));
        jws.sign(signer);
        return jws.serialize();
    }
}
