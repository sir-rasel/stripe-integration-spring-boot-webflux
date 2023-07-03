package org.sir.stripeintegration.core.shared.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.sir.stripeintegration.core.domain.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    @Value("${springbootwebflux.jjwt.secret}")
    private String secret;

    @Value("${springbootwebflux.jjwt.tokenexpiration}")
    private String tokenExpirationTime;

    @Value("${springbootwebflux.jjwt.refreshtokenexpiration}")
    private String refreshTokenExpirationTime;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token).getBody();
    }

    public String getUserEmailFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        return doGenerateToken(claims, user.getEmail(), tokenExpirationTime);
    }

    public String generateRefreshToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        return doGenerateToken(claims, user.getEmail(), refreshTokenExpirationTime);
    }

    private String doGenerateToken(Map<String, Object> claims, String userEmail, String expirationTime) {
        long expirationTimeLong = Long.parseLong(expirationTime); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
