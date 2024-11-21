package com.iiex.cost_share_service.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.iiex.cost_share_service.config.CustomUserDetails;

import org.springframework.security.core.Authentication;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Component
public class JwtUtils {
    @Value("${security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-time}")
    private int expirationTime;

    public String generateTokenForUser(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("id", userPrincipal.getUserId())
                .claim("role", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + expirationTime)))
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
        return parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parserBuilder().setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }

    public String generateTokenForOAuthUser(String email, String username) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + expirationTime)))
                .signWith(key())
                .compact();
    }
}
