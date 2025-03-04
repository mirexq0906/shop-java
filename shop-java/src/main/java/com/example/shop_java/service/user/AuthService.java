package com.example.shop_java.service.user;

import com.example.shop_java.entity.user.User;
import com.example.shop_java.web.dto.user.JwtDto;
import com.example.shop_java.web.dto.user.LoginDto;
import com.example.shop_java.web.response.user.JwtResponse;

public interface AuthService {

    User register(User user);

    JwtResponse login(LoginDto loginDto);

    JwtResponse refresh(JwtDto jwtDto);

}
