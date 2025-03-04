package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.user.Role;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.web.dto.user.UserDto;
import com.example.shop_java.web.response.user.JwtResponse;
import com.example.shop_java.web.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User requestToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public JwtResponse jwtTokenToJwtResponse(String token, String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setRefreshToken(refreshToken);
        return jwtResponse;
    }

    public UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }

}