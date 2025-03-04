package com.example.shop_java.web.response.user;

import lombok.Data;

@Data
public class JwtResponse {

    private String token;

    private String refreshToken;

}
