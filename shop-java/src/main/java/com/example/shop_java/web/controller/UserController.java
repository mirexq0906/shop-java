package com.example.shop_java.web.controller;

import com.example.shop_java.exception.InvalidJwtToken;
import com.example.shop_java.service.user.AuthService;
import com.example.shop_java.service.user.JwtService;
import com.example.shop_java.web.dto.user.JwtDto;
import com.example.shop_java.web.dto.user.LoginDto;
import com.example.shop_java.web.dto.user.UserDto;
import com.example.shop_java.web.mapper.UserMapper;
import com.example.shop_java.web.response.user.JwtResponse;
import com.example.shop_java.web.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Validated UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userMapper.userToUserResponse(
                        authService.register(userMapper.requestToUser(userDto))
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.authService.login(loginDto)
        );
    }

    @PostMapping("/check")
    public ResponseEntity<Void> check(@RequestBody JwtDto jwtDto) {
        try {
            if (jwtService.validateToken(jwtDto.getToken())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (InvalidJwtToken e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody JwtDto jwtDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.authService.refresh(jwtDto)
        );
    }

}
