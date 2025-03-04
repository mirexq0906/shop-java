package com.example.shop_java.service.user.impl;

import com.example.shop_java.entity.user.RefreshToken;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.exception.InvalidJwtToken;
import com.example.shop_java.service.user.JwtService;
import com.example.shop_java.service.user.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.token}")
    private String jwtSignToken;

    @Value("${app.jwt.access}")
    private Duration access;

    @Value("${app.jwt.refresh}")
    private Duration refresh;

    private final UserDetailsService userService;

    private final RefreshTokenService refreshTokenService;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(User.Fields.id, user.getId());
        claims.put(User.Fields.email, user.getEmail());
        claims.put(User.Fields.username, user.getUsername());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .signWith(getSecretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + access.toMillis()))
                .compact();
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(User.Fields.id, user.getId());

        String token = Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .signWith(getSecretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refresh.toMillis()))
                .compact();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .userId(user.getId())
                .build();

        this.refreshTokenService.save(refreshToken);
        return token;
    }

    public boolean validateToken(String token) throws InvalidJwtToken {
        return !this.extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) throws InvalidJwtToken {
        try {
            return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        } catch (RuntimeException e) {
            throw new InvalidJwtToken("Invalid JWT token", e.getCause());
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSignToken));
    }

    public Authentication getAuthentication(String token) throws InvalidJwtToken {
        String username = extractAllClaims(token).getSubject();
        UserDetails user = userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
