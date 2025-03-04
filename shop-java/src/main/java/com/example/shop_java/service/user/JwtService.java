package com.example.shop_java.service.user;

import com.example.shop_java.entity.user.RefreshToken;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.exception.InvalidJwtToken;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateToken(User user);

    String generateRefreshToken(User user);

    boolean validateToken(String token) throws InvalidJwtToken;

    Authentication getAuthentication(String token) throws InvalidJwtToken;

}
