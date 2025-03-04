package com.example.shop_java.service.user.impl;

import com.example.shop_java.entity.user.RefreshToken;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.exception.InvalidJwtToken;
import com.example.shop_java.repository.RefreshTokenRepository;
import com.example.shop_java.repository.UserRepository;
import com.example.shop_java.service.user.AuthService;
import com.example.shop_java.service.user.JwtService;
import com.example.shop_java.service.user.RefreshTokenService;
import com.example.shop_java.service.user.UserService;
import com.example.shop_java.web.dto.user.JwtDto;
import com.example.shop_java.web.dto.user.LoginDto;
import com.example.shop_java.web.mapper.UserMapper;
import com.example.shop_java.web.response.user.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserService userService;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    private final RefreshTokenService refreshTokenService;

    public User register(User user) {
        return userRepository.save(user);
    }

    public JwtResponse login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        User user = userService.findByUsername(loginDto.getUsername());

        return this.userMapper.jwtTokenToJwtResponse(
                this.jwtService.generateToken(user),
                this.jwtService.generateRefreshToken(user)
        );
    }

    public JwtResponse refresh(JwtDto jwtDto) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(jwtDto.getToken());
        User user = this.userService.findById(refreshToken.getUserId());
        this.refreshTokenService.delete(refreshToken);
        return this.userMapper.jwtTokenToJwtResponse(
                this.jwtService.generateToken(user),
                this.jwtService.generateRefreshToken(user)
        );
    }

}
