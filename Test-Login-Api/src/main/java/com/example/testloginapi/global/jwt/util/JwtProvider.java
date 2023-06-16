package com.example.testloginapi.global.jwt.util;

import com.example.testloginapi.domain.user.domain.User;
import com.example.testloginapi.global.security.auth.AuthDetails;
import com.example.testloginapi.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final AuthDetailsService authDetailsService;

    public String createToken(User user, Long time) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey(secretKey))
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT Signature.");
            e.getMessage();
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT.");
            e.getMessage();
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT.");
            e.getMessage();
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT.");
            e.getMessage();
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
            e.getMessage();
        } catch (NullPointerException e) {
            System.out.println("JWT RefreshToken is empty.");
            e.getMessage();
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = (AuthDetails) authDetailsService.loadUserByUsername(extractEmail(token));
        return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey(secretKey))
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email", String.class);
    }
}